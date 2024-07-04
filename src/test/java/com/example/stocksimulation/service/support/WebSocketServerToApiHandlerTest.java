package com.example.stocksimulation.service.support;

import com.example.stocksimulation.domain.vo.WebSocketConnectedVO;
import com.example.stocksimulation.service.stock.StockService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

import static org.mockito.Mockito.*;

class WebSocketServerToApiHandlerTest {

    @Mock
    private StockService stockService;

    @Mock
    private WebSocketSession session;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private WebSocketServerToApiHandler webSocketServerToApiHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void 웹소켓_케세지_파싱_테스트() throws IOException {
        String messagePayload = "0|response|code|TEST^TEST^10000";
        TextMessage message = new TextMessage(messagePayload);

        when(objectMapper.readValue(message.getPayload(), WebSocketConnectedVO.class)).thenReturn(new WebSocketConnectedVO("msg1", "iv", "key"));
        webSocketServerToApiHandler.handleTextMessage(session, message);

        verify(stockService, times(1)).updateStockPrice(anyString(), anyInt());
    }

    @Test
    void 웹소켓_닫힌후_세션_종료_테스트() {
        webSocketServerToApiHandler.afterConnectionClosed(session, null);
    }

    @Test
    void 웹소켓_에러_발생시_세션_종료_테스트() throws IOException {
        webSocketServerToApiHandler.handleTransportError(session, new Exception("Test exception"));

        verify(session, times(1)).close();
    }
}
