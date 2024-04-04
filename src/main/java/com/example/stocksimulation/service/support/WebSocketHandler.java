package com.example.stocksimulation.service.support;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class WebSocketHandler extends TextWebSocketHandler {
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        // 클라이언트로부터 메시지를 받았을 때의 처리
        System.out.println("Received message from client: " + payload);

        // 클라이언트에게 응답 보내기
        session.sendMessage(new TextMessage("Hello, client! I received your message: " + payload));
    }
}