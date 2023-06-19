package com.zaychik.learning.system_user_rest;

import com.zaychik.learning.system_user_rest.source.DriverManagerDataSource;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;

@SpringBootApplication
public class SystemUserRestApplication {

	private static final String URL = "jdbc:postgresql://localhost:5430/demoDB";
	private static final String USER = "usr";
	private static final String PASSWORD = "pwd";
	private static final Logger log = LoggerFactory.getLogger(SystemUserRestApplication.class);
	public static void main(String[] args) {
		var dataSource = new DriverManagerDataSource(URL, USER, PASSWORD);
		flywayMigrations(dataSource);

		SpringApplication.run(SystemUserRestApplication.class, args);

	}

	private static void flywayMigrations(DataSource dataSource) {
		log.info("db migration started...");
		var flyway = Flyway.configure()
				.dataSource(dataSource)
				.locations("classpath:/db/migration")
				.load();
		flyway.migrate();
		log.info("db migration finished.");
		log.info("***");
	}

}


