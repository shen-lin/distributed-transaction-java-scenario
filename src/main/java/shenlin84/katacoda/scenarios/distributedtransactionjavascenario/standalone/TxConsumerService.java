package shenlin84.katacoda.scenarios.distributedtransactionjavascenario.standalone;

import org.springframework.stereotype.Component;

import shenlin84.katacoda.scenarios.distributedtransactionjavascenario.mariadb1.repo.MariaDB1UserAccountRepo;
import shenlin84.katacoda.scenarios.distributedtransactionjavascenario.mariadb2.repo.MariaDB2UserAccountRepo;
import shenlin84.katacoda.scenarios.distributedtransactionjavascenario.model.UserAccount;

import org.springframework.beans.factory.annotation.Autowired;

@Component
public class TxConsumerService implements ConsumerService {

    @Autowired
    private MariaDB1UserAccountRepo mariaDB1Repo;
    @Autowired
    private MariaDB2UserAccountRepo mariaDB2Repo;

    public void test() {
        UserAccount account = this.mariaDB1Repo.findByUser("alex");
        System.out.println(account);
    }
}