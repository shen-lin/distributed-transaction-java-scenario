package shenlin84.katacoda.scenarios.distributedtransactionjavascenario.model;

import java.io.Serializable;

public class Transfer implements Serializable {

    private static final long serialVersionUID = -7032552750248895293L;

    private String sender;
    private String receiver;
    private String senderBank;
    private String receiverBank;
    private int amount;

    public Transfer() {

    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSenderBank() {
        return senderBank;
    }

    public void setSenderBank(String senderBank) {
        this.senderBank = senderBank;
    }

    public String getReceiverBank() {
        return receiverBank;
    }

    public void setReceiverBank(String receiverBank) {
        this.receiverBank = receiverBank;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String toString() {
        return "From " + this.sender + " to " + this.receiver + ": $" + this.amount;
    }
}