package shenlin84.katacoda.scenarios.distributedtransactionjavascenario.mariadb1.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import shenlin84.katacoda.scenarios.distributedtransactionjavascenario.model.UserAccount;

public interface MariaDB1UserAccountRepo extends CrudRepository<UserAccount, Long> {

  @Query("from UserAccount acct where acct.user=:user")
  UserAccount findByUser(@Param("user") String user);
}