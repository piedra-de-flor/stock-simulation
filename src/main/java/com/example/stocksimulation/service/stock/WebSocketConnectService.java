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
    public WebSocketConnectService(@Qualifier("serverToApiHandler")WebSocketHandler handler) {
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

    public void send(String message) {
        try {
            session.sendMessage(new TextMessage(message));
        } catch (IOException e){
            log.warn("webSocketClient : WebSocket session is not open.");
            log.warn("webSocketClient : ", e);
        }
    }

    public void setSession(WebSocketSession session) {
        this.session = session;
    }
}