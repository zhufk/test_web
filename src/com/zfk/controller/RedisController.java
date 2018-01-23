package com.zfk.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zfk.entity.User;
import com.zfk.redis.service.UserRedisService;

@Controller
@RequestMapping("/redis")
public class RedisController {

	@Resource
	UserRedisService userRedisService;

	@RequestMapping({ "add" })
	@ResponseBody
	// public Object add(@RequestBody Map<String, Object> map) {
	public Object add(String id, String name, Integer age) {

		User user = new User();
		user.setId(id);
		user.setName(name);
		user.setAge(age);

		return userRedisService.insertUser(user);
	}

	@RequestMapping({ "get" })
	@ResponseBody
	public Object get(String id) {
		return userRedisService.getUser(id);
	}
}
