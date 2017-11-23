package com.zfk.mq;

import javax.jms.Destination;
import javax.jms.JMSException;

/**
 * <p>
 * Description: 生产者服务接口类
 * </p>
 */
public interface ProducerService {

	/**
	 * 根据目的地发送消息
	 *
	 * @param destination
	 * @param msg
	 * @throws Exception 
	 */
	void sendMessage(final String destination, final String msg) throws JMSException;

	/**
	 * 根据目的地发送消息
	 *
	 * @param destination
	 * @param msg
	 */
	void sendMessage(final Destination destination, final String msg) throws JMSException;

	
	/**
	 * 发送到默认的目的地
	 *
	 * @param msg
	 */
	void sendMessage(final String msg) throws JMSException;
}
