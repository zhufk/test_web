package com.zfk.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.zfk.base.entity.ResultData;
import com.zfk.entity.MessageData;
import com.zfk.mq.ConsumerService;
import com.zfk.mq.ProducerService;

@Controller
@RequestMapping("/mq")
public class MqController {

	@Resource
	ProducerService producerService;

	@Resource
	ConsumerService consumerService;

	@RequestMapping({ "send" })
	@ResponseBody
	public ResultData send(MessageData data) {
		ResultData resultData = new ResultData(false);
		// 前台传
		// data.setRobotId("robotId1");
		// data.setRobotName("小龙人1号");
		// data.setTime(new Date());
		// data.setContent(message);
		// data.setUserId("userId1");
		// data.setUserName("朱富昆");
		System.out.println("发送消息到q.one==========>"+data.toString());
		try {
			producerService.sendMessage("q.one", data.toString());
			resultData.setSuccess(true);
		} catch (JMSException e) {
			e.printStackTrace();
		}
		return resultData;
	}

	@RequestMapping({"receive"})
	@ResponseBody
	public List<MessageData> receive(@RequestParam(value="size", required=false) Integer size) throws Exception {
		List<MessageData> list = new ArrayList<MessageData>();
		System.out.println("size#############"+size);
		if (size == null || size == 0) {
			size = 10;
		}
		TextMessage textMsg = null;
		String message = null;
		do {
			textMsg = consumerService.receive("q.two");
			message = textMsg == null ? null : textMsg.getText();
			if (message != null) {
				MessageData data = JSONObject.parseObject(message, MessageData.class);
				list.add(data);
			}
		} while (message != null && list.size() <= size);
		return list;
	}

	@RequestMapping({"list_history_message"})
	@ResponseBody
	public List<MessageData> listHistoryMessage(String userId, @RequestParam(value="size", required=false) Integer size) throws Exception {
		if (size == null || size == 0) {
			size = 10;
		}
		return consumerService.getMessageList4Cache(userId, size);
	}

}
