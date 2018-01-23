package com.zfk.redis.service;

import java.util.List;

import com.zfk.entity.User;

public interface UserRedisService {
	
	public int insertUser(User user);

	public int insertUserBatch(List<User> users);

	public int updateUser(User user);
	
	public int updateUserBatch(List<User> users);

	public int deleteUser(String id);
	
	public int deleteUserBatch(List<String> ids);
	
	public User getUser(String id);
}
