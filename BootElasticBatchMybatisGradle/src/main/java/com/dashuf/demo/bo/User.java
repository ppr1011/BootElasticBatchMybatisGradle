package com.dashuf.demo.bo;



public class User extends BaseEntity{
	
	private String userId;
	
	private long salary;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public long getSalary() {
		return salary;
	}
	public void setSalary(long salary) {
		this.salary = salary;
	}
	
	
	@Override
	public String[] getProperties() {
		
		String[] properties = new String[2];
		properties[0] = "userId";
		properties[1] = "salary";
		return properties;
	}
	@Override
	public void setProperties(String[] arr) {
		// TODO Auto-generated method stub
		
	}
	
	

}
