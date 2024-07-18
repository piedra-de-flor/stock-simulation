package com.example.stocksimulation.service.account;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component("balanceWebSocketHandler")
@RequiredArgsConstructor
public class WebSocketForBalance extends TextWebSocketHandler {
    private static final ConcurrentHashMap<String, WebSocketSession> CLIENTS = new ConcurrentHashMap<>();

    public void sendMessageToAllClients() {
        TextMessage textMessage = new TextMessage("update balance");

        CLIENTS.forEach((id, session) -> {
            try {
                session.sendMessage(textMessage);
            } catch (IOException e) {
                log.warn("WebSocketServer : send message error", e);
            }
        });
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        CLIENTS.put(session.getId(), session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        CLIENTS.remove(session.getId());
    }
}

