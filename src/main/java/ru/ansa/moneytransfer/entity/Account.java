package ru.ansa.moneytransfer.entity;

import org.joda.money.Money;

import java.io.Serializable;
import java.math.BigInteger;

public class Account implements Serializable {

    private BigInteger number;

    private Money ammount;

    public Account() {
    }

    public BigInteger getNumber() {
        return number;
    }

    public void setNumber(BigInteger number) {
        this.number = number;
    }

    public Money getAmmount() {
        return ammount;
    }

    public void setAmmount(Money ammount) {
        this.ammount = ammount;
    }

    public Money deposit(String addMoney){
        Money depositMoney = Money.parse(addMoney);
        ammount = ammount.plus(depositMoney);
        return ammount;
    }

    public Money withdraw(String minusMoney){
        Money withdraw = Money.parse(minusMoney);
        if(enoughMoneyWithdraw(minusMoney)){
            ammount = ammount.minus(withdraw);
        }
        return ammount;
    }

    public boolean enoughMoneyWithdraw(String minusMoney){
        return Money.parse(minusMoney).isLessThan(ammount);
    }

    @Override
    public String toString() {
        return "Account{" +
                "number=" + number +
                ", ammount=" + ammount.toString() +
                '}';
    }
}
