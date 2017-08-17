package ru.ansa.moneytransfer.rest.post;

import java.io.Serializable;

/**
 * Entity for money transfer method.
 *
 * @author Anton Safronov
 * @see <a href="https://github.com/antonsafronov/restmoneytransfer">Source Code</a>
 * @since 1.0
 */
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
