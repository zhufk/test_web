package com.zfk.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/redis")
public class RedisController {
	
	@RequestMapping({"add"})
	public String add(@RequestBody Map<String, Object> map) {
		
		
		
		return null;
	}
}
