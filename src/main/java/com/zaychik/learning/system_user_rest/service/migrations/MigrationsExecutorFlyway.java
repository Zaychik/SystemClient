package com.zaychik.learning.system_user_rest.service.migrations;

import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class MigrationsExecutorFlyway {
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String userName;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${migration.run}")
    private Boolean isExecMigration;



    @PostConstruct
    public void executeMigrations() {
        if (isExecMigration) {
            log.info("db migration started...");
            Flyway.configure()
                    .dataSource(url, userName, password)
                    .locations("classpath:/db/migration")
                    .load()
                    .migrate();
            log.info("db migration finished.");
        }
    }
}
