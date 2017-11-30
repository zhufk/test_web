package com.zfk.listener;

import org.apache.activemq.command.ActiveMQBytesMessage;
import org.apache.activemq.util.ByteSequence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * <p>Description: 消息监听器</p>
 *
 * @author evan
 * @version 1.0.0
 * <p>Company:workway</p>
 * <p>Copyright:Copyright(c)2017-2018</p>
 * @date 2017/11/16
 */
public class QueueMessageListener implements MessageListener {

    private static final Logger logger = LoggerFactory.getLogger(QueueMessageListener.class);

    @Override
    public void onMessage(Message msg) {
    	if(msg instanceof ActiveMQBytesMessage){
    		ActiveMQBytesMessage message = (ActiveMQBytesMessage)msg;
    		ByteSequence byteSequence = message.getContent();
    		logger.info("QueueMessageListener：ActiveMQBytesMessage接收消息====="+new String(byteSequence.data));
    	}else{
    		TextMessage message = (TextMessage)msg;  
            try {
				logger.info("QueueMessageListener监听到了文本消息：{}", message.getText());
			} catch (JMSException e) {
				logger.error("onMessage_" + e.getMessage(), e);
			}
    	}
      
    }
}