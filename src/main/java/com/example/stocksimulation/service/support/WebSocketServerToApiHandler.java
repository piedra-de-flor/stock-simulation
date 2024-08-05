package com.example.stocksimulation.service.support;

import com.example.stocksimulation.domain.vo.WebSocketConnectInfo;
import com.example.stocksimulation.domain.vo.WebSocketIndexNumber;
import com.example.stocksimulation.domain.vo.WebSocketParsingInfo;
import com.example.stocksimulation.service.stock.StockService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

@Slf4j
@RequiredArgsConstructor
@Component
@Getter
public class WebSocketServerToApiHandler implements WebSocketHandler {
    private final StockService service;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private WebSocketConnectInfo connectedVO;
    private WebSocketSession session;

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        log.info("webSocketClient : Connected to WebSocket server");
        sendHeartbeat(session);
        this.session = session;

        return session.receive()
                .flatMap(this::handleTextMessage)
                .doOnError(e -> handleTransportError(session, e))
                .then();
    }

    private Mono<Void> handleTextMessage(WebSocketMessage message) {
        log.info(message.getPayloadAsText());
        return Mono.fromRunnable(() -> {
            try {
                String payload = message.getPayloadAsText();
                log.info("webSocketClient : Received message from server");

                if (payload.startsWith("0") || payload.startsWith("1")) {
                    String[] responses = payload.split(WebSocketParsingInfo.WEB_SOCKET_CLIENT_RESPONSE_SPLITER.getValue());
                    String[] response = responses[WebSocketIndexNumber.INDEX_OF_RESPONSE.getValue()]
                            .split(WebSocketParsingInfo.WEB_SOCKET_CLIENT_PRICE_SPLITER.getValue());
                    String code = response[WebSocketIndexNumber.INDEX_OF_CODE.getValue()];
                    String price = response[WebSocketIndexNumber.INDEX_OF_PRICE.getValue()];

                    service.updateStockPrice(code, Integer.parseInt(price));
                } else if (payload.contains("SUBSCRIBE SUCCESS")) {
                    JsonNode jsonNode = objectMapper.readTree(payload);
                    String msg1 = jsonNode.path("body").path("msg1").asText();
                    String iv = jsonNode.path("body").path("output").path("iv").asText();
                    String key = jsonNode.path("body").path("output").path("key").asText();
                    connectedVO = new WebSocketConnectInfo(msg1, iv, key);
                    log.info("Message: " + payload);
                }
            } catch (Exception e) {
                log.error("Error processing WebSocket message", e);
            }
        });
    }

    public Mono<Void> handleTransportError(WebSocketSession session, Throwable exception) {
        log.warn("webSocketClient : Transport error occurred in WebSocket session");
        log.warn("Exception : ", exception);
        return session.close();
    }

    private void sendHeartbeat(WebSocketSession session) {
        Flux.interval(Duration.ofSeconds(80))
                .flatMap(interval -> session.send(
                        Mono.just(session.textMessage(WebSocketParsingInfo.WEB_SOCKET_CLIENT_HEARTBEAT.getValue()))
                ))
                .subscribeOn(Schedulers.boundedElastic())
                .doOnError(e -> log.warn("Error sending heartbeat", e))
                .subscribe();
    }
}