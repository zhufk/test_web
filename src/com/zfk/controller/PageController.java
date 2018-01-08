package com.zfk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PageController {

	@RequestMapping("/page/{page}")
	public String page(@PathVariable String page) {
		System.out.println("page###########"+page);
		return page;
	}
	
	@RequestMapping("/page")
	public String page2(@RequestParam String page) {
		System.out.println("page###########"+page);
		return page;
	}
	
}
