package shenlin84.katacoda.scenarios.distributedtransactionjavascenario.mariadb1.repo;

import org.springframework.data.repository.CrudRepository;
import shenlin84.katacoda.scenarios.distributedtransactionjavascenario.model.UserAccount;

public interface MariaDB1UserAccountRepo extends CrudRepository<UserAccount, Long>{
  UserAccount findByUser(String user);
}