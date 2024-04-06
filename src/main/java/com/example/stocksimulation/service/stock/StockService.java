package com.example.stocksimulation.service.stock;

import com.example.stocksimulation.configuration.WebSocketClientConfig;
import com.example.stocksimulation.service.support.WebSocketClientSessionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Service
public class StockService {
    private WebSocketSession session;

    public void connectAndSendRequest() throws IOException, ExecutionException, InterruptedException {
        StandardWebSocketClient webSocketClient = new StandardWebSocketClient();
        session = webSocketClient.doHandshake(new WebSocketClientSessionHandler(), "ws://ops.koreainvestment.com:21000/tryitout/H0STCNT0").get();
        System.out.println("WebSocket client connected to server.");
    }

    public void test() throws IOException, ExecutionException, InterruptedException {
        connectAndSendRequest();
    }

    public void send(String message) throws IOException {
        if (session != null && session.isOpen()) {
            session.sendMessage(new TextMessage(message));
        } else {
            System.out.println("WebSocket session is not open.");
        }
    }
}