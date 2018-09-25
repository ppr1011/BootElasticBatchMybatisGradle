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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.dashuf.demo.bo.SpecificUser;
import com.dashuf.demo.bo.User;
import com.dashuf.demo.config.BatchJobConfig;
import com.dashuf.demo.job.batch.listener.DemoJobListener;
import com.dashuf.demo.job.batch.processor.DemoProcessor;


/**
 * SpringBatch读取奇数列的Job
 * @author chenguiqi
 *
 */
@Configuration
@EnableBatchProcessing
@Import(BatchJobConfig.class)
public class DemoOddJobConf {

	private static final Log logger = LogFactory.getLog(DemoOddJobConf.class);

	@Autowired
	private SqlSessionFactory sqlSessionFactory;

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	@Bean("demoOddReader")
	public ItemReader<User> oddReader() {
		MyBatisPagingItemReader<User> reader = new MyBatisPagingItemReader<User>();
		reader.setPageSize(2000);
		reader.setSqlSessionFactory(sqlSessionFactory);
		reader.setQueryId("com.dashuf.demo.dao.UserDao.selectUsersByOddId");
		return reader;
	}

	@Bean("demoOddProcessor")
	public ItemProcessor<User, SpecificUser> processor() {
		DemoProcessor processor = new DemoProcessor();
		return processor;
	}

	@Bean("demoOddWriter")
	public ItemWriter<SpecificUser> writer() {
		MyBatisBatchItemWriter<SpecificUser> writer = new MyBatisBatchItemWriter<SpecificUser>();
		writer.setSqlSessionFactory(sqlSessionFactory);
		writer.setSqlSessionTemplate(sqlSessionTemplate);
		writer.setStatementId("com.dashuf.demo.dao.SpecificUserDao.insertSpecificUser");
		return writer;
	}

	@Bean("demoOddBatchJob")
	public Job demoBatchJob(JobBuilderFactory jobBuilderFactory, @Qualifier("demoOddBatchStep") Step step) {
		return jobBuilderFactory.get("demoOddBatchJob").incrementer(new RunIdIncrementer()).flow(step).end().listener(new DemoJobListener()).build();
	}

	@Bean("demoOddBatchStep")
	public Step step(StepBuilderFactory stepBuilderFactory, @Qualifier("demoOddReader") ItemReader<User> reader,
			@Qualifier("demoOddWriter") ItemWriter<SpecificUser> writer,
			@Qualifier("demoOddProcessor") ItemProcessor<User, SpecificUser> processor) {
		return stepBuilderFactory.get("demoOddBatchStep").<User, SpecificUser>chunk(2000).reader(reader)
				.processor(processor).writer(writer).build();
	}
}
