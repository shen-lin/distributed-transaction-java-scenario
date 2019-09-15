package shenlin84.katacoda.scenarios.distributedtransactionjavascenario.rmq;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.transaction.Transactional;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;

import shenlin84.katacoda.scenarios.distributedtransactionjavascenario.model.TransactionAudit;
import shenlin84.katacoda.scenarios.distributedtransactionjavascenario.model.Transfer;
import shenlin84.katacoda.scenarios.distributedtransactionjavascenario.model.UserAccount;
import shenlin84.katacoda.scenarios.distributedtransactionjavascenario.mariadb1.repo.MariaDB1TransactionAuditRepo;
import shenlin84.katacoda.scenarios.distributedtransactionjavascenario.mariadb2.repo.MariaDB2TransactionAuditRepo;
import shenlin84.katacoda.scenarios.distributedtransactionjavascenario.mariadb1.repo.MariaDB1UserAccountRepo;
import shenlin84.katacoda.scenarios.distributedtransactionjavascenario.mariadb2.repo.MariaDB2UserAccountRepo;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class RmqTxProducerListener implements TransactionListener {

    private final MariaDB1UserAccountRepo mariaDB1Repo;
    private final MariaDB2UserAccountRepo mariaDB2Repo;
    private final MariaDB1TransactionAuditRepo mariaDB1TxRepo;
    private final MariaDB2TransactionAuditRepo mariaDB2TxRepo;

    @Autowired
    public RmqTxProducerListener(MariaDB1UserAccountRepo mariaDB1Repo, MariaDB2UserAccountRepo mariaDB2Repo,
            MariaDB1TransactionAuditRepo mariaDB1TxRepo, MariaDB2TransactionAuditRepo mariaDB2TxRepo) {
        this.mariaDB1Repo = mariaDB1Repo;
        this.mariaDB2Repo = mariaDB2Repo;
        this.mariaDB1TxRepo = mariaDB1TxRepo;
        this.mariaDB2TxRepo = mariaDB2TxRepo;
    }

    /**
     * MQ ack prepare message received. It notifies producer to execute local
     * commit. If producer failed before being notified, MQ might periodically
     * trigger this call? Might receive duplicate messages. Need to check for
     * dulicate message.
     */
    @Override
    public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        Transfer transfer = (Transfer) arg;
        String txid = msg.getTransactionId();
        System.out.println("Callbak of prepare message: " + transfer + " " + txid);
        this.commitTransaction(transfer, txid);
        return LocalTransactionState.UNKNOW;
    }

    /**
     * If mq did to receive commit message from producer, it will query producer for
     * the transaction status periodically and this function is called. Producer can
     * choose to send ROLLBACK message to mq.
     */
    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt msg) {
        String txid = msg.getTransactionId();
        System.out.println("MQ queries producer " + txid);
        System.out.println(msg.getMsgId());
        TransactionAudit txAudit = this.mariaDB1TxRepo.findByTxId(txid);
        System.out.println("Found tx id: " + txAudit.getTxid());
        return LocalTransactionState.UNKNOW;
    }

    @Transactional
    public void commitTransaction(Transfer transfer, String txid) {
        String senderBank = transfer.getSenderBank();
        String sender = transfer.getSender();
        UserAccount senderAccount = null;
        if ("boa".equals(senderBank)) {
            senderAccount = this.mariaDB1Repo.findByUser(sender);
            System.out.println("Sender balance: " + senderAccount.getBalance());
            int newAmount = senderAccount.getBalance() - transfer.getAmount();
            senderAccount.setBalance(newAmount);
            this.mariaDB1Repo.save(senderAccount);
            TransactionAudit tx = new TransactionAudit();
            tx.setTxid(txid);
            this.mariaDB1TxRepo.save(tx);
        }
        if ("chase".equals(senderBank)) {
            senderAccount = this.mariaDB2Repo.findByUser(sender);
            System.out.println("Sender balance: " + senderAccount.getBalance());
            int newAmount = senderAccount.getBalance() - transfer.getAmount();
            senderAccount.setBalance(newAmount);
            this.mariaDB2Repo.save(senderAccount);
            TransactionAudit tx = new TransactionAudit();
            tx.setTxid(txid);
            this.mariaDB2TxRepo.save(tx);
        }
    }
}