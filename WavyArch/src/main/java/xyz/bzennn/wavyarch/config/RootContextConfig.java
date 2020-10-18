package xyz.bzennn.wavyarch.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan(basePackages = { "xyz.bzennn.wavyarch" }, excludeFilters = {
		@Filter(type = FilterType.ANNOTATION, classes = EnableWebMvc.class) })
@PropertySource(value = "classpath:application.properties", ignoreResourceNotFound = true)
public class RootContextConfig {
	private final Logger log = LogManager.getLogger(RootContextConfig.class);
	
	@Autowired
	private Environment env;

	@Bean
	public DataSource dataSource() {
		log.debug("Initializing dataSource bean");
		
		HikariDataSource dataSource = new HikariDataSource();

		dataSource.setDriverClassName(env.getProperty("db.driver.class"));
		dataSource.setJdbcUrl(env.getProperty("db.url"));
		dataSource.setUsername(env.getProperty("db.username"));
		dataSource.setPassword(env.getProperty("db.password"));
		dataSource.addDataSourceProperty("cachePrepStmts", "true");
		dataSource.addDataSourceProperty("prepStmtCacheSize", "250");
		dataSource.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
		
		log.debug("Initialized dataSource bean");
		
		return dataSource;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
		log.debug("Initializing entityManagerFactory bean");
		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactory.setDataSource(dataSource);
		entityManagerFactory.setPackagesToScan("xyz.bzennn.wavyarch");
		
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		entityManagerFactory.setJpaVendorAdapter(vendorAdapter);
		entityManagerFactory.setJpaProperties(getJpaProperties());
		log.debug("Initialized entityManagerFactory bean");
		
		return entityManagerFactory;
	}
	
	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		log.debug("Initializing transactionManager bean");
		JpaTransactionManager transactionManager = new JpaTransactionManager(entityManagerFactory);
		log.debug("Initialized transactionManager bean");
		
		return transactionManager;
	}
	
	private Properties getJpaProperties() {
		final Properties hibernateProperties = new Properties();
		
		log.debug("Getting hibernate properties");
		hibernateProperties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        hibernateProperties.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
        hibernateProperties.setProperty("hibernate.hikari.connectionTimeout", env.getProperty("hibernate.hikari.connectionTimeout"));
        hibernateProperties.setProperty("hibernate.hikari.minimumIdle", env.getProperty("hibernate.hikari.minimumIdle"));
        hibernateProperties.setProperty("hibernate.hikari.maximumPoolSize", env.getProperty("hibernate.hikari.maximumPoolSize"));
        hibernateProperties.setProperty("hibernate.hikari.idleTimeout", env.getProperty("hibernate.hikari.idleTimeout"));
        
        return hibernateProperties;
	}
}
