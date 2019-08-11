package shenlin84.katacoda.scenarios.distributedtransactionjavascenario.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shenlin84.katacoda.scenarios.distributedtransactionjavascenario.model.UserAccount;

import java.util.*;

@RestController
public class DemoController {


    public DemoController() {
    }

    @RequestMapping(value = "/createDB", method = RequestMethod.POST)
    public ResponseEntity<String> createDB() {
        try {
            return new ResponseEntity<String>("DB created", HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<String>("DB creation failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/createUser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createUser(@RequestBody UserAccount userAccount) {
        try {

            System.out.println(userAccount.getUser() + ' ' +  userAccount.getBalance() + ' ' +  userAccount.getBank());
            return new ResponseEntity<String>("Entity created", HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<String>("Entity creation failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}