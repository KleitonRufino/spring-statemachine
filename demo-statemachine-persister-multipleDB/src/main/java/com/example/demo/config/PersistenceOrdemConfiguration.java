package com.example.demo.config;


import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.demo.model.Ordem;


@Configuration
@PropertySource({ "classpath:persistence-multiple-db.properties" })
@EnableJpaRepositories(basePackages = "com.example.demo.repository",
        entityManagerFactoryRef = "ordemEntityManagerFactory",
        transactionManagerRef= "ordemTransactionManager")
//@EntityScan("com.example.demo")
public class PersistenceOrdemConfiguration {

    @Bean
    @ConfigurationProperties("app.datasource.ordem")
    public DataSourceProperties ordemDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("app.datasource.ordem.configuration")
    public DataSource ordemDataSource() {
        return ordemDataSourceProperties().initializeDataSourceBuilder()
                .type(BasicDataSource.class).build();
    }

    @Bean(name = "ordemEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean ordemEntityManagerFactory(
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(ordemDataSource())
                .packages(Ordem.class)
                .build();
    }

    @Bean
    public PlatformTransactionManager ordemTransactionManager(
            final @Qualifier("ordemEntityManagerFactory") LocalContainerEntityManagerFactoryBean ordemEntityManagerFactory) {
        return new JpaTransactionManager(ordemEntityManagerFactory.getObject());
    }

}
