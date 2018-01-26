package com.zfk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zfk.entity.User;
import com.zfk.redis.RedisUtils;

@Controller
@RequestMapping("/redis")
public class RedisController {

	@RequestMapping({ "add" })
	@ResponseBody
	// public Object add(@RequestBody Map<String, Object> map) {
	public Object add(String id, String name, Integer age) {

		User user = new User();
		user.setId(id);
		user.setName(name);
		user.setAge(age);

		return RedisUtils.setObject(user.getId(), user);
	}

	@RequestMapping({ "get" })
	@ResponseBody
	public User get(String id) {
		return (User) RedisUtils.getObject(id);
	}
}
