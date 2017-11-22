package com.zfk.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zfk.dao.UserMapper;
import com.zfk.dao.UserMapper2;
import com.zfk.entity.User;
import com.zfk.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Resource
	UserMapper userMapper;

	@Resource
	UserMapper2 userMapper2;

	public List<User> listAllUser() {
		return userMapper.listAllUser();
	}

	public List<User> listAllUser2() {
		return userMapper2.listAllUser2();
	}

	public User getUser(String id) {
		return null;
	}

	@Transactional
	public int insertUser(User user) {
		return 0;
	}

	@Transactional
	public int updateUser(User user) {
		return 0;
	}

	@Transactional
	public int deleteUser(String id) {
		return 0;
	}

}
