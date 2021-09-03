package com.sysmap.firstcall.config;

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

import com.sysmap.firstcall.FirstCallApplication;


@Configuration
@PropertySource({ "classpath:persistence-mydb.properties" })
@EnableJpaRepositories(basePackages = "com.sysmap.firstcall.repository",
        entityManagerFactoryRef = "mydbEntityManagerFactory",
        transactionManagerRef= "mydbTransactionManager")
public class PersistenceMyDBConfiguration {

    @Bean
    @ConfigurationProperties("app.datasource.mydb")
    public DataSourceProperties mydbDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("app.datasource.mydb.configuration")
    public DataSource mydbDataSource() {
        return mydbDataSourceProperties().initializeDataSourceBuilder()
                .type(BasicDataSource.class).build();
    }

    @Bean(name = "mydbEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean mydbEntityManagerFactory(
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(mydbDataSource())
                .packages(FirstCallApplication.class)
                .build();
    }

    @Bean
    public PlatformTransactionManager mydbTransactionManager(
            final @Qualifier("mydbEntityManagerFactory") LocalContainerEntityManagerFactoryBean mydbEntityManagerFactory) {
        return new JpaTransactionManager(mydbEntityManagerFactory.getObject());
    }

}