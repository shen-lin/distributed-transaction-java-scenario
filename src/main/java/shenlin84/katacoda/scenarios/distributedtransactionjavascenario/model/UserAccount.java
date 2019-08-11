package shenlin84.katacoda.scenarios.distributedtransactionjavascenario.model;


public class UserAccount {

    private String user;
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

    public void setBank(String bank) {
        this.bank = bank;
    }
}