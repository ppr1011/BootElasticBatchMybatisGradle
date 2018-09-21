package com.dashuf.demo.job.batch.processor;

import org.springframework.batch.item.ItemProcessor;

import com.dashuf.demo.bo.SpecificUser;
import com.dashuf.demo.bo.User;

public class DemoProcessor implements ItemProcessor<User, SpecificUser>{

	@Override
	public SpecificUser process(User item) throws Exception {
		SpecificUser su = new SpecificUser();
		su.setUserId(item.getUserId());
		su.setSalary(item.getSalary());
		su.setEmail("test" + item.getUserId() + "@dashuf.com");
		su.setLocation("深圳市南山区大冲国际中心24楼");
		su.setUserPassword(item.getUserId());
		System.out.println(su.getUserId()+","+su.getSalary()+","+su.getEmail()+","+su.getLocation());
		return su;
	}
	
}
