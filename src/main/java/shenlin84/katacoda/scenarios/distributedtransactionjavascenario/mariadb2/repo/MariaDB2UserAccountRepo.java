package shenlin84.katacoda.scenarios.distributedtransactionjavascenario.mariadb2.repo;

import org.springframework.data.repository.CrudRepository;
import shenlin84.katacoda.scenarios.distributedtransactionjavascenario.model.UserAccount;

public interface MariaDB2UserAccountRepo extends CrudRepository<UserAccount, Long>{
  UserAccount findByUser(String user);
}