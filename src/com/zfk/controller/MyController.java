package com.zfk.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zfk.service.IUserService;

@Controller
@RequestMapping("/myC")
public class MyController {

	@Resource
	private IUserService userService;

	@RequestMapping("/index")
	public String index(HttpServletRequest request) {
		return "/WEB-INF/pages/index.jsp";
	}

	@RequestMapping("/index2")
	public ModelAndView index2(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("/WEB-INF/pages/index.jsp");
		modelAndView.addObject("info", "朱富昆啊");
		modelAndView.addObject("info2", Arrays.asList("朱富昆", "小龙"));
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("name", "朱富昆");
		map.put("age", "25");
		modelAndView.addObject("info3", map);
		return modelAndView;
	}

	@RequestMapping("/rest1")
	public @ResponseBody Object rest1(HttpServletRequest request) {
		Map<String, Object> user = new HashMap<String, Object>();
		user.put("name", "朱富昆");
		user.put("age", "25");
		// 朋友
		List<Map> friends = new ArrayList<Map>();
		Map<String, Object> friend = new HashMap<String, Object>();
		friend.put("name", "小龙");
		friend.put("age", "23");
		// 添加朋友
		user.put("friends", friends);
		return user;
	}

	@RequestMapping("/listAllUser")
	public @ResponseBody Object listAllUser(HttpServletRequest request) {
		return userService.listAllUser();
	}

	@RequestMapping("/listAllUser2")
	public @ResponseBody Object listAllUser2(HttpServletRequest request) {
		return userService.listAllUser2();
	}

}
