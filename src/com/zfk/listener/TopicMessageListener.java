package com.zfk.listener;

import java.io.IOException;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.activemq.command.ActiveMQBytesMessage;
import org.apache.activemq.util.ByteSequence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zfk.entity.MessageData;
import com.zfk.mq.MessageCache;


public class TopicMessageListener implements MessageListener{
	
	private static final Logger logger = LoggerFactory.getLogger(TopicMessageListener.class);
	
	@Resource
	MessageCache messageCache;
	
	@Override
    public void onMessage(Message msg) {
		if(msg instanceof ActiveMQBytesMessage){
    		ActiveMQBytesMessage message = (ActiveMQBytesMessage)msg;
    		ByteSequence byteSequence = message.getContent();
    		String text = new String(byteSequence.data);
    		logger.info("TopicMessageListener：ActiveMQBytesMessage接收消息====="+text);
			try {
				MessageData data = new ObjectMapper().readValue(text, MessageData.class);
				messageCache.offerMessage(data.getUserId(), data);
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}else{
    		TextMessage message = (TextMessage)msg;  
            try {
            	String text = message.getText();
            	MessageData data = new ObjectMapper().readValue(text, MessageData.class);
				messageCache.offerMessage(data.getUserId(), data);
				logger.info("TopicMessageListener监听到了文本消息：{}", text);
			} catch (IOException e) {
				logger.error("onMessage_" + e.getMessage(), e);
			} catch (JMSException e) {
				logger.error("onMessage_" + e.getMessage(), e);
			}
    	}
    }
}
