package com.zfk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {
	
	@RequestMapping("/index/{page}")
	public String index(@PathVariable String page) {
		System.out.println("page###########"+page);
		return page;
	}
	
	@RequestMapping("/index")
	public String index2(@RequestParam String page) {
		System.out.println("page###########"+page);
		return page;
	}
}
