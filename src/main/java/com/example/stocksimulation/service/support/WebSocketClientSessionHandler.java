package com.example.stocksimulation.service.support;

import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.lang.reflect.Type;


public class WebSocketClientSessionHandler extends TextWebSocketHandler {

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        // 서버로부터 받은 응답 메시지 처리
        System.out.println("Received message from server:");
        System.out.println("Message: " + message.getPayload());
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 연결이 성공한 후 실행할 작업 정의
        System.out.println("Connected to WebSocket server");
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        // 전송 오류 처리
        System.out.println("Transport error occurred in WebSocket session:");
        System.out.println("Exception: " + exception);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // 연결 종료 시 처리
        System.out.println("Connection closed.");
    }
}