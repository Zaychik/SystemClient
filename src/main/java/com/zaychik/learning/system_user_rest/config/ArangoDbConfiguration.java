package com.zaychik.learning.system_user_rest.config;

import com.arangodb.ArangoDB;
import com.arangodb.springframework.annotation.EnableArangoRepositories;
import com.arangodb.springframework.config.ArangoConfiguration;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableArangoRepositories(basePackages = {"com.zaychik.learning.system_user_rest"})
@ConfigurationProperties(prefix = "arangodb")
@Data
public class ArangoDbConfiguration implements ArangoConfiguration   {
    private String host;

    private int port;

    private String user;

    private String password;
    @Override
    public ArangoDB.Builder arango() {
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