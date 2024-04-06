package com.example.stocksimulation.service.support;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.SimpleAsyncTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

@Slf4j
@Component
public class WebSocketClientSessionHandler extends TextWebSocketHandler {
    private final String HEARTBEAT_MESSAGE = "HEARTBEAT";
    private final TaskScheduler taskScheduler = new SimpleAsyncTaskScheduler();
    private WebSocketSession session;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        // 서버로부터 받은 응답 메시지 처리
        log.info("webSocketClient : Received message from server");
        System.out.println("Message: " + message.getPayload());
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 연결이 성공한 후 실행할 작업 정의
        log.info("webSocketClient : Connected to WebSocket server");
        this.session = session;

        taskScheduler.scheduleAtFixedRate(() -> {
            try {
                sendHeartbeat();
                System.out.println("heart-beat");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, 80000);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        // 전송 오류 처리
        log.warn("webSocketClient : Transport error occurred in WebSocket session = ", exception);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // 연결 종료 시 처리
        System.out.println("Connection closed.");
    }

    private void sendHeartbeat() throws IOException {
        if (session != null && session.isOpen()) {
            session.sendMessage(new TextMessage(HEARTBEAT_MESSAGE));
        } else {
            System.out.println("WebSocket session is not open.");
        }
    }
}