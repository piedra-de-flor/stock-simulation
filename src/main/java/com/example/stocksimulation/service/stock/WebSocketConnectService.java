package com.example.stocksimulation.service.stock;

import com.example.stocksimulation.domain.vo.StockCodes;
import com.example.stocksimulation.domain.vo.WebSocketParsingInfo;
import com.example.stocksimulation.service.support.WebSocketServerToApiHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.socket.WebSocketSession;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebSocketConnectService {
    private final String SOCKET_URL = WebSocketParsingInfo.WEB_SOCKET_CLIENT_URL.getValue();
    private WebSocketServerToApiHandler handler;
    private final StockService service;

    public void connect() {
        WebSocketClient webSocketClient = new ReactorNettyWebSocketClient();
        handler = new WebSocketServerToApiHandler(service);

        webSocketClient.execute(URI.create(SOCKET_URL), handler)
                .doOnError(e -> log.warn("webSocketClient : Error connecting to WebSocket server", e))
                .doOnTerminate(() -> log.info("webSocketClient : Connection terminated"))
                .subscribe();
    }

    public void send(String[] messages) {
        StockCodes stockCodes = new StockCodes();
        String[] codes = stockCodes.getCodes().split("=");
        List<String> list = new ArrayList<>();

        for (String code : codes) {
            list.add(messages[0] + code + messages[1]);
        }

        WebSocketSession session = handler.getSession();

        if (session != null && session.isOpen()) {
            for (String message : list) {
                session.send(Mono.just(session.textMessage(message)))
                        .doOnError(e -> log.warn("webSocketClient : Error sending message", e))
                        .subscribe();
            }
        } else {
            log.warn("webSocketClient : WebSocket session is not open or is null.");
        }
    }
}
