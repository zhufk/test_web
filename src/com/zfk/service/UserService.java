package com.zfk.service;

import java.util.List;

import com.zfk.entity.User;

public interface IUserService {
	public List<User> listAllUser();
	
	public List<User> listAllUser2();

	public User getUser(String id);

	public int insertUser(User user);

	public int updateUser(User user);

	public int deleteUser(String id);
}
