package com.dashuf.demo.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.dashuf.demo.bo.SpecificUser;

public interface SpecificUserDao {

	
	@Insert("insert into specificuser(userId,salary,userPassword,email,location) values(#{userId},#{salary},#{userPassword},#{email},#{location})")
	public void insertSpecificUser(SpecificUser specificUser); 
}
