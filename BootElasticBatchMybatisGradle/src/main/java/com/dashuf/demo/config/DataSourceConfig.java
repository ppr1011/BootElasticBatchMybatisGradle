package com.dashuf.demo.config;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {

    @Bean(name="dataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource(){
    	return DataSourceBuilder.create().build();
    }
	
}
