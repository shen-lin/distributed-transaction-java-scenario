package shenlin84.katacoda.scenarios.distributedtransactionjavascenario;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ComponentScan
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "mariaDB2EntityManagerFactory", transactionManagerRef = "mariaDB2TransactionManager", basePackages = {
        "shenlin84.katacoda.scenarios.distributedtransactionjavascenario.mariadb2.repo" })

public class MariaDB2Config {

    @Autowired
    private Environment env;

    @Bean(name = "mariaDB2DataSource")
    public DataSource mariaDB1DataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("mariadb2.datasource.driver-class-name"));
        dataSource.setUrl(env.getProperty("mariadb2.datasource.url"));
        dataSource.setUsername(env.getProperty("mariadb2.datasource.username"));
        dataSource.setPassword(env.getProperty("mariadb2.datasource.password"));
        return dataSource;
    }

    @Bean(name = "mariaDB2EntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean mariaDB2EntityManagerFactory(EntityManagerFactoryBuilder builder,
            @Qualifier("mariaDB2DataSource") DataSource dataSource) {
        return builder.dataSource(dataSource)
                .packages("shenlin84.katacoda.scenarios.distributedtransactionjavascenario.model")
                .persistenceUnit("userAccount").build();
    }

    @Bean(name = "mariaDB2TransactionManager")
    public PlatformTransactionManager mariaDB2TransactionManager(
            @Qualifier("mariaDB2EntityManagerFactory") EntityManagerFactory mariaDB2EntityManagerFactory) {
        return new JpaTransactionManager(mariaDB2EntityManagerFactory);
    }
}