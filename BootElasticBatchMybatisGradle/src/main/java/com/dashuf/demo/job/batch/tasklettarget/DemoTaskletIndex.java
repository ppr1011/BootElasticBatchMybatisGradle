package com.dashuf.demo.job.batch.tasklettarget;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DemoTaskletIndex {

	@Bean
	public DemoTasklet1 demoTasklet1() {
		return new DemoTasklet1();
	}
	
	@Bean
	public DemoTasklet2 demoTasklet2() {
		return new DemoTasklet2();
	}
	
	@Bean
	public DemoTasklet3 demoTasklet3() {
		return new DemoTasklet3();
	}
	
	@Bean
	public DemoTasklet4 demoTasklet4() {
		return new DemoTasklet4();
	}
	
}


class DemoTasklet1 implements CommonTasklet{

	@Override
	public void exec(String j) {
		System.out.println(1);
		
	}
	
}

class DemoTasklet2 implements CommonTasklet{

	@Override
	public void exec(String j) {
		System.out.println(2);
		
	}
	
}
class DemoTasklet3 implements CommonTasklet{

	@Override
	public void exec(String j) {
		System.out.println(3);
		
	}
	
}
class DemoTasklet4 implements CommonTasklet{

	@Override
	public void exec(String j) {
		System.out.println(4);
		
	}
	
}
