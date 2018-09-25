package com.dashuf.demo.job.batch.conf;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.task.TaskExecutor;

import com.dashuf.demo.config.BatchJobConfig;
import com.dashuf.demo.job.batch.tasklettarget.DemoTaskletFactory;

/**
 * SpringBatch任务编排
 * 
 * @author chenguiqi
 *
 */

@Configuration
@EnableBatchProcessing
@Import(BatchJobConfig.class)
public class DemoOrderJobsConf {

	@Autowired
	private DemoTaskletFactory demoTaskletFactory;

	@Bean("demoBatchOrderJob")
	public Job demoTaskletBatchJob(JobBuilderFactory jobBuilderFactory, @Qualifier("demoBatchOrderStep1") Step step1,
			@Qualifier("demoBatchOrderStep2") Step step2, @Qualifier("demoBatchOrderStep3") Step step3,
			@Qualifier("demoBatchOrderStep4") Step step4) {
		return jobBuilderFactory.get("demoBatchOrderJob").flow(step1).next(step2).next(step3).next(step4).end().build();
	}

	@Bean("demoBatchOrderStep1")
	public Step demoTaskletBatchStep1(StepBuilderFactory stepBuilderFactory) {
		return stepBuilderFactory.get("demoBatchOrderStep1").tasklet(demoTaskletFactory.demoTasklet1()).build();
	}

	@Bean("demoBatchOrderStep2")
	public Step demoTaskletBatchStep2(StepBuilderFactory stepBuilderFactory) {
		return stepBuilderFactory.get("demoBatchOrderStep2").tasklet(demoTaskletFactory.demoTasklet2()).build();
	}

	@Bean("demoBatchOrderStep3")
	public Step demoTaskletBatchStep3(StepBuilderFactory stepBuilderFactory) {
		return stepBuilderFactory.get("demoBatchOrderStep3").tasklet(demoTaskletFactory.demoTasklet3()).build();
	}

	@Bean("demoBatchOrderStep4")
	public Step demoTaskletBatchStep4(StepBuilderFactory stepBuilderFactory) {
		return stepBuilderFactory.get("demoBatchOrderStep4").tasklet(demoTaskletFactory.demoTasklet4()).build();
	}
}
