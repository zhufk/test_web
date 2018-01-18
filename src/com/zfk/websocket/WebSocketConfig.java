package com.zfk.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.zfk.websocket.handler.MyWebSocketHandler;
import com.zfk.websocket.handler.WebsocketEndPoint;
import com.zfk.websocket.interceptor.HandshakeInterceptor;

@Configuration
@EnableWebMvc
@EnableWebSocket
public class WebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {
	public WebSocketConfig() {
	}

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(new WebsocketEndPoint(), "/websck").addInterceptors(new HandshakeInterceptor());
		System.out.println("registed!");
		registry.addHandler(new WebsocketEndPoint(), "/sockjs/websck").addInterceptors(new HandshakeInterceptor())
				.withSockJS();
	}

}
