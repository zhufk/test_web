package com.zfk.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zfk.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Resource
	private UserService userService;

	@RequestMapping("/listAllUser")
	public @ResponseBody Object listAllUser(HttpServletRequest request) {
		return userService.listAllUser();
	}

	@RequestMapping("/listAllUser2")
	public @ResponseBody Object listAllUser2(HttpServletRequest request) {
		return userService.listAllUser2();
	}

}
