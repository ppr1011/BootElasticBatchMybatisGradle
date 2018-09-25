package com.dashuf.demo.job.batch.conf;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
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
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.dashuf.demo.bo.SpecificUser;
import com.dashuf.demo.bo.User;
import com.dashuf.demo.config.BatchJobConfig;
import com.dashuf.demo.job.batch.listener.DemoJobListener;
import com.dashuf.demo.job.batch.processor.DemoProcessor;


/**
 * 读取所有ID
 * @author chenguiqi
 *
 */
@Configuration
@EnableBatchProcessing
@Import(BatchJobConfig.class)
public class DemoJobConf {

	private static final Log logger = LogFactory.getLog(DemoJobConf.class);

	@Autowired
	private SqlSessionFactory sqlSessionFactory;

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	@Bean("demoReader")
	public ItemReader<User> reader() {
		MyBatisPagingItemReader<User> reader = new MyBatisPagingItemReader<User>();
		reader.setPageSize(2000);
		reader.setSqlSessionFactory(sqlSessionFactory);
		reader.setQueryId("com.dashuf.demo.dao.UserDao.selectUsers");
		return reader;
	}

	@Bean("demoProcessor")
	public ItemProcessor<User, SpecificUser> processor() {
		DemoProcessor processor = new DemoProcessor();
		return processor;
	}

	@Bean("demoWriter")
	public ItemWriter<SpecificUser> writer() {
		MyBatisBatchItemWriter<SpecificUser> writer = new MyBatisBatchItemWriter<SpecificUser>();
		writer.setSqlSessionFactory(sqlSessionFactory);
		writer.setSqlSessionTemplate(sqlSessionTemplate);
		writer.setStatementId("com.dashuf.demo.dao.SpecificUserDao.insertSpecificUser");
		return writer;
	}

	@Bean("demoBatchJob")
	public Job demoBatchJob(JobBuilderFactory jobBuilderFactory, @Qualifier("demoBatchStep") Step step) {
		return jobBuilderFactory.get("demoBatchJob").incrementer(new RunIdIncrementer()).flow(step).end()
				.listener(new DemoJobListener()).build();
	}

	@Bean("demoBatchStep")
	public Step step(StepBuilderFactory stepBuilderFactory, @Qualifier("demoReader") ItemReader<User> reader,
			@Qualifier("demoWriter") ItemWriter<SpecificUser> writer,
			@Qualifier("demoProcessor") ItemProcessor<User, SpecificUser> processor) {
		return stepBuilderFactory.get("demoBatchStep").<User, SpecificUser>chunk(2000).reader(reader)
				.processor(processor).writer(writer).build();
	}

}
