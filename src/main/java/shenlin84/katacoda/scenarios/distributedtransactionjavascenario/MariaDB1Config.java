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
import org.springframework.context.annotation.Primary;
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
@EnableJpaRepositories(entityManagerFactoryRef = "mariaDB1EntityManagerFactory", transactionManagerRef = "mariaDB1TransactionManager", basePackages = {
        "shenlin84.katacoda.scenarios.distributedtransactionjavascenario.mariadb1.repo" })
public class MariaDB1Config {


    @Autowired
    private Environment env;
    
    @Primary
    @Bean(name = "mariaDB1DataSource")
    public DataSource mariaDB1DataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("mariadb1.datasource.driver-class-name"));
        dataSource.setUrl(env.getProperty("mariadb1.datasource.url"));
        dataSource.setUsername(env.getProperty("mariadb1.datasource.username"));
        dataSource.setPassword(env.getProperty("mariadb1.datasource.password"));
        return dataSource;
    }

    @Primary
    @Bean(name = "mariaDB1EntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean mariaDB1EntityManagerFactory(EntityManagerFactoryBuilder builder,
            @Qualifier("mariaDB1DataSource") DataSource dataSource) {
        return builder.dataSource(dataSource)
                .packages("shenlin84.katacoda.scenarios.distributedtransactionjavascenario.model")
                .persistenceUnit("userAccount").build();
    }

    @Primary
    @Bean(name = "mariaDB1TransactionManager")
    public PlatformTransactionManager mariaDB1TransactionManager(
            @Qualifier("mariaDB1EntityManagerFactory") EntityManagerFactory mariaDB1EntityManagerFactory) {
        return new JpaTransactionManager(mariaDB1EntityManagerFactory);
    }
}