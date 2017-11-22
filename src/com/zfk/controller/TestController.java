package com.zfk.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zfk.service.mq.ProducerService;

@Controller
@RequestMapping("/test")
public class TestController {
	
	//@Resource
	ProducerService producerService;
	
	
	@RequestMapping("/send")
	@ResponseBody
	public String test1(String message) {
		
		Destination dest = new org.apache.activemq.command.ActiveMQQueue("q.one");
		producerService.sendMessage(message+"1");
		producerService.sendMessage(dest, message+"2");
		
		return "ok";
	}
	
	@RequestMapping("/test1")
	public String test1(HttpServletRequest request) {
		return "test1";
	}

	@RequestMapping("/test2")
	public ModelAndView test2(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("test");
		modelAndView.addObject("info", "朱富昆啊");
		modelAndView.addObject("info2", Arrays.asList("朱富昆", "小龙"));
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("name", "朱富昆");
		map.put("age", "25");
		modelAndView.addObject("info3", map);
		return modelAndView;
	}
	
	
	@RequestMapping({"test3"})
    public String test3(ModelMap modelMap) {
        modelMap.addAttribute("info", "朱富昆");
        return "test";
    }

	@RequestMapping("/rest1")
	@ResponseBody
	public Object rest1(String name) {
		Map<String, Object> user = new HashMap<String, Object>();
		user.put("name", "朱富昆"+name);
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
	
	@RequestMapping("/rest2/{name}")
	@ResponseBody
	public Object rest2(@PathVariable String name) {
		Map<String, Object> user = new HashMap<String, Object>();
		user.put("name", "朱富昆"+name);
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
}
