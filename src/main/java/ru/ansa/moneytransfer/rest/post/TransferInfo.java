package ru.ansa.moneytransfer.rest.post;

import java.io.Serializable;


public class TransferInfo implements Serializable {

    private String accountFromId;
    private String accountToId;
    private String transferMoney;

    public TransferInfo() {
    }

    public TransferInfo(String accountFromId, String accountToId, String transferMoney) {
        this.accountFromId = accountFromId;
        this.accountToId = accountToId;
        this.transferMoney = transferMoney;
    }

    public String getAccountFromId() {
        return accountFromId;
    }

    public void setAccountFromId(String accountFromId) {
        this.accountFromId = accountFromId;
    }

    public String getAccountToId() {
        return accountToId;
    }

    public void setAccountToId(String accountToId) {
        this.accountToId = accountToId;
    }

    public String getTransferMoney() {
        return transferMoney;
    }

    public void setTransferMoney(String transferMoney) {
        this.transferMoney = transferMoney;
    }
}
