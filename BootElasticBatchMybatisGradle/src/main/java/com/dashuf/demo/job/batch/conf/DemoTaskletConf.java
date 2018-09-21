package com.dashuf.demo.job.batch.conf;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.core.step.tasklet.MethodInvokingTaskletAdapter;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import com.dashuf.demo.config.BatchJobConfig;
import com.dashuf.demo.job.batch.tasklettarget.DemoTaskletTarget;

@Configuration
@EnableBatchProcessing
@Import(BatchJobConfig.class)
public class DemoTaskletConf {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	@Autowired
	private SqlSessionFactory sqlSessionFactory;

	@Autowired
	private DemoTaskletTarget demoTaskletTarget;
	
	@Bean("demoTasklet")
	public Tasklet demoTasklet() {
		MethodInvokingTaskletAdapter demoTasklet = new MethodInvokingTaskletAdapter();
		demoTasklet.setTargetObject(demoTaskletTarget);
		demoTasklet.setTargetMethod("exec");
		demoTasklet.setArguments(new String[]{"1"});		
		return demoTasklet;
	}
	
	
	@Bean("demoTaskExecutor")
	public TaskExecutor demoTaskExecutor() {
		SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
		return taskExecutor;
	}

	@Bean("demoTaskletBatchJob")
	public Job demoTaskletBatchJob(JobBuilderFactory jobBuilderFactory,@Qualifier("demoTaskletBatchStep")Step step,TaskExecutor taskExecutor) {
		return jobBuilderFactory.get("demoTaskletBatchJob").start(step).build();
	}

	@Bean("demoTaskletBatchStep")
	public Step demoTaskletBatchStep(StepBuilderFactory stepBuilderFactory,@Qualifier("demoTasklet") Tasklet tasklet) {
		return stepBuilderFactory.get("demoTaskletBatchStep").tasklet(tasklet).build();
	}

	
}
