package ru.ansa.moneytransfer.service;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteTransactions;
import org.apache.ignite.Ignition;
import org.apache.ignite.transactions.Transaction;
import org.joda.money.Money;
import ru.ansa.moneytransfer.entity.Account;
import ru.ansa.moneytransfer.exceptions.NotEnoughMoney;
import ru.ansa.moneytransfer.server.MoneyTransferApp;

import javax.annotation.ManagedBean;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Resource
@ManagedBean
public class TransferImpl implements Transfer {

    @Inject
    private AccountService accountService;

    @Override
    public String transfer(@NotNull BigInteger accountFromId, @NotNull BigInteger accountToId, @NotNull Money ammount) throws NotEnoughMoney {

        Ignite ignite = Ignition.ignite("MoneyTransfer");
        IgniteTransactions transactions = ignite.transactions();
        Lock lock = new ReentrantLock();
        try (Transaction tx = transactions.txStart()) {

            Account accountFrom = accountService.findAccountById(accountFromId);
            Account accountTo = accountService.findAccountById(accountToId);

            if(accountFrom.enoughMoneyWithdraw(ammount)) {
                try {
                    lock.lock();
                    accountFrom.withdraw(ammount);
                    accountTo.deposit(ammount);
                } finally {
                    lock.unlock();
                }
            } else {
                tx.rollback();
                throw new NotEnoughMoney();
            }
            ignite.getOrCreateCache(MoneyTransferApp.CACHE).put(accountFrom.getNumber(),accountFrom);
            ignite.getOrCreateCache(MoneyTransferApp.CACHE).put(accountTo.getNumber(),accountTo);
            tx.commit();
            return "The money transfer completed.";
        }
    }
}
