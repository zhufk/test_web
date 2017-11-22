package com.zfk.mq;

import javax.jms.Destination;

/**
 * <p>Description: 生产者服务接口类</p>
 *
 * @author evan
 * @version 1.0.0
 * <p>Company:workway</p>
 * <p>Copyright:Copyright(c)2017-2018</p>
 * @date 2017/11/16
 */
public interface ProducerService {

    /**
     * 根据目的地发送消息
     *
     * @param destination
     * @param msg
     */
    void sendMessage(Destination destination, final String msg);

    /**
     * 发送到默认的目的地
     *
     * @param msg
     */
    void sendMessage(final String msg);
}
