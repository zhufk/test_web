package com.zfk.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

/**
 * UserMapper.xml的接口类实现方式
 * @author ZHUFUKUN
 *
 */
public interface UserMapper2 {
	
	@Select("select * from  zfk_user")
	public List<User> listAllUser2();
	
	@Insert("insert into zfk_user(id,name,age) values (#{id},#{name},#{age})")
	public int insertUser2(User user);
	
	/**
	 * 。。。
	 */
	
}
