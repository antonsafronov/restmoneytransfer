package ru.ansa.moneytransfer;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.joda.money.Money;
import org.junit.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import ru.ansa.moneytransfer.entity.Account;
import ru.ansa.moneytransfer.exceptions.NotEnoughMoney;
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


public class TestTransferMoneyFromClient1ToClient2 extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(MoneyTransfer.class);
    }

    private WebTarget target;
    private Ignite ignite;

    @BeforeTest
    public void defaultData(){
        IgniteConfiguration igniteConfiguration = new IgniteConfiguration();
        igniteConfiguration.setIgniteInstanceName("MoneyTransfer");
        igniteConfiguration.setClientMode(true);
        ignite = Ignition.getOrStart(igniteConfiguration);

        Account a1 = new Account();
        a1.setNumber(BigInteger.valueOf(15));
        a1.setAmmount(Money.parse("RUB 1500.00"));

        Account a2 = new Account();
        a2.setNumber(BigInteger.valueOf(12));
        a2.setAmmount(Money.parse("RUB 100.00"));

        ignite.cache(MoneyTransferApp.CACHE).put(a1.getNumber(),a1);
        ignite.cache(MoneyTransferApp.CACHE).put(a2.getNumber(),a2);

        ClientConfig config = new ClientConfig();
        config.register(JerseyMapperProvider.class);
        Client client = ClientBuilder.newClient(config);
        target = client.target("http://localhost:8680").path("rest").path("transfer").path("v.1").path("a2a");
    }

    @Test(threadPoolSize = 3, invocationCount = 10,  timeOut = 10000)
    public void transferPositive(){

        TransferInfo in = new TransferInfo("15", "12","RUB 50.0");
        Response response = target.request(MediaType.APPLICATION_JSON).post(Entity.json(in));

        System.out.println(response.readEntity(String.class));

        Assert.assertEquals(200,response.getStatus());
    }

    @Test(dependsOnMethods = "transferPositive")
    public void checkAmmount(){
        Account account = (Account) ignite.cache(MoneyTransferApp.CACHE).get(BigInteger.valueOf(12));
        Assert.assertEquals(600.0, account.getAmmount().getAmount().doubleValue(),0);

    }

    @Test(dependsOnMethods = "checkAmmount")
    public void transferNegative(){
        TransferInfo in = new TransferInfo("15", "12","RUB 2000.0");
        Response response = target.request(MediaType.APPLICATION_JSON).post(Entity.json(in));

        System.out.println(response.readEntity(NotEnoughMoney.class).getMessage());
        Assert.assertEquals(403,response.getStatus());
    }


}
