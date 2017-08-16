package ru.ansa.moneytransfer.service;

import ru.ansa.moneytransfer.entity.Account;

import java.math.BigInteger;

public interface AccountService {

    Account findAccountById(BigInteger accountId);

    void save(Account account);

    void deactivate(Account account);


}
