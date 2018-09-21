package com.dashuf.demo.bo;

public class SpecificUser extends BaseEntity {

	
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

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	private String userId;
	
	private long salary;
	
	private String userPassword;
	
	private String email;
	
	private String location;
	
	
	
	
	
	@Override
	public String[] getProperties() {
		// TODO Auto-generated method stub
		String[] properties = new String[5];
		properties[0] = "userId";
		properties[1] = "salary";
		properties[2] = "userPassword";
		properties[3] = "email";
		properties[4] = "location";
		
		return null;
	}

	@Override
	public void setProperties(String[] arr) {
		// TODO Auto-generated method stub

	}

}
