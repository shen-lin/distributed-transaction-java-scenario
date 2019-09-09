package shenlin84.katacoda.scenarios.distributedtransactionjavascenario.datasource;

import java.util.HashMap;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

public class DataSourceMaria1 {
        @Autowired
        private Environment env;

        @Primary
        @Bean
        public LocalContainerEntityManagerFactoryBean db1EntityManager() {
                LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
                em.setDataSource(db1Datasource());
                em.setPersistenceUnitName("db1EntityManager");
                HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
                em.setJpaVendorAdapter(vendorAdapter);
                HashMap<String, Object> properties = new HashMap<>();
                properties.put("hibernate.dialect",
                        env.getProperty("hibernate.dialect"));
                properties.put("hibernate.show-sql",
                        env.getProperty("jdbc.show-sql"));
                em.setJpaPropertyMap(properties);
                return em;
        }

        @Primary
        @Bean
        public DataSource db1Datasource() {

                DriverManagerDataSource dataSource = new DriverManagerDataSource();
                dataSource.setDriverClassName(env.getProperty("mariadb1.datasource.driver-class-name"));
                dataSource.setUrl(env.getProperty("mariadb1.datasource.url"));
                dataSource.setUsername(env.getProperty("mariadb1.datasource.username"));
                dataSource.setPassword(env.getProperty("mariadb1.datasource.password"));

                return dataSource;
        }

        @Primary
        @Bean
        public PlatformTransactionManager db1TransactionManager() {

                JpaTransactionManager transactionManager = new JpaTransactionManager();
                transactionManager.setEntityManagerFactory(db1EntityManager().getObject());
                return transactionManager;
        }
}