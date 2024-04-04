package com.example.stocksimulation.service.support;

import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class WebSocketClientSessionHandler extends StompSessionHandlerAdapter {

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        // 구독한 채널의 메세지 받기
        System.out.println("Received message from server:");
        System.out.println("headers: " + headers);
        System.out.println("payload: " + new String((byte[]) payload));
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return Object.class;
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        // 연결이 성공한 후 실행할 작업 정의
        System.out.println("Connected to WebSocket server");

        // 요청 메시지 생성
        String request = "{\"header\":{\"approval_key\":\"1d2d8d5f-b6de-46e5-be74-96312a693a76\",\"custtype\":\"P\",\"tr_type\":\"1\",\"content-type\":\"utf-8\"},\"body\":{\"input\":{\"tr_id\":\"H0STCNT0\",\"tr_key\":\"005930\"}}}";

        // 요청 메시지를 서버에 전송
        session.send("/tryitout/H0STCNT0", request.getBytes());
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        System.out.println("Exception occurred in WebSocket session:");
        System.out.println("Exception: " + exception);
    }

    @Override
    public void handleTransportError(StompSession session, Throwable exception) {
        System.out.println("Transport error occurred in WebSocket session:");
        System.out.println("Exception: " + exception);
    }
}
