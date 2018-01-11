package com.zfk.mqtest;

import java.util.Date;
import java.util.Scanner;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.activemq.command.ActiveMQBytesMessage;
import org.apache.activemq.util.ByteSequence;

import com.zfk.base.util.DateUtils;
import com.zfk.entity.MessageData;
import com.zfk.mqtest.base.Receiver;
import com.zfk.mqtest.base.Sender;


public class Robot2 {
	public static void main(String[] args) throws JMSException {
		new Thread(){
			@Override
			public void run() {
				Sender sender = new Sender();
				sender.init("robot-user",2);
				for (int i=1;i<1000;i++) {
					Scanner scan = new Scanner(System.in);
					System.out.println("Robot2：请输入信息：回车");
					String lineStr = scan.nextLine();
					if(lineStr.equals("exit")){
		    			break;
		    		}
					MessageData data = new MessageData();
					data.setRobotId("robotId2");
					data.setRobotName("小龙人2号");
					data.setUserId("userId1");
					data.setUserName("朱富昆");
					data.setType("0");
					data.setContent("你好，我是小龙人2号-"+i);
					data.setTime(DateUtils.formatDate(new Date(), DateUtils.SECOND));
					String msg = data.toString();
		    		try {
		    			System.out.println("Robot2发送信息:"+msg);
						sender.sendMessage(msg);
					} catch (Exception e) {
						e.printStackTrace();
					}
		    	}
				sender.destroy();
				System.exit(0);
			}
		}.start();
		
		Receiver receiver = new Receiver();
		receiver.init("user-robot",2);
		receiver.receive(new MessageListener(){
            @Override
            public void onMessage(Message msg) {  
            	if(msg instanceof ActiveMQBytesMessage){
            		ActiveMQBytesMessage message = (ActiveMQBytesMessage)msg;
            		ByteSequence byteSequence = message.getContent();
            		System.out.println("Robot2：接收消息====="+new String(byteSequence.data));
            	}else{
            		TextMessage message = (TextMessage)msg;  
                    try {
    					System.out.println("Robot2：接收消息=====" + message.getText());
    				} catch (JMSException e) {
    					e.printStackTrace();
    				}
            	}
                
            }
        });
	}
}