package shenlin84.katacoda.scenarios.distributedtransactionjavascenario.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import shenlin84.katacoda.scenarios.distributedtransactionjavascenario.mariadb1.repo.MariaDB1UserAccountRepo;
import shenlin84.katacoda.scenarios.distributedtransactionjavascenario.mariadb2.repo.MariaDB2UserAccountRepo;
import shenlin84.katacoda.scenarios.distributedtransactionjavascenario.model.UserAccount;

import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.*;
import org.apache.rocketmq.remoting.common.*;

@RestController
public class DemoController {

    private final MariaDB1UserAccountRepo mariaDB1Repo;
    private final MariaDB2UserAccountRepo mariaDB2Repo;

    @Autowired
    public DemoController(MariaDB1UserAccountRepo mariaDB1Repo, MariaDB2UserAccountRepo mariaDB2Repo) {
        this.mariaDB1Repo = mariaDB1Repo;
        this.mariaDB2Repo = mariaDB2Repo;
    }

    /**
     * Send test message to local RMQ
     * 
     * @return
     */
    @RequestMapping(value = "/testRMQ", method = RequestMethod.POST)
    public ResponseEntity<String> testRMQ() throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("group1");
        producer.setNamesrvAddr("localhost:9876");

        try {
            producer.start();
            Message msg = new Message("Test", "TagTest", ("Hello RMQ").getBytes(RemotingHelper.DEFAULT_CHARSET));
            SendResult sendResult = producer.send(msg);
            System.out.println("SendResult: " + sendResult.getSendStatus());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.shutdown();
        }
        System.out.println("Send response no matter what!!!");
        return new ResponseEntity<String>("Test Succeeded", HttpStatus.OK);
    }

    @RequestMapping(value = "/createUser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createUser(@RequestBody UserAccount userAccount) {
        try {
            System.out.println(userAccount.getUser() + ' ' + userAccount.getBalance() + ' ' + userAccount.getBank());

            if ("boa".equals(userAccount.getBank())) {
                this.mariaDB1Repo.save(userAccount);
            } 
            
            if ("chase".equals(userAccount.getBank())){
                this.mariaDB2Repo.save(userAccount);
            }
            this.mariaDB1Repo.save(userAccount);
            return new ResponseEntity<String>("Entity created", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("Entity creation failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}