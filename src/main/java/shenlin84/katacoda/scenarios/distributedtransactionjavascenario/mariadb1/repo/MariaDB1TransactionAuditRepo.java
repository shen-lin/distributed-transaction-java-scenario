package shenlin84.katacoda.scenarios.distributedtransactionjavascenario.mariadb1.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import shenlin84.katacoda.scenarios.distributedtransactionjavascenario.model.TransactionAudit;

public interface MariaDB1TransactionAuditRepo extends CrudRepository<TransactionAudit, Long> {
  @Query("from TransactionAudit txAudti where txAudti.txid=:txid")
  TransactionAudit findByTxId(@Param("txid") String txid);
}