package com.zfk.dao;

import java.util.List;

import com.zfk.entity.User;

/**
 * spring整合mybatic用，
 * 类名UserMapper与UserMapper.xml中的mapper标签中的namespace值对应;
 * 方法名与UserMapper.xml与select...标签中的id对应
 * @author ZFK
 *
 */
public interface UserMapper {

	public List<User> listAllUser();
	
	public User getUser(String id);
	
	public int insertUser(User user);
	
	public int updateUser(User user);
	
	public int deleteUser(String id);
}
