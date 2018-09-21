package com.dashuf.demo.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.event.rdb.JobEventRdbConfiguration;


@Configuration
@MapperScan("com.dashuf.demo.dao")
public class MybatisConfig {
	
	@Autowired
	private DataSource dataSource;

	@Bean
	public DataSourceTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource);
	}

	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		factoryBean.setDataSource(dataSource);
		return factoryBean.getObject();
	}

	@Bean
	public SqlSessionTemplate sqlSessionBatchTemplate() throws Exception {
		SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactory(),ExecutorType.BATCH);
		return template;
	}
	
    @Bean
    public JobEventConfiguration jobEventConfiguration() {
        return new JobEventRdbConfiguration(dataSource);
    }
}
