package com.zfk.mq;

import java.util.List;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.TextMessage;

import com.zfk.entity.MessageData;

/**
 * <p>
 * Description: 消费都消息服务接口类
 * </p>
 */
public interface ConsumerService{

	/**
	 * 接收消息
	 *
	 * @param destination
	 * @return
	 */
	TextMessage receive(final String destination) throws JMSException;

	/**
	 * 接收消息
	 *
	 * @param destination
	 * @return
	 */
	TextMessage receive(final Destination destination) throws JMSException;
	
	/**
	 * 取出缓存中的信息
	 * 方法用途: <br>
	 * 实现步骤: <br>
	 * @param userId
	 * @param size
	 * @return
	 */
	List<MessageData> getMessageList4Cache(String userId, int size);

}
