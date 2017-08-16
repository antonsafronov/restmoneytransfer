package ru.ansa.moneytransfer.service;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteTransactions;
import org.apache.ignite.Ignition;
import org.apache.ignite.transactions.Transaction;
import ru.ansa.moneytransfer.entity.Account;
import ru.ansa.moneytransfer.server.MoneyTransferApp;

import javax.annotation.ManagedBean;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Resource
@ManagedBean
public class TransferImpl implements Transfer {

    @Inject
    private AccountService accountService;

    @Override
    public String transfer(@NotNull String accountFromId, @NotNull String accountToId, @NotNull String ammount) {

        try{
            Ignite ignite = Ignition.ignite("MoneyTransfer");
            IgniteTransactions transactions = ignite.transactions();

            Account accountFrom = accountService.findAccountById(new BigInteger(accountFromId));

            if(accountFrom.enoughMoneyWithdraw(ammount)){
                try (Transaction tx = transactions.txStart()) {
                    accountFrom = accountService.findAccountById(new BigInteger(accountFromId));
                    Account accountTo = accountService.findAccountById(new BigInteger(accountToId));

                    accountTo.deposit(ammount);
                    accountFrom.withdraw(ammount);

                    ignite.getOrCreateCache(MoneyTransferApp.CACHE).put(accountFrom.getNumber(),accountFrom);
                    ignite.getOrCreateCache(MoneyTransferApp.CACHE).put(accountTo.getNumber(),accountTo);
                    tx.commit();
                    return "The money transfer completed.";
                }
            }
            return "The money transfer rejected.Not enough money.";
        } catch (Exception e){
            return "Sorry, unexpected behavior. Please try again late.";
        }

    }
}
