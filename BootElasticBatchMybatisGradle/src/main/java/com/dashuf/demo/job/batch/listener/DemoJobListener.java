package com.dashuf.demo.job.batch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;


public class DemoJobListener implements JobExecutionListener{

	long startTime;
	long endTime;
	
	Logger logger = LoggerFactory.getLogger(DemoJobListener.class);
	
	
	@Override
	public void beforeJob(JobExecution jobExecution) {
		logger.info("job start...");
		this.startTime = System.currentTimeMillis();
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		logger.info("job end");
		this.endTime = System.currentTimeMillis();
		logger.info("last time:{}",endTime-startTime);
	}
}