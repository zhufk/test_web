package com.zfk.websocket.handler;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

public class MyWebSocketHandler implements WebSocketHandler {
	private static int onlineCount = 0;

	// concurrent包的线程安全Set，用来存放每个客户端对应的WebSocketSession对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
	private static CopyOnWriteArraySet<WebSocketSession> SessionSet = new CopyOnWriteArraySet<WebSocketSession>();

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		session.sendMessage(new TextMessage("Server:connected OK!"));
		System.out.println("有新连接加入！当前在线人数为" + (++onlineCount));
		sendMessage2All(new TextMessage("有新连接加入！当前在线人数为" + onlineCount));
		SessionSet.add(session);
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> msg) throws Exception {
		TextMessage returnMessage = new TextMessage(msg.getPayload().toString());
		sendMessage2All(returnMessage);
	}

	// 群发消息
	public void sendMessage2All(TextMessage message) throws IOException {
		for (WebSocketSession ss : SessionSet) {
			ss.sendMessage(message);
		}
	}

	/**
	 * 给某个用户发送消息
	 *
	 * @param userName
	 * @param message
	 */
	public static void sendMessageToUser(String userName, TextMessage message) {
		for (WebSocketSession session : SessionSet) {
			if (session.getHandshakeAttributes().get("userName").equals(userName)) {
				try {
					if (session.isOpen()) {
						session.sendMessage(message);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			}
		}
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable thrwbl) throws Exception {
		if (session.isOpen()) {
			session.close();
		}
		System.out.println("websocket connection closed......");
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus cs) throws Exception {
		System.out.println("有一连接关闭！当前在线人数为" + (--onlineCount));
		SessionSet.remove(session);
		sendMessage2All(new TextMessage("有一连接关闭！当前在线人数为" + onlineCount));

	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}
}
