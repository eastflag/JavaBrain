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

@Configuration
@Lazy
@EnableTransactionManagement //@MapperScan(basePackages = {"com.center.persistence"})
@ConfigurationProperties(prefix = "datasource")
public class DataConfig {
	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private DatasourceProfile profile;

	@Bean(destroyMethod = "close")
	public DataSource dataSource() {
		org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource();
		dataSource.setDriverClassName(profile.getDriverClassName());
		dataSource.setUrl(profile.getUrl());
		dataSource.setUsername(profile.getUserName());
		dataSource.setPassword(profile.getPassword());
		dataSource.setInitialSize(Integer.parseInt(profile.getInitialSize()));
		dataSource.setMaxActive(Integer.parseInt(profile.getMaxActive()));
		dataSource.setMaxIdle(Integer.parseInt(profile.getMaxIdle()));
		dataSource.setMinIdle(Integer.parseInt(profile.getMinIdle()));
		dataSource.setMaxWait(Integer.parseInt(profile.getMaxWait()));
		//validation connections
		dataSource.setValidationQuery(profile.getValidationQuery());
		dataSource.setValidationInterval(Integer.parseInt(profile.getValidationInterval()));
		dataSource.setTestOnBorrow(Boolean.parseBoolean(profile.getTestOnBorrow()));
		return dataSource;
	}

	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
		sessionFactoryBean.setDataSource(dataSource());
		//gradle build로 컴파일 후 실행시에 typeAliases 가 매핑되지 않아서 config를 사용함.
		//sessionFactoryBean.setTypeAliasesPackage("com.center.domain");
		sessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:mybatis/mybatis-config.xml"));
		//sessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mybatis/mapper/**/*.xml"));
		return sessionFactoryBean.getObject();
	}


	@Bean
	public PlatformTransactionManager transactionManager() {
		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource());
		transactionManager.setGlobalRollbackOnParticipationFailure(false);
		return transactionManager;
	}
}
