package com.example.stocksimulation.service.support;

import com.example.stocksimulation.dto.stock.StockDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Getter
@Component("clientToServerHandler")
@RequiredArgsConstructor
public class WebSocketClientToServerHandler extends TextWebSocketHandler {
    private static final ConcurrentHashMap<String, WebSocketSession> CLIENTS = new ConcurrentHashMap<>();
    public final Map<String, StockDto> stocks = new HashMap<>();
    private final ObjectMapper objectMapper;

    public void sendStockDtoToAllClients(StockDto stockDto) {
        String stockDtoJson;
        try {
            stockDtoJson = objectMapper.writeValueAsString(stockDto);
        } catch (IOException e) {
            log.warn("WebSocketServer : change stock info to json error", e);
            return;
        }

        TextMessage updateInfo = new TextMessage(stockDtoJson);

        CLIENTS.forEach((id, session) -> {
            try {
                session.sendMessage(updateInfo);
            } catch (IOException e) {
                log.warn("WebSocketServer : send update info error", e);
            }
        });
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        String stocksDtoJson;
        try {
            stocksDtoJson = objectMapper.writeValueAsString(stocks);
        } catch (IOException e) {
            log.warn("WebSocketServer : change stocks list to json error", e);
            return;
        }

        TextMessage stocksInfo = new TextMessage(stocksDtoJson);

        CLIENTS.put(session.getId(), session);
        try {
            session.sendMessage(stocksInfo);
        } catch (IOException e) {
            log.warn("WebSocketServer : send stocks info error", e);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        CLIENTS.remove(session.getId());
    }
}
