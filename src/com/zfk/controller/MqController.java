package com.zfk.controller;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zfk.mq.ConsumerService;
import com.zfk.mq.ProducerService;

@Controller
@RequestMapping("/mq")
public class MqController {
	
	@Resource
	ProducerService producerService;
	
	@Resource
	ConsumerService consumerService;
	
	
	@RequestMapping("/send")
	@ResponseBody
	public String send(String message,String queue) {
		Destination dest = new ActiveMQQueue(queue);
		producerService.sendMessage(dest, message);
		return message;
	}
	
	@RequestMapping("/receive")
	@ResponseBody
	public String receive(String queue) throws JMSException {
		Destination dest = new ActiveMQQueue(queue);
		TextMessage textMsg = consumerService.receive(dest);
		return textMsg == null ? "" : textMsg.getText();
	}
}
