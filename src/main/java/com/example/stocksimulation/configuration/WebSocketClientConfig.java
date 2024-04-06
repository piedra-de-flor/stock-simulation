package com.example.stocksimulation.configuration;

import com.example.stocksimulation.service.support.WebSocketClientSessionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

@Configuration
public class WebSocketClientConfig {

    @Bean
    public WebSocketClient webSocketClient() {
        return new StandardWebSocketClient();
    }

    @Bean
    public WebSocketHandler webSocketHandler() {
        return new WebSocketClientSessionHandler();
    }
}
