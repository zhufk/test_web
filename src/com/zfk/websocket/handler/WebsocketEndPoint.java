package com.zfk.websocket.handler;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class WebsocketEndPoint extends TextWebSocketHandler {
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("connect to the websocket success......WebsocketEndPoint");
		session.sendMessage(new TextMessage("Server:connected OK!"));
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		super.handleTextMessage(session, message);
		TextMessage returnMessage = new TextMessage(message.getPayload() + " received at server WebsocketEndPoint");
		session.sendMessage(returnMessage);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus cs) throws Exception {
		System.out.println("websocket connection closed......WebsocketEndPoint");
	}
}
