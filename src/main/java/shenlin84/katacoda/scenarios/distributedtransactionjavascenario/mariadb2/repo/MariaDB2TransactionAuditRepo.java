package shenlin84.katacoda.scenarios.distributedtransactionjavascenario.mariadb2.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import shenlin84.katacoda.scenarios.distributedtransactionjavascenario.model.TransactionAudit;

public interface MariaDB2TransactionAuditRepo extends CrudRepository<TransactionAudit, Long> {

}