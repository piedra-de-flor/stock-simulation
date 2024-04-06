package com.example.stocksimulation.service.stock;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.SimpleAsyncTaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@RequiredArgsConstructor
@Service
public class StockService {
    private final String HEARTBEAT_MESSAGE = "HEARTBEAT";
    private final TaskScheduler taskScheduler = new SimpleAsyncTaskScheduler();
    private final WebSocketHandler handler;
    private WebSocketSession session;

    public void connect() throws ExecutionException, InterruptedException {
        StandardWebSocketClient webSocketClient = new StandardWebSocketClient();
        session = webSocketClient.doHandshake(handler, "ws://ops.koreainvestment.com:21000/tryitout/H0STCNT0").get();
        System.out.println("WebSocket client connected to server.");

        taskScheduler.scheduleAtFixedRate(() -> {
            try {
                sendHeartbeat();
                System.out.println("heart-beat");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, 80000);
    }

    public void test() throws IOException, ExecutionException, InterruptedException {
        connect();
    }

    public void send(String message) throws IOException {
        if (session != null && session.isOpen()) {
            session.sendMessage(new TextMessage(message));
        } else {
            System.out.println("WebSocket session is not open.");
        }
    }

    private void sendHeartbeat() throws IOException {
        if (session != null && session.isOpen()) {
            session.sendMessage(new TextMessage(HEARTBEAT_MESSAGE));
        } else {
            System.out.println("WebSocket session is not open.");
        }
    }
}