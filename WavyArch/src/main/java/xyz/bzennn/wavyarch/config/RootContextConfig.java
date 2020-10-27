package xyz.bzennn.wavyarch.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.zaxxer.hikari.HikariDataSource;

/**
 * Root Spring application context configuration file.
 * 
 * @author bzennn
 * @version 1.0
 */
@Configuration
@ComponentScan(basePackages = { "xyz.bzennn.wavyarch" }, excludeFilters = {
		@Filter(type = FilterType.ANNOTATION, classes = EnableWebMvc.class) })
@PropertySource(value = "classpath:database.properties", ignoreResourceNotFound = true)
public class RootContextConfig {

	@Autowired
	private Environment env;

	@Bean
	public DataSource dataSource() {
		HikariDataSource dataSource = new HikariDataSource();

		dataSource.setDriverClassName(env.getProperty("db.driver.class"));
		dataSource.setJdbcUrl(env.getProperty("db.url"));
		dataSource.setUsername(env.getProperty("db.username"));
		dataSource.setPassword(env.getProperty("db.password"));
		dataSource.addDataSourceProperty("cachePrepStmts", "true");
		dataSource.addDataSourceProperty("prepStmtCacheSize", "250");
		dataSource.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

		return dataSource;
	}

	@Bean
	public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);
		sessionFactory.setPackagesToScan(new String[] { "xyz.bzennn.wavyarch" });
		sessionFactory.setHibernateProperties(getJpaProperties());

		return sessionFactory;
	}

	private Properties getJpaProperties() {
		final Properties hibernateProperties = new Properties();

		hibernateProperties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
		hibernateProperties.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
		hibernateProperties.setProperty("hibernate.hikari.connectionTimeout",
				env.getProperty("hibernate.hikari.connectionTimeout"));
		hibernateProperties.setProperty("hibernate.hikari.minimumIdle",
				env.getProperty("hibernate.hikari.minimumIdle"));
		hibernateProperties.setProperty("hibernate.hikari.maximumPoolSize",
				env.getProperty("hibernate.hikari.maximumPoolSize"));
		hibernateProperties.setProperty("hibernate.hikari.idleTimeout",
				env.getProperty("hibernate.hikari.idleTimeout"));

		return hibernateProperties;
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder(11);
	}
}
