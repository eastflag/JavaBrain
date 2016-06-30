package com.eastflag.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Lazy
@EnableTransactionManagement //@MapperScan(basePackages = {"com.center.persistence"})
@ConfigurationProperties(locations = "classpath:application.yml")
public class DataConfig {
	@Autowired
	private ApplicationContext applicationContext;

	//application.yml을 읽어들인다. Map으로 읽을려면 ConfigurationProperties를 사용
/*	@Value("${datasource.driver-class-name}") String driverClassName;
	@Value("${datasource.url}") String url;
	@Value("${datasource.user-name}") String userName;
	@Value("${datasource.password}") String password;
	@Value("${datasource.initial-size}") int initialSize;
	@Value("${datasource.max-active}") int maxActive;
	@Value("${datasource.max-idle}") int maxIdle;
	@Value("${datasource.min-idle}") int minIdle;
	@Value("${datasource.max-wait}") int maxWait;*/

	//application.yml을 읽어들인다. Map으로 읽을려면 ConfigurationProperties를 사용
	public Map<String, String> datasource = new HashMap<>();

	//getter 함수가 반드시 필요하다.
	public Map<String, String> getDatasource() {
		return datasource;
	}

	@Bean(destroyMethod = "close")
	public DataSource dataSource() {
		org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource();
		dataSource.setDriverClassName(datasource.get("driver-class-name"));
		dataSource.setUrl(datasource.get("url"));
		dataSource.setUsername(datasource.get("user-name"));
		dataSource.setPassword(datasource.get("password"));
		dataSource.setInitialSize(Integer.parseInt(datasource.get("initial-size")));
		dataSource.setMaxActive(Integer.parseInt(datasource.get("max-active")));
		dataSource.setMaxIdle(Integer.parseInt(datasource.get("max-idle")));
		dataSource.setMinIdle(Integer.parseInt(datasource.get("min-idle")));
		dataSource.setMaxWait(Integer.parseInt(datasource.get("max-wait")));
		//validation connections
		dataSource.setValidationQuery(datasource.get("validationQuery"));
		dataSource.setValidationInterval(Integer.parseInt(datasource.get("validationInterval")));
		dataSource.setTestOnBorrow(Boolean.parseBoolean(datasource.get("testOnBorrow")));
		return dataSource;
	}

	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
		sessionFactoryBean.setDataSource(dataSource());
		sessionFactoryBean.setTypeAliasesPackage("com.eastflag.domain");
		//sessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:mybatis/mybatis-config.xml"));
		sessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mybatis/mapper/**/*.xml"));
		return sessionFactoryBean.getObject();
	}


	@Bean
	public PlatformTransactionManager transactionManager() {
		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource());
		transactionManager.setGlobalRollbackOnParticipationFailure(false);
		return transactionManager;
	}
}
