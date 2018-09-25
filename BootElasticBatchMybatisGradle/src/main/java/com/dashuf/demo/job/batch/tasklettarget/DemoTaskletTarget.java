package com.dashuf.demo.job.batch.tasklettarget;

import org.springframework.stereotype.Component;


/** Tasklet的调用对象样例
 * @see Tasklet
 * @author chenguiqi
 *
 */
@Component
public class DemoTaskletTarget implements CommonTasklet{
	
	
	
	
	@Override
	public void exec(String j) {
		int s = 100000;
		for(int i=1;i<s;i++) {
			System.out.println(Thread.currentThread().toString() + ",i=" +i);
		}
	}
}
