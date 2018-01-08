package com.zfk.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zfk.base.util.PropertyUtils;

@Controller
public class IndexController {
	
	@RequestMapping({"index"})
	public String index(ModelMap modelMap) {
		modelMap.addAttribute("publicPath", PropertyUtils.getConfig("publicPath"));
		modelMap.addAttribute("appPath", PropertyUtils.getConfig("appPath"));
		
		Map<String, Object> user = new HashedMap<String, Object>();
		user.put("userId", "userId1");
		user.put("loginName", "朱富昆");
		user.put("userName", "朱富昆");
		user.put("loginTime", new Date());
		modelMap.addAttribute("user", user);
		
		//菜单
		Map<String,Object> cMenuItem1 = new HashMap<String, Object>();
		cMenuItem1.put("caption", "聊天");
		cMenuItem1.put("url", "chat/chat");
		Map<String,Object> cMenuItem2 = new HashMap<String, Object>();
		cMenuItem2.put("caption", "聊天2");
		cMenuItem2.put("url", "chat/chat2");
		
		List<Map<String,Object>> cMenuItems = new ArrayList<Map<String,Object>>();
		cMenuItems.add(cMenuItem1);
		cMenuItems.add(cMenuItem2);
		
		Map<String,Object> menuItem = new HashMap<String, Object>();
		menuItem.put("caption", "人工客服");
		menuItem.put("child", cMenuItems);
		
		List<Map<String,Object>> menuItems = new ArrayList<Map<String,Object>>();
		menuItems.add(menuItem);
		
		modelMap.addAttribute("menu", menuItems);
		
		return "index";
	}
	
	
}
