package com.zfk.mqtest;

import java.util.Date;
import java.util.Scanner;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.activemq.command.ActiveMQBytesMessage;
import org.apache.activemq.util.ByteSequence;

import com.zfk.entity.MessageData;
import com.zfk.mqtest.base.Receiver;
import com.zfk.mqtest.base.Sender;


public class Robot1 {
	public static void main(String[] args) throws JMSException {
		new Thread(){
			@Override
			public void run() {
				Sender sender = new Sender();
				sender.init("q.one",1);
				for (int i=1;i<1000;i++) {
					Scanner scan = new Scanner(System.in);
					System.out.println("Robot1：请输入信息：回车");
					String content = scan.nextLine();
					//String content = "我是小龙人—"+i;
					if(content.equals("exit")){
		    			break;
		    		}
					MessageData data = new MessageData();
					data.setRobotId("robotId1");
					data.setRobotName("小龙人1号");
					data.setTime(new Date());
					data.setContent(content);
					String msg = data.toString();
		    		try {
		    			System.out.println("Robot1发送信息:"+msg);
						sender.sendMessage(msg);
					} catch (JMSException e) {
						e.printStackTrace();
					}
		    		try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
		    	}
				sender.destroy();
				//System.exit(0);
			}
		}.start();
		
		Receiver receiver = new Receiver();
		receiver.init("q.two",1);
		receiver.receive(new MessageListener(){
            @Override
            public void onMessage(Message msg) {  
            	if(msg instanceof ActiveMQBytesMessage){
            		ActiveMQBytesMessage message = (ActiveMQBytesMessage)msg;
            		ByteSequence byteSequence = message.getContent();
            		System.out.println("Robot1：接收消息1====="+new String(byteSequence.data));
            	}else{
            		TextMessage message = (TextMessage)msg;  
                    try {
    					System.out.println("Robot1：接收消息2=====" + message.getText());
    				} catch (JMSException e) {
    					e.printStackTrace();
    				}
            	}
            }
        });
	}
}
