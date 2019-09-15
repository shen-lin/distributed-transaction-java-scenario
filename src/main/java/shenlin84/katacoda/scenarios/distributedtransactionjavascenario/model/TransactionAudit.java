package shenlin84.katacoda.scenarios.distributedtransactionjavascenario.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "transaction_audit")
public class TransactionAudit implements Serializable {

    private static final long serialVersionUID = -7032552750248895293L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "txid")
    private String txid;

    public TransactionAudit() {

    }

    public String toString() {
        return this.txid;
    }

    public String getTxid() {
        return txid;
    }

    public void setTxid(String txid) {
        this.txid = txid;
    }
}