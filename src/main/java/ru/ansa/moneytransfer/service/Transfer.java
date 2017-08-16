package ru.ansa.moneytransfer.service;

public interface Transfer {

    String transfer(String accountFrom, String accountTo, String ammount);

}
