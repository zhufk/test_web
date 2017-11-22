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

/**
 * <p>Description: </p>
 *
 * @author evan
 * @version 1.0.0
 * <p>Company:workway</p>
 * <p>Copyright:Copyright(c)2017-2018</p>
 * @date 2017/11/16
 */
@Service
public class ConsumerServiceImpl implements ConsumerService {
    private static final Logger logger = LoggerFactory.getLogger(ConsumerServiceImpl.class);


    @Resource
    private JmsTemplate jmsTemplate;

    @Override
    public TextMessage receive(Destination destination) {
        TextMessage textMessage = (TextMessage) jmsTemplate.receive(destination);
        try {
            logger.info("从队列{}, 收到了消息：{}", new Object[]{destination.toString(), textMessage.getText()});
        } catch (JMSException e) {
            logger.error("receive_" + e.getMessage(), e);
        }
        return textMessage;
    }
}