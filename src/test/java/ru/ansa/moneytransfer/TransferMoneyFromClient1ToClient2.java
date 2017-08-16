package ru.ansa.moneytransfer;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.joda.money.Money;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.ansa.moneytransfer.entity.Account;
import ru.ansa.moneytransfer.rest.post.MoneyTransfer;
import ru.ansa.moneytransfer.rest.post.TransferInfo;
import ru.ansa.moneytransfer.server.JerseyMapperProvider;
import ru.ansa.moneytransfer.server.MoneyTransferApp;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigInteger;

public class TransferMoneyFromClient1ToClient2 extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(MoneyTransfer.class);
    }


    @BeforeAll
    public static void defaultData(){
        IgniteConfiguration igniteConfiguration = new IgniteConfiguration();
        igniteConfiguration.setIgniteInstanceName("MoneyTransfer");
        igniteConfiguration.setClientMode(true);
        Ignite ignite = Ignition.getOrStart(igniteConfiguration);

        Account a1 = new Account();
        a1.setNumber(BigInteger.valueOf(15));
        a1.setAmmount(Money.parse("RUB 1234.32"));

        Account a2 = new Account();
        a2.setNumber(BigInteger.valueOf(12));
        a2.setAmmount(Money.parse("RUB 421.43"));

        ignite.cache(MoneyTransferApp.CACHE).put(a1.getNumber(),a1);
        ignite.cache(MoneyTransferApp.CACHE).put(a2.getNumber(),a2);
    }

    @Test
    public void transferPositive(){
        ClientConfig config = new ClientConfig();
        config.register(JerseyMapperProvider.class);
        Client client = ClientBuilder.newClient(config);
        WebTarget target = client.target("http://localhost:8680").path("rest").path("transfer").path("v.1");
        TransferInfo in = new TransferInfo("15", "12","RUB 232.0");
        Response response = target.path("a2a").request(MediaType.APPLICATION_JSON).post(Entity.json(in));

        System.out.println(response.readEntity(String.class));
        Assert.assertEquals(200,response.getStatus());
    }

    @Test
    public void transferNegative(){
        ClientConfig config = new ClientConfig();
        config.register(JerseyMapperProvider.class);
        Client client = ClientBuilder.newClient(config);
        WebTarget target = client.target("http://localhost:8680").path("rest").path("transfer").path("v.1");
        TransferInfo in = new TransferInfo("12", "15","RUB 232.0");
        Response response = target.path("a2a").request(MediaType.APPLICATION_JSON).post(Entity.json(in));

        System.out.println(response.readEntity(String.class));
        Assert.assertEquals(200,response.getStatus());
    }


}
