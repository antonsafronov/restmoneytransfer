package ru.ansa.moneytransfer.service;

import org.joda.money.Money;
import ru.ansa.moneytransfer.exceptions.NotEnoughMoney;

import java.math.BigInteger;

public interface Transfer {

    String transfer(BigInteger accountFrom, BigInteger accountTo, Money ammount) throws NotEnoughMoney;

}
