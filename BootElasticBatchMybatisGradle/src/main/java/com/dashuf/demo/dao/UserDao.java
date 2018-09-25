package com.dashuf.demo.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.dashuf.demo.bo.User;

/**
 * 用户表DAO
 * @author chenguiqi
 *
 */
public interface UserDao {

	
	@Select("select * from user where mod(userId,2)=0 limit #{_pagesize} OFFSET #{_skiprows}")
	public List<User> selectUsersByEvenId();
	
	@Select("select * from user where mod(userId,2)!=0 limit #{_pagesize} OFFSET #{_skiprows}")
	public List<User> selectUsersByOddId();
	
	@Select("select * from user limit #{_pagesize} OFFSET #{_skiprows}")
	public List<User> selectUsers();
	
	/**
	 * 通用分片DAO
	 * @return
	 */
	@Select("select * from user where mod(userId,10)=#{_modNum} limit #{_pagesize} OFFSET #{_skiprows}")
	public List<User> selectUsersByModNum();
	
}
