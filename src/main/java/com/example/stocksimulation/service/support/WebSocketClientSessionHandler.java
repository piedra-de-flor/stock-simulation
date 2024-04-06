package com.example.stocksimulation.service.support;

import com.example.stocksimulation.domain.vo.WebSocketClientVO;
import com.example.stocksimulation.domain.vo.WebSocketConnectedVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private final String[] RESPONSE_KEYS = WebSocketClientVO.WEB_SOCKET_CLIENT_RESPONSE.getValue().split(WebSocketClientVO.WEB_SOCKET_CLIENT_RESPONSE_SPLITER.getValue());
    private final TaskScheduler taskScheduler = new SimpleAsyncTaskScheduler();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private WebSocketConnectedVO connectedVO;
    private WebSocketSession session;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws JsonProcessingException {
        log.info("webSocketClient : Received message from server");
        if (message.getPayload().startsWith("0") || message.getPayload().startsWith("1")) {
            String[] responses = message.getPayload().split(WebSocketClientVO.WEB_SOCKET_CLIENT_RESPONSE_SPLITER.getValue());
            String response = AES256Decryptor.decrypt(responses[3], connectedVO.getIv(), connectedVO.getKey());

        } else if (message.getPayload().contains("SUBSCRIBE SUCCESS")) {
            connectedVO = objectMapper.readValue(message.getPayload(), WebSocketConnectedVO.class);
            JsonNode jsonNode = objectMapper.readTree(message.getPayload());
            String msg1 = jsonNode.path("body").path("msg1").asText();
            String iv = jsonNode.path("body").path("output").path("iv").asText();
            String key = jsonNode.path("body").path("output").path("key").asText();
            connectedVO = new WebSocketConnectedVO(msg1, iv, key);
            System.out.println("Message: " + message.getPayload());
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        log.info("webSocketClient : Connected to WebSocket server");
        this.session = session;

        taskScheduler.scheduleAtFixedRate(this::sendHeartbeat, 80000);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        log.warn("webSocketClient : Transport error occurred in WebSocket session");
        log.warn("Exception : ", exception);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        log.info("webSocketClient : Connection closed.");
    }

    private void sendHeartbeat() {
        try {
            session.sendMessage(new TextMessage(WebSocketClientVO.WEB_SOCKET_CLIENT_HEARTBEAT.getValue()));
        } catch (IOException e){
            log.warn("webSocketClient : WebSocket session is not open.");
            log.warn("Exception : ", e);
        }
    }
}