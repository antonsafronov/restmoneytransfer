package ru.ansa.moneytransfer.entity;

import org.joda.money.Money;
import ru.ansa.moneytransfer.exceptions.NotEnoughMoney;

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

    public Money deposit(Money depositMoney){
        ammount = ammount.plus(depositMoney);
        return ammount;
    }

    public Money withdraw(Money withdraw) throws NotEnoughMoney {
        if(enoughMoneyWithdraw(withdraw)){
            return ammount.minus(withdraw);
        }
        throw new NotEnoughMoney();
    }

    public boolean enoughMoneyWithdraw(Money minusMoney){
        return minusMoney.isLessThan(ammount);
    }

    @Override
    public String toString() {
        return "Account{" +
                "number=" + number +
                ", ammount=" + ammount.toString() +
                '}';
    }
}
