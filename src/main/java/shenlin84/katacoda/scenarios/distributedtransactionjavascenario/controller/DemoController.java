package shenlin84.katacoda.scenarios.distributedtransactionjavascenario.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import shenlin84.katacoda.scenarios.distributedtransactionjavascenario.mariadb1.repo.MariaDB1UserAccountRepo;
import shenlin84.katacoda.scenarios.distributedtransactionjavascenario.mariadb2.repo.MariaDB2UserAccountRepo;
import shenlin84.katacoda.scenarios.distributedtransactionjavascenario.model.Transfer;
import shenlin84.katacoda.scenarios.distributedtransactionjavascenario.model.UserAccount;
import shenlin84.katacoda.scenarios.distributedtransactionjavascenario.rmq.RmqTxProducer;

import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.*;
import org.apache.rocketmq.remoting.common.*;

@RestController
public class DemoController {

    private final RmqTxProducer producerClient;
    private final MariaDB1UserAccountRepo mariaDB1Repo;
    private final MariaDB2UserAccountRepo mariaDB2Repo;

    @Autowired
    public DemoController(MariaDB1UserAccountRepo mariaDB1Repo, MariaDB2UserAccountRepo mariaDB2Repo,
            RmqTxProducer producerClient) {
        this.mariaDB1Repo = mariaDB1Repo;
        this.mariaDB2Repo = mariaDB2Repo;
        this.producerClient = producerClient;
    }

    /**
     * Send test message to local RMQ
     * 
     * @return
     */
    // @RequestMapping(value = "/testRMQ", method = RequestMethod.POST)
    // public ResponseEntity<String> testRMQ() throws Exception {
    // DefaultMQProducer producer = new DefaultMQProducer("group1");
    // producer.setNamesrvAddr("localhost:9876");

    // try {
    // producer.start();
    // Message msg = new Message("Test", "TagTest", ("Hello
    // RMQ").getBytes(RemotingHelper.DEFAULT_CHARSET));
    // SendResult sendResult = producer.send(msg);
    // System.out.println("SendResult: " + sendResult.getSendStatus());
    // } catch (Exception e) {
    // e.printStackTrace();
    // } finally {
    // producer.shutdown();
    // }
    // System.out.println("Send response no matter what!!!");
    // return new ResponseEntity<String>("Test Succeeded", HttpStatus.OK);
    // }

    @RequestMapping(value = "/createUser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<String> createUser(@RequestBody UserAccount userAccount) {
        try {
            System.out.println(userAccount.getUser() + ' ' + userAccount.getBalance() + ' ' + userAccount.getBank());

            String feedback = "Entity already exists";
            if ("boa".equals(userAccount.getBank())) {
                UserAccount resultUserAccount = this.mariaDB1Repo.findByUser(userAccount.getUser());
                System.out.println("Found user " + resultUserAccount);
                if (resultUserAccount == null) {
                    this.mariaDB1Repo.save(userAccount);
                    feedback = "Entity created";
                }
            }

            if ("chase".equals(userAccount.getBank())) {
                UserAccount resultUserAccount = this.mariaDB2Repo.findByUser(userAccount.getUser());
                System.out.println("Found user " + resultUserAccount);
                if (resultUserAccount == null) {
                    this.mariaDB2Repo.save(userAccount);
                    feedback = "Entity created";
                }
            }

            return new ResponseEntity<String>(feedback, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("Entity creation failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/getBalance", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getBalance(@RequestBody UserAccount userAccount) {
        try {
            System.out.println(userAccount.getUser() + ' ' + userAccount.getBank());
            String user = userAccount.getUser();
            UserAccount result = null;
            if ("boa".equals(userAccount.getBank())) {
                result = this.mariaDB1Repo.findByUser(user);
            }

            if ("chase".equals(userAccount.getBank())) {
                result = this.mariaDB2Repo.findByUser(user);
            }
            return new ResponseEntity<UserAccount>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("Failed to access user balance", HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/transfer", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> bankTransfer(@RequestBody Transfer transfer) {

        try {
            System.out.println(transfer);
            System.out.println(this.producerClient.getProducer().getClientIP());
            Message msg = new Message("Test", "transactionId", "KEY",
                    ("Hello RocketMQ").getBytes(RemotingHelper.DEFAULT_CHARSET));
            SendResult sendResult = this.producerClient.getProducer().sendMessageInTransaction(msg, null);
            System.out.printf("%s%n", sendResult);

            return new ResponseEntity<String>("Transfer Submitted", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("Transfer failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}