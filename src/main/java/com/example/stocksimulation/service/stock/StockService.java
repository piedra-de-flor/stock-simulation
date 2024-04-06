package com.example.stocksimulation.service.stock;

import com.example.stocksimulation.domain.vo.WebSocketClientVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.SimpleAsyncTaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Slf4j
@RequiredArgsConstructor
@Service
public class StockService {
    private final String socketUrl = WebSocketClientVO.WEB_SOCKET_CLIENT_URL.getValue();
    private final WebSocketHandler handler;
    private WebSocketSession session;

    public void connect() {
        try {
            StandardWebSocketClient webSocketClient = new StandardWebSocketClient();
            session = webSocketClient.execute(handler, socketUrl).get();
        } catch (ExecutionException | InterruptedException e) {
            log.warn("webSocketClient : ", e);
        }
    }

    public void send(String message) {
        try {
            session.sendMessage(new TextMessage(message));
        } catch (IOException e){
            log.warn("webSocketClient : WebSocket session is not open.");
            log.warn("webSocketClient : ", e);
        }
    }
}