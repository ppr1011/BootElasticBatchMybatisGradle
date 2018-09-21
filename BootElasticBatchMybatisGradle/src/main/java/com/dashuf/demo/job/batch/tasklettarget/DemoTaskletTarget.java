package com.dashuf.demo.job.batch.tasklettarget;

import org.springframework.stereotype.Component;

@Component
public class DemoTaskletTarget{
	
	
	void exec(String j) {
		for(int i=1;i<100000;i++) {
			System.out.println(Thread.currentThread().toString() + ",i=" +i);
		}
	}
}
