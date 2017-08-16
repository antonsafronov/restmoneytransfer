package ru.ansa.moneytransfer.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.ansa.moneytransfer.rest.post.MoneyModule;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

@Provider
public class JerseyMapperProvider implements ContextResolver<ObjectMapper> {

    final ObjectMapper defaultObjectMapper;

    public JerseyMapperProvider() {
        this.defaultObjectMapper = new ObjectMapper();
        defaultObjectMapper.registerModule(new MoneyModule());
    }

    public ObjectMapper getContext(Class<?> type)
    {
        return defaultObjectMapper;
    }
}
