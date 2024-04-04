package com.example.stocksimulation.configuration;

import com.example.stocksimulation.service.support.WebSocketClientSessionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

@Configuration
public class WebSocketClientConfig {

    @Bean
    public WebSocketStompClient webSocketStompClient(StompSessionHandler stompSessionHandler) {
        WebSocketClient webSocketClient = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(webSocketClient);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        stompClient.connectAsync("ws://ops.koreainvestment.com:21000/tryitout/H0STCNT0", stompSessionHandler);
        System.out.println("web Socket connected");
        return stompClient;
    }

    @Bean
    public StompSessionHandler stompSessionHandler() {
        return new WebSocketClientSessionHandler();
    }
}
