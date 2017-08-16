package ru.ansa.moneytransfer.server;

import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.configuration.TransactionConfiguration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import static org.apache.ignite.cache.CacheAtomicityMode.ATOMIC;

@WebListener
public class StartIgnitionServer implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {

        IgniteConfiguration igniteConfiguration = new IgniteConfiguration();
        igniteConfiguration.setIgniteInstanceName("MoneyTransfer");
        TransactionConfiguration txCfg = new TransactionConfiguration();

        CacheConfiguration cfg = new CacheConfiguration();
        cfg.setName(MoneyTransferApp.CACHE);
        cfg.setAtomicityMode(ATOMIC);

        igniteConfiguration.setTransactionConfiguration(txCfg);
        igniteConfiguration.setCacheConfiguration(cfg);

        Ignition.getOrStart(igniteConfiguration);
    }

}
