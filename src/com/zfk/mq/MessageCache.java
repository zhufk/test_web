package com.zfk.mq;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.zfk.entity.MessageData;

@Component
public class MessageCache {

	private static Map<String, LinkedList<MessageData>> cacheMap = new ConcurrentHashMap<String, LinkedList<MessageData>>();

	private static final int queueLimitSize = 100;

	/**
	 * 添加信息 方法用途: <br>
	 * 实现步骤: <br>
	 * 
	 * @param userId
	 * @param data
	 */
	public void offerMessage(String userId, MessageData data) {
		if (cacheMap.get(userId) != null) {
			offerLimit(cacheMap.get(userId), data, queueLimitSize);
		} else {
			LinkedList<MessageData> queue = new LinkedList<MessageData>();
			queue.offer(data);
			cacheMap.put(userId, queue);
		}
	}

	private void offerLimit(LinkedList<MessageData> queue, MessageData data, int queueLimitSize) {
		queue.offer(data);
		while (queue.size() > queueLimitSize) {
			queue.poll();
		}
	}

	/**
	 * 取出信息不删除
	 * 
	 * @param userId
	 * @param size
	 * @return
	 */
	public List<MessageData> getMessageList(String userId, int size) {
		List<MessageData> list = new ArrayList<MessageData>();
		LinkedList<MessageData> queue = cacheMap.get(userId);
		if (queue != null) {
			for (int i = queue.size() > size ? queue.size() - size : 0; list.size() < size && i < queue.size(); i++) {
				list.add(queue.get(i));
			}
		}
		return list;
	}

	/**
	 * 取出信息
	 * 
	 * @param userId
	 * @param size
	 * @return
	 */
	public List<MessageData> pollMessageList(String userId, int size) {
		List<MessageData> list = new ArrayList<MessageData>();
		LinkedList<MessageData> queue = cacheMap.get(userId);
		if (queue != null) {
			while (!queue.isEmpty() && list.size() < size) {
				list.add(queue.poll());
			}
		}
		return list;
	}

	// 取出一条信息
	public MessageData pollMessage(String userId) {
		LinkedList<MessageData> queue = cacheMap.get(userId);
		if (queue != null) {
			return queue.poll();
		}
		return null;
	}
	
	public static void main(String[] args) {
		MessageCache ms = new MessageCache();
		for(int i= 1;i<=9;i++){
			MessageData data = new MessageData();
			data.setRobotId("robotId"+i);
			ms.offerMessage("userId1", data);
		}
		System.out.println(ms.getMessageList("userId1", 10));
		
	}

}
