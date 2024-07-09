package com.example.stocksimulation.service.stock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WebSocketConnectServiceTest {

    @Mock
    private WebSocketHandler handler;

    @Mock
    private WebSocketSession session;

    @Mock
    private StandardWebSocketClient webSocketClient;

    @InjectMocks
    private WebSocketConnectService webSocketConnectService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        webSocketConnectService = new WebSocketConnectService(handler);
        webSocketConnectService.setSession(session);
    }

    //TODO
/*    @Test
    public void testConnect() throws ExecutionException, InterruptedException {
        CompletableFuture<WebSocketSession> futureSession = CompletableFuture.completedFuture(session);

        when(webSocketClient.execute(any(WebSocketHandler.class), anyString())).thenReturn(futureSession);

        webSocketConnectService.connect();

        verify(webSocketClient, times(1)).execute(any(WebSocketHandler.class), eq(WebSocketClientVO.WEB_SOCKET_CLIENT_URL.getValue()));
    }*/

    @Test
    public void 웹소켓_메세지_보내기_성공_테스트() throws IOException {
        String message = "test message";

        webSocketConnectService.send(message);

        verify(session, times(1)).sendMessage(new TextMessage(message));
    }

    @Test
    public void 웹소켓_메세지_보내기_실패_테스트() throws IOException {
        String message = "test message";
        doThrow(new IOException()).when(session).sendMessage(any(TextMessage.class));

        webSocketConnectService.send(message);

        verify(session, times(1)).sendMessage(new TextMessage(message));
    }
}
