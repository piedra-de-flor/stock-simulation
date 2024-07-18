package com.example.stocksimulation.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    private final WebSocketHandler stockInfoHandler;
    private final WebSocketHandler balanceHandler;

    @Autowired
    public WebSocketConfig(@Qualifier("clientToServerHandler") WebSocketHandler stockInfoHandler,
                           @Qualifier("balanceWebSocketHandler") WebSocketHandler balanceHandler) {
        this.stockInfoHandler = stockInfoHandler;
        this.balanceHandler = balanceHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(stockInfoHandler, "/stocks-infos").setAllowedOrigins("*");
        registry.addHandler(balanceHandler, "/balance").setAllowedOrigins("*");
    }
}
