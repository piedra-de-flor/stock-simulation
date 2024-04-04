package com.example.stocksimulation.service.stock;

import com.example.stocksimulation.configuration.WebSocketClientConfig;
import com.example.stocksimulation.service.support.WebSocketClientSessionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;

@RequiredArgsConstructor
@Service
public class StockService {
    private final WebSocketStompClient webSocketStompClient;
    private final StompSessionHandler stompSessionHandler;

    public void connectAndSendRequest() {
        // WebSocket 서버에 연결
        webSocketStompClient.connect("ws://ops.koreainvestment.com:21000/tryitout/H0STCNT0", stompSessionHandler);

        System.out.println("WebSocket client connected to server.");
    }

    public static void main(String[] args) {
        WebSocketClientConfig clientConfig = new WebSocketClientConfig();
        WebSocketStompClient webSocketStompClient = clientConfig.webSocketStompClient(clientConfig.stompSessionHandler());
        // StockService 인스턴스 생성
        StockService stockService = new StockService(webSocketStompClient, clientConfig.stompSessionHandler());

        // WebSocket 서버에 연결하고 요청을 보내기
        stockService.connectAndSendRequest();

    }
}