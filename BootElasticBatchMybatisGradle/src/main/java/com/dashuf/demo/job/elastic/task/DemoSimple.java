package com.dashuf.demo.job.elastic.task;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.PlatformTransactionManager;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dashuf.demo.bo.SpecificUser;
import com.dashuf.demo.bo.User;
import com.dashuf.demo.support.SpringUtil;

public class DemoSimple implements SimpleJob{

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	@Qualifier("demoOddBatchJob")
	private Job demoOddBatchJob;
	
	@Autowired
	@Qualifier("demoEvenBatchJob")
	private Job demoEvenBatchJob;

	@Autowired
	@Qualifier("demoBatchJob")
	private Job demoBatchJob;

	
	@Autowired
	@Qualifier("demoBatchXmlJob")
	private Job demoBatchXmlJob;
	
	
	@Autowired
	@Qualifier("demoTaskletBatchJob")
	private Job demoTaskletBatchJob;
	
	@Value("${simplejob.jobname:NoName}")
	private String name;
	
	public String getName() {
		return name;
	}

	@Override
	public void execute(ShardingContext shardingContext) {
//		System.out.println(String.format(
//				"------Thread ID: %s, 任务总片数: %s, " + "当前分片项: %s.当前分片参数: %s," + "当前任务名称: %s.当前任务参数: %s",
//				Thread.currentThread().getId(), shardingContext.getShardingTotalCount(),
//				shardingContext.getShardingItem(), shardingContext.getShardingParameter(),
//				shardingContext.getJobName(), shardingContext.getJobParameter()
//
//		));
		JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
				.toJobParameters();		
		String start = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		long startTime = System.currentTimeMillis();
		
		try {
//			if (shardingContext.getShardingParameter().equals("even")) {
//				jobLauncher.run(demoEvenBatchJob, jobParameters);
//			} else {
//				jobLauncher.run(demoOddBatchJob, jobParameters);
//			}
//			jobLauncher.run(demoBatchJob, jobParameters);			
//			jobLauncher.run(demoTaskletBatchJob, jobParameters);
			
			jobLauncher.run(demoBatchXmlJob, jobParameters);

		} catch (JobExecutionAlreadyRunningException e) {
			e.printStackTrace();
		} catch (JobRestartException e) {
			e.printStackTrace();
		} catch (JobInstanceAlreadyCompleteException e) {
			e.printStackTrace();
		} catch (JobParametersInvalidException e) {
			e.printStackTrace();
		}
		finally {
			String end = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			long endTime = System.currentTimeMillis();
			System.out.println("开始时间："+ start);
			System.out.println("结束时间："+ end);
			System.out.printf("持续:%d\n", endTime-startTime);
			
		}
		
		
	}

}
