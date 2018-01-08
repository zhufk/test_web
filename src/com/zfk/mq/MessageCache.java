package com.zfk.mq;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.zfk.entity.MessageData;

@Component
public class MessageCache {

	private static Map<String, LinkedList<MessageData>> cacheMap = new ConcurrentHashMap<String, LinkedList<MessageData>>();

	private static final int queueLimitSize = 100;

	/**
	 * 添加用户与机器人相关的信息
	 * 
	 * @param userIdAndRobotId 用户ID-机器人ID
	 * @param data 信息数据
	 */
	public void offerMessage(String userIdAndRobotId, MessageData data) {
		if (cacheMap.get(userIdAndRobotId) != null) {
			offerLimit(cacheMap.get(userIdAndRobotId), data, queueLimitSize);
		} else {
			LinkedList<MessageData> queue = new LinkedList<MessageData>();
			queue.offer(data);
			cacheMap.put(userIdAndRobotId, queue);
		}
	}

	private void offerLimit(LinkedList<MessageData> queue, MessageData data, int queueLimitSize) {
		queue.offer(data);
		while (queue.size() > queueLimitSize) {
			queue.poll();
		}
	}

	/**
	 * 取出与用户和机器人相关的信息不删除
	 * 
	 * @param userIdAndRobotId 用户ID-机器人ID
	 * @param size 数量
	 * @return
	 */
	public List<MessageData> getMessageListByUserIdAndRobotId(String userIdAndRobotId, int size) {
		List<MessageData> list = new ArrayList<MessageData>();
		LinkedList<MessageData> queue = cacheMap.get(userIdAndRobotId);
		if (queue != null) {
			for (int i = queue.size() > size ? queue.size() - size : 0; list.size() < size && i < queue.size(); i++) {
				list.add(queue.get(i));
			}
		} 
		return list;
	}
	
	/**
	 * 取出与用户相关的信息不删除
	 * 
	 * @param userId 用户ID
	 * @param size 数量
	 * @return
	 */
	public List<List<MessageData>> getMessageListByUserId(String userId, int size) {
		List<List<MessageData>> reList = new ArrayList<List<MessageData>>();
		
		Set<String> keys = cacheMap.keySet();
		Iterator<String> it = keys.iterator();
		while(it.hasNext()){
			String key = it.next();
			if(key.contains(userId)){
				LinkedList<MessageData> queue = cacheMap.get(key);
				List<MessageData> list = new ArrayList<MessageData>();
				if (queue != null) {
					for (int i = queue.size() > size ? queue.size() - size : 0; list.size() < size && i < queue.size(); i++) {
						list.add(queue.get(i));
					}
					reList.add(list);
				}
			}
		}
		return reList;
	}

	/**
	 * 取出用户与机器人相关的信息
	 * 
	 * @param userIdAndRobotId 用户ID-机器人ID
	 * @param size 数量
	 * @return
	 */
	public List<MessageData> pollMessageListByUserIdAndRobotId(String userIdAndRobotId, int size) {
		List<MessageData> list = new ArrayList<MessageData>();
		LinkedList<MessageData> queue = cacheMap.get(userIdAndRobotId);
		if (queue != null) {
			while (!queue.isEmpty() && list.size() < size) {
				list.add(queue.poll());
			}
		}
		return list;
	}

	// 取出一条信息
	public MessageData pollMessage(String userIdAndRobotId) {
		LinkedList<MessageData> queue = cacheMap.get(userIdAndRobotId);
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
			ms.offerMessage("userId1-"+data.getRobotId(), data);
		}
		System.out.println(ms.getMessageListByUserId("userId1", 10));
		System.out.println(ms.getMessageListByUserIdAndRobotId("userId1-robotId1", 10));
		
	}

}
