package ru.ansa.moneytransfer.server;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import ru.ansa.moneytransfer.rest.post.MoneyTransfer;
import ru.ansa.moneytransfer.service.AccountService;
import ru.ansa.moneytransfer.service.AccountServiceInMemory;
import ru.ansa.moneytransfer.service.Transfer;
import ru.ansa.moneytransfer.service.TransferImpl;

public class MoneyTransferApp extends ResourceConfig {

    public static final String CACHE = "MoneyTransfer";

    public MoneyTransferApp() {

        packages("ru.ansa.moneytransfer");


        register(new AbstractBinder(){
            @Override
            protected void configure() {
                bind(AccountServiceInMemory.class).to(AccountService.class);
                bind(TransferImpl.class).to(Transfer.class);
            }
        });

    }

}
