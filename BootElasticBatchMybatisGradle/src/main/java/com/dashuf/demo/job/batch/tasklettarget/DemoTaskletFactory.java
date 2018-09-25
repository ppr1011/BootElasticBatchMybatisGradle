package com.dashuf.demo.job.batch.tasklettarget;

import org.springframework.batch.core.step.tasklet.MethodInvokingTaskletAdapter;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Tasklet工厂对象
 * @author chenguiqi
 *
 */
@Component
public class DemoTaskletFactory {

	public Tasklet demoTasklet1() {
		MethodInvokingTaskletAdapter tasklet = new MethodInvokingTaskletAdapter();
		tasklet.setTargetObject(new DemoTasklet1());
		tasklet.setTargetMethod("exec");
		tasklet.setArguments(new String[]{"1"});
		return tasklet;
	}
	
	public Tasklet demoTasklet2() {
		MethodInvokingTaskletAdapter tasklet = new MethodInvokingTaskletAdapter();
		tasklet.setTargetObject(new DemoTasklet2());
		tasklet.setTargetMethod("exec");
		tasklet.setArguments(new String[]{"1"});
		return tasklet;
	}
	
	public Tasklet demoTasklet3() {
		MethodInvokingTaskletAdapter tasklet = new MethodInvokingTaskletAdapter();
		tasklet.setTargetObject(new DemoTasklet3());
		tasklet.setTargetMethod("exec");
		tasklet.setArguments(new String[]{"1"});
		return tasklet;
	}
	
	public Tasklet demoTasklet4() {
		MethodInvokingTaskletAdapter tasklet = new MethodInvokingTaskletAdapter();
		tasklet.setTargetObject(new DemoTasklet4());
		tasklet.setTargetMethod("exec");
		tasklet.setArguments(new String[]{"1"});
		return tasklet;
	}
	
}


class DemoTasklet1 implements CommonTasklet{

	@Override
	public void exec(String j) {
		for(int k=1;k<7;k++) {
			System.out.println(1+","+k);
		}
	}
	
}

class DemoTasklet2 implements CommonTasklet{

	@Override
	public void exec(String j) {
		for(int k=1;k<7;k++) {
			System.out.println(2+","+k);
		}
	}
	
}
class DemoTasklet3 implements CommonTasklet{

	@Override
	public void exec(String j) {
		for(int k=1;k<7;k++) {
			System.out.println(3+","+k);
		}
	}
	
}
class DemoTasklet4 implements CommonTasklet{

	@Override
	public void exec(String j) {
		for(int k=1;k<7;k++) {
			System.out.println(4+","+k);
		}	
	}
	
}
