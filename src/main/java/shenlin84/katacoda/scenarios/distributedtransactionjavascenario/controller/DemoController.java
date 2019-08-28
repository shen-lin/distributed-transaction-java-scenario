package shenlin84.katacoda.scenarios.distributedtransactionjavascenario.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shenlin84.katacoda.scenarios.distributedtransactionjavascenario.model.UserAccount;

import java.util.*;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.*;
import org.apache.rocketmq.remoting.common.*;

@RestController
public class DemoController {

    public DemoController() {

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
            Message msg = new Message("TBW102", "TagTest", ("Hello RMQ").getBytes(RemotingHelper.DEFAULT_CHARSET));
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

    @RequestMapping(value = "/createDB", method = RequestMethod.POST)
    public ResponseEntity<String> createDB() {
        try {
            return new ResponseEntity<String>("DB created", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("DB creation failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/createUser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createUser(@RequestBody UserAccount userAccount) {
        try {

            System.out.println(userAccount.getUser() + ' ' + userAccount.getBalance() + ' ' + userAccount.getBank());
            return new ResponseEntity<String>("Entity created", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("Entity creation failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}