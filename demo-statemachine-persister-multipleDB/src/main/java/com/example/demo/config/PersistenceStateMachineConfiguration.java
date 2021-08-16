package com.example.demo.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.statemachine.data.jpa.JpaStateMachineRepository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;


@Configuration
@PropertySource({ "classpath:persistence-multiple-db.properties" })
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "org.springframework.statemachine.data.jpa",
        entityManagerFactoryRef = "statemachineEntityManagerFactory",
        transactionManagerRef= "statemachineTransactionManager"
)
@EntityScan("org.springframework.statemachine.data.jpa")
public class PersistenceStateMachineConfiguration {

    @Bean
    @Primary
    @ConfigurationProperties("app.datasource.statemachine")
    public DataSourceProperties statemachineDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties("app.datasource.statemachine.configuration")
    public DataSource statemachineDataSource() {
        return statemachineDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }

    @Primary
    @Bean(name = "statemachineEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean statemachineEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(statemachineDataSource())
                .packages(JpaStateMachineRepository.class)
                .build();
    }

    @Primary
    @Bean
    public PlatformTransactionManager statemachineTransactionManager(
            final @Qualifier("statemachineEntityManagerFactory") LocalContainerEntityManagerFactoryBean statemachineEntityManagerFactory) {
        return new JpaTransactionManager(statemachineEntityManagerFactory.getObject());
    }

}
