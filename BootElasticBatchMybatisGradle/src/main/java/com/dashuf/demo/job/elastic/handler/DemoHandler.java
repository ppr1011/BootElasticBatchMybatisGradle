package com.dashuf.demo.job.elastic.handler;

import org.springframework.stereotype.Component;

import com.dangdang.ddframe.job.executor.handler.JobExceptionHandler;

@Component
public class DemoHandler implements JobExceptionHandler {

	@Override
	public void handleException(String jobName, Throwable cause) {
		//System.out.println("异常方法： " + cause.getStackTrace()[0].getMethodName());
		System.out.println(String.format("任务[%s]调度异常", jobName) + ",异常类型： " + cause.toString());
	}

}
