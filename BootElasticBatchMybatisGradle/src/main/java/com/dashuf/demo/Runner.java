package com.dashuf.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@ImportResource(locations={"classpath:com/dashuf/demo/spring-batch-job-db2file.xml"})
@SpringBootApplication
public class Runner {
	
	public static void main(String[] args) {
		SpringApplication.run(Runner.class, args);
		System.out.println(Thread.currentThread().getName() + " MainThread end!");		
	}
}
