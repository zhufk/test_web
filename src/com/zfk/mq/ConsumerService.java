package com.zfk.mq;

import javax.jms.Destination;
import javax.jms.TextMessage;

/**
 * <p>Description: 消费都消息服务接口类</p>
 *
 * @author evan
 * @version 1.0.0
 * <p>Company:workway</p>
 * <p>Copyright:Copyright(c)2017-2018</p>
 * @date 2017/11/16
 */
public interface ConsumerService {

    /**
     * 接收消息
     *
     * @param destination
     * @return
     */
    TextMessage receive(Destination destination);
}
