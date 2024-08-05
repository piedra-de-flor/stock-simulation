package com.example.stocksimulation.service.stock;

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

@Slf4j
@Service
@RequiredArgsConstructor
public class WebSocketConnectService {
    private final String SOCKET_URL = WebSocketParsingInfo.WEB_SOCKET_CLIENT_URL.getValue();
    private final WebSocketClient webSocketClient = new ReactorNettyWebSocketClient();
    private final WebSocketServerToApiHandler handler;

    public void connect() {
        webSocketClient.execute(URI.create(SOCKET_URL), handler)
                .doOnError(e -> log.warn("webSocketClient : Error connecting to WebSocket server", e))
                .doOnTerminate(() -> log.info("webSocketClient : Connection terminated"))
                .subscribe();
    }

    public void send(String[] messages) {
        for (String m : messages) {
            System.out.println(m);
        }

        WebSocketSession session = handler.getSession();
        if (session != null && session.isOpen()) {
            for (String message : messages) {
                session.send(Mono.just(session.textMessage(message)))
                        .doOnError(e -> log.warn("webSocketClient : Error sending message", e))
                        .subscribe();
            }
        } else {
            log.warn("webSocketClient : WebSocket session is not open or is null.");
        }
    }
}

/*
package com.example.stocksimulation.service.stock;

import com.example.stocksimulation.domain.vo.WebSocketParsingInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
public class WebSocketConnectService {
    private final String socketUrl = WebSocketParsingInfo.WEB_SOCKET_CLIENT_URL.getValue();
    private final WebSocketHandler handler;
    private WebSocketSession session;

    @Autowired
    public WebSocketConnectService(WebSocketHandler handler) {
        this.handler = handler;
    }

    public void connect() {
        try {
            StandardWebSocketClient webSocketClient = new StandardWebSocketClient();
            setSession(webSocketClient.execute(handler, socketUrl).get());
        } catch (ExecutionException | InterruptedException e) {
            log.warn("webSocketClient : ", e);
        }
    }

    public void send(String[] messages) {
        for (String message : messages) {
            try {
                session.sendMessage(new TextMessage(message));
            } catch (IOException e){
                log.warn("webSocketClient : WebSocket session is not open.");
                log.warn("webSocketClient : ", e);
            }
        }
    }

    public void setSession(WebSocketSession session) {
        this.session = session;
    }
}*/
