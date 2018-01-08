package com.zfk.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zfk.entity.MessageData;
import com.zfk.mq.MessageCache;

@Controller
@RequestMapping("/chat")
public class ChatController {

	@Resource
	MessageCache messageCache;

	@RequestMapping({ "chat" })
	public String index(ModelMap modelMap) {
		return "chat/chat";
	}

	@RequestMapping({ "list_history_user_robot" })
	@ResponseBody
	public List<MessageData> listHistoryUserRobot(@RequestBody Map<String, Object> map) {
		String userId = map.get("userId").toString();
		String robotId = map.get("robotId").toString();
		int size = Integer.parseInt(map.get("size").toString());
		// int page = Integer.parseInt(map.get("page").toString());
		return messageCache.getMessageListByUserIdAndRobotId(userId+"-"+robotId, size);
	}
	
	@RequestMapping({ "list_history_user" })
	@ResponseBody
	public List<List<MessageData>> listHistoryUser(@RequestBody Map<String, Object> map) {
		String userId = map.get("userId").toString();
		int size = Integer.parseInt(map.get("size").toString());
		// int page = Integer.parseInt(map.get("page").toString());
		return messageCache.getMessageListByUserId(userId, size);
	}
}
