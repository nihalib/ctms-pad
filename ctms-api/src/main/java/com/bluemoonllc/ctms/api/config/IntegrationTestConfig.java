package com.bluemoonllc.ctms.api.config;

import org.slf4j.LoggerFactory;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;

import javax.sql.DataSource;

@Configuration
@Profile("integration-test")
public class IntegrationTestConfig {

    @Bean
    public DataSource dataSource() {
        try (PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:9.6.21-alpine")) {
            container.withDatabaseName("ctms_pad")
                    .withUsername("postgres")
                    .withPassword("postgres")
                    .withLogConsumer(new Slf4jLogConsumer(LoggerFactory.getLogger("🐳 " + "postgres")));
            container.start();
            return getDataSource();
        }
    }

    private DataSource getDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.username("postgres");
        dataSourceBuilder.password("postgres");
        dataSourceBuilder.driverClassName("org.testcontainers.jdbc.ContainerDatabaseDriver");
        dataSourceBuilder.url("jdbc:tc:postgresql:///ctms_pad");

        return dataSourceBuilder.build();
    }
}
