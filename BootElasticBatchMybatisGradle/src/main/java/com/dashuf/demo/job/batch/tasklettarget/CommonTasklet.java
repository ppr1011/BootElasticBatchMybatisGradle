package com.dashuf.demo.job.batch.tasklettarget;

/**
 * 通用接口，作为SpringBatch的Tasklet的调用对象，必须实现此接口
 * @author chenguiqi
 *
 */
public interface CommonTasklet {

	/**
	 * 执行方法
	 * @param j
	 */
	void exec(String j);
}
