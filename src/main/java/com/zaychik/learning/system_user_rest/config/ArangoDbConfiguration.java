package com.zaychik.learning.system_user_rest.config;

import com.arangodb.ArangoDB;
import com.arangodb.springframework.annotation.EnableArangoRepositories;
import com.arangodb.springframework.config.ArangoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration @EnableArangoRepositories(basePackages = {"com.zaychik.learning.system_user_rest"})
public class ArangoDbConfiguration implements ArangoConfiguration   {
    private String host;

    private int port;

    private String user;

    private String password;
    @Autowired
    private Environment environment;
    @Override
    public ArangoDB.Builder arango() {
        host = environment.getProperty("arangodb.host");
        port = Integer.parseInt(environment.getProperty("arangodb.port"));
        user = environment.getProperty("arangodb.user");
        password = environment.getProperty("arangodb.password");

        return new ArangoDB.Builder()
                .host(host, port)
                .user(user)
                .password(password);
    }
    @Override
    public String database() {
        return "arangodb";
    }
}