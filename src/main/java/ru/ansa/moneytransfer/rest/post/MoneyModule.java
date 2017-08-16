package ru.ansa.moneytransfer.rest.post;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.joda.money.Money;
import ru.ansa.moneytransfer.rest.post.MoneyDeserializer;
import ru.ansa.moneytransfer.rest.post.MoneySerializer;

public class MoneyModule extends SimpleModule {
    public MoneyModule() {
        super("MoneyModule", new Version(0, 1, 0, "alpha","moneytransfer","restmoneytransfer"));
        this.addSerializer(Money.class, new MoneySerializer());
        this.addDeserializer(Money.class, new MoneyDeserializer());
    }
}
