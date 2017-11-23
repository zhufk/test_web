package com.zfk.mq.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.zfk.mq.ConsumerService;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.TextMessage;

@Service
public class ConsumerServiceImpl implements ConsumerService {
	private static final Logger logger = LoggerFactory.getLogger(ConsumerServiceImpl.class);

	@Resource
	private JmsTemplate jmsTemplate;

	@Override
	public TextMessage receive(final String destination) throws JMSException{
		TextMessage textMessage = (TextMessage) jmsTemplate.receive(destination);
		logger.info("收到destination消息------------>"+textMessage, new Object[] { destination, textMessage });
		return textMessage;
	}

	@Override
	public TextMessage receive(final Destination destination) throws JMSException{
		TextMessage textMessage = (TextMessage) jmsTemplate.receive(destination);
		logger.info("收到了消息------------>"+textMessage, new Object[] { destination.toString(), textMessage });
		return textMessage;
	}
}