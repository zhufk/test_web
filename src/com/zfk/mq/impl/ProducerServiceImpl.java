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

//@Service
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
    public void sendMessage(final String destination, final String msg) throws JMSException{
        logger.info("发送消息------------->"+msg, new Object[]{Thread.currentThread().getName(), destination, msg});
        jmsTemplate.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(msg);
            }
        });
    }
    
    /**
     * 根据目的地发送消息
     *
     * @param destination
     * @param msg
     */
    @Override
    public void sendMessage(final Destination destination, final String msg) throws JMSException{
        logger.info("发送消息-------------->"+msg, new Object[]{Thread.currentThread().getName(), destination.toString(), msg});

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
    public void sendMessage(final String msg) throws JMSException{
        String destination = jmsTemplate.getDefaultDestinationName();

        logger.info("发送消息---------------------->", new Object[]{Thread.currentThread().getName(), destination, msg});

        jmsTemplate.send(new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(msg);
            }
        });
    }
}