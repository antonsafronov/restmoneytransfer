package ru.ansa.moneytransfer.service;

import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import ru.ansa.moneytransfer.entity.Account;
import ru.ansa.moneytransfer.server.MoneyTransferApp;

import javax.annotation.ManagedBean;
import javax.annotation.Resource;
import javax.inject.Singleton;
import java.math.BigInteger;

@Resource
@ManagedBean
@Singleton
public class AccountServiceInMemory implements AccountService {

    private IgniteCache<BigInteger, Account> db;

    public AccountServiceInMemory(){

        db = Ignition.ignite("MoneyTransfer").cache(MoneyTransferApp.CACHE);

    }


    public Account findAccountById(BigInteger accountId) {
        return db.get(accountId);
    }

    public void save(Account account) {
        db.put(account.getNumber(),account);
    }

    public void deactivate(Account account) {

    }
}
