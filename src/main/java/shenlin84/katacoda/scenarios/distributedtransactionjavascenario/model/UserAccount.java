package shenlin84.katacoda.scenarios.distributedtransactionjavascenario.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "account")
public class UserAccount implements Serializable {

    private static final long serialVersionUID = -7032552750248895293L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "user")
    private String user;

    @Column(name = "balance")
    private int balance;

    private String bank;

    public UserAccount() {

    }

    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getBalance() {
        return this.balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getBank() {
        return this.bank;
    }

    public void setBalance(String bank) {
        this.bank = bank;
    }

    public String toString() {
        return this.bank + " " + this.user + " " + this.balance;
    }
}