package ru.ansa.moneytransfer.exceptions;

public class NotEnoughMoney extends IllegalArgumentException {

    public NotEnoughMoney() {
        this("Sorry, but you have not enough money on your account.");
    }

    public NotEnoughMoney(String s) {
        super(s);
    }
}
