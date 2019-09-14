package shenlin84.katacoda.scenarios.distributedtransactionjavascenario.mariadb2.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import shenlin84.katacoda.scenarios.distributedtransactionjavascenario.model.UserAccount;

public interface MariaDB2UserAccountRepo extends CrudRepository<UserAccount, Long> {
  @Query("from UserAccount acct where acct.user=:user")
  UserAccount findByUser(@Param("user") String user);
}