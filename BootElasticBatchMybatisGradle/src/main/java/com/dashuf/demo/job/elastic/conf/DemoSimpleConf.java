package com.dashuf.demo.job.elastic.conf;

import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.dashuf.demo.job.elastic.handler.DemoHandler;
import com.dashuf.demo.job.elastic.task.DemoSimple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
public class DemoSimpleConf {

	@Resource
	private ZookeeperRegistryCenter regCenter1;

	@Resource
	private JobEventConfiguration jobEventConfiguration;

	@Bean("demoSimpleJob")
	public SimpleJob demoSimpleJob() {
		return new DemoSimple();
	}

	@Bean(initMethod = "init",name="demoSimpleJobScheduler")
	public JobScheduler simpleJobScheduler(@Qualifier("demoSimpleJob")SimpleJob simpleJob, @Value("${simpleJob.cron}")String cron,
			@Value("${simpleJob.shardingTotalCount}")int shardingTotalCount,
			@Value("${simpleJob.shardingItemParameters}")String shardingItemParameters) {
		return new SpringJobScheduler(simpleJob, regCenter1,
				getLiteJobConfiguration(simpleJob.getClass(), cron, shardingTotalCount, shardingItemParameters),
				jobEventConfiguration);
	}

	private LiteJobConfiguration getLiteJobConfiguration(final Class<? extends SimpleJob> jobClass, final String cron,
			final int shardingTotalCount, final String shardingItemParameters) {
		return LiteJobConfiguration
				.newBuilder(
						new SimpleJobConfiguration(
								JobCoreConfiguration.newBuilder(jobClass.getName(), cron, shardingTotalCount)
										.shardingItemParameters(shardingItemParameters).build(),
								jobClass.getCanonicalName()))
				.overwrite(true).build();
	}
}