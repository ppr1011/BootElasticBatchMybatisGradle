package com.dashuf.demo.job.batch.conf;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.dashuf.demo.bo.SpecificUser;
import com.dashuf.demo.bo.User;
import com.dashuf.demo.config.BatchJobConfig;
import com.dashuf.demo.job.batch.listener.DemoJobListener;
import com.dashuf.demo.job.batch.processor.DemoProcessor;

@Configuration
@EnableBatchProcessing
@Import(BatchJobConfig.class)
public class DemoEvenJobConf {

	private static final Log logger = LogFactory.getLog(DemoEvenJobConf.class);

	@Autowired
	private SqlSessionFactory sqlSessionFactory;

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	// @Value("${path.csvfilepath}")
	// private String filepath;

	// @Bean
	// public ItemReader<User> reader(String filepath) {
	// FlatFileItemReader<User> reader = new FlatFileItemReader<User>();
	// Resource resource = new PathResource(filepath);
	// reader.setResource(resource);
	// DefaultLineMapper<User> lineMapper = new DefaultLineMapper<User>();
	// DelimitedLineTokenizer delimitedLineTokenizer = new
	// DelimitedLineTokenizer();
	// String[] properties = new String[2];
	// properties[0] = "userId";
	// properties[1] = "salary";
	// delimitedLineTokenizer.setNames(properties);
	// BeanWrapperFieldSetMapper<User> beanWrapperFieldSetMapper = new
	// BeanWrapperFieldSetMapper<User>();
	// beanWrapperFieldSetMapper.setTargetType(User.class);
	// lineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);
	// lineMapper.setLineTokenizer(delimitedLineTokenizer);
	// reader.setLineMapper(lineMapper);
	// return reader;
	// }

	@Bean("demoEvenReader")
	public ItemReader<User> evenReader() {
		MyBatisPagingItemReader<User> reader = new MyBatisPagingItemReader<User>();
		reader.setPageSize(2000);
		reader.setSqlSessionFactory(sqlSessionFactory);
		reader.setQueryId("com.dashuf.demo.dao.UserDao.selectUsersByEvenId");
		return reader;
	}

	@Bean("demoEvenProcessor")
	public ItemProcessor<User, SpecificUser> processor() {
		DemoProcessor processor = new DemoProcessor();
		return processor;
	}

	@Bean("demoEvenWriter")
	public ItemWriter<SpecificUser> writer() {
		MyBatisBatchItemWriter<SpecificUser> writer = new MyBatisBatchItemWriter<SpecificUser>();
		writer.setSqlSessionFactory(sqlSessionFactory);
		writer.setSqlSessionTemplate(sqlSessionTemplate);
		writer.setStatementId("com.dashuf.demo.dao.SpecificUserDao.insertSpecificUser");
		return writer;
	}

	@Bean("demoEvenBatchJob")
	public Job DemoBatchJob(JobBuilderFactory jobBuilderFactory, @Qualifier("demoEvenBatchStep") Step step) {
		return jobBuilderFactory.get("demoEvenBatchJob").incrementer(new RunIdIncrementer()).flow(step).end().listener(new DemoJobListener()).build();
	}

	@Bean("demoEvenBatchStep")
	public Step step(StepBuilderFactory stepBuilderFactory, @Qualifier("demoEvenReader") ItemReader<User> reader,
			@Qualifier("demoEvenWriter") ItemWriter<SpecificUser> writer,
			@Qualifier("demoEvenProcessor") ItemProcessor<User, SpecificUser> processor) {
		return stepBuilderFactory.get("demoEvenBatchStep").<User, SpecificUser>chunk(2000).reader(reader)
				.processor(processor).writer(writer).build();
	}
}
