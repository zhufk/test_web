package com.zfk.mq.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.zfk.mq.ProducerService;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

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
public class ProducerServiceImpl implements ProducerService {

    private static final Logger logger = LoggerFactory.getLogger(ProducerServiceImpl.class);

    @Resource
    private JmsTemplate jmsTemplate;
    
    /**
     * 根据目的地发送消息
     *
     * @param destination
     * @param msg
     */
    @Override
    public void sendMessage(Destination destination, final String msg) {
        logger.info("{} 向队列{} ,发送消息---------------------->", new Object[]{Thread.currentThread().getName(), destination.toString(), msg});

        jmsTemplate.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(msg);
            }
        });
    }

    /**
     * 发送到默认的目的地
     *
     * @param msg
     */
    @Override
    public void sendMessage(final String msg) {
        String destination = jmsTemplate.getDefaultDestinationName();

        logger.info("{} 向队列{} ,发送消息---------------------->", new Object[]{Thread.currentThread().getName(), destination, msg});

        jmsTemplate.send(new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(msg);
            }
        });
    }
}