package com.example.stocksimulation.service.support;

import com.example.stocksimulation.dto.stock.StockDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class WebSocketClientToServerHandlerTest {

    private WebSocketClientToServerHandler handler;
    private ObjectMapper objectMapper;
    private WebSocketSession session;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        handler = new WebSocketClientToServerHandler(objectMapper);
        session = mock(WebSocketSession.class);
    }

    @Test
    public void 사용자가_서버의_웹소켓에_연결_테스트() throws IOException {
        when(session.getId()).thenReturn("1");

        handler.afterConnectionEstablished(session);

        assertTrue(handler.getCLIENTS().containsKey("1"));

        ArgumentCaptor<TextMessage> messageCaptor = ArgumentCaptor.forClass(TextMessage.class);
        verify(session).sendMessage(messageCaptor.capture());

        String messageContent = messageCaptor.getValue().getPayload();
        ConcurrentHashMap<String, StockDto> sentStocks = objectMapper.readValue(messageContent, ConcurrentHashMap.class);

        assertEquals(handler.getStocks(), sentStocks);
    }

    @Test
    public void 서버의_웹소켓이_닫혔을때_사용자_웹소켓_연결_실패_테스트() {
        when(session.getId()).thenReturn("1");

        handler.getCLIENTS().put("1", session);

        handler.afterConnectionClosed(session, null);

        assertFalse(handler.getCLIENTS().containsKey("1"));
    }

    @Test
    public void 서버_웹소켓_연결한_사용자들에게_주식_변동사항_전송_테스트() throws IOException {
        WebSocketSession session1 = mock(WebSocketSession.class);
        when(session1.getId()).thenReturn("1");
        WebSocketSession session2 = mock(WebSocketSession.class);
        when(session2.getId()).thenReturn("2");

        handler.getCLIENTS().put("1", session1);
        handler.getCLIENTS().put("2", session2);

        StockDto stockDto = new StockDto("stock1", 100, "Test Stock");

        handler.sendStockDtoToAllClients(stockDto);

        ArgumentCaptor<TextMessage> messageCaptor = ArgumentCaptor.forClass(TextMessage.class);
        verify(session1).sendMessage(messageCaptor.capture());
        verify(session2).sendMessage(messageCaptor.capture());

        String messageContent = messageCaptor.getValue().getPayload();
        StockDto sentStockDto = objectMapper.readValue(messageContent, StockDto.class);

        assertEquals(stockDto, sentStockDto);
    }
}
