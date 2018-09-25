package com.dashuf.demo.bo;

/**
 * 基本对象
 * @author chenguiqi
 *
 */
public abstract class BaseEntity {
	
	
	/**
	 * 获得实体的属性数组
	 * @return
	 */
	public abstract String[] getProperties();
	public abstract void setProperties(String[] arr);
	
}
