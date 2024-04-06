package com.example.stocksimulation.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WebSocketClientVO {
    WEB_SOCKET_CLIENT_RESPONSE("\"유가증권단축종목코드|주식체결시간|주식현재가|전일대비부호|전일대비|전일대비율|가중평균주식가격|주식시가|주식최고가|주식최저가|매도호가1|매수호가1|체결거래량|누적거래량|누적거래대금|매도체결건수|매수체결건수|순매수체결건수|체결강도|총매도수량|총매수수량|체결구분|매수비율|전일거래량대비등락율|시가시간|시가대비구분|시가대비|최고가시간|고가대비구분|고가대비|최저가시간|저가대비구분|저가대비|영업일자|신장운영구분코드|거래정지여부|매도호가잔량|매수호가잔량|총매도호가잔량|총매수호가잔량|거래량회전율|전일동시간누적거래량|전일동시간누적거래량비율|시간구분코드|임의종료구분코드|정적VI발동기준가\""),
    WEB_SOCKET_CLIENT_REQUEST("{\"header\":{\"approval_key\":\"1d2d8d5f-b6de-46e5-be74-96312a693a76\",\"custtype\":\"P\",\"tr_type\":\"1\",\"content-type\":\"utf-8\"},\"body\":{\"input\":{\"tr_id\":\"H0STCNT0\",\"tr_key\":\"005930\"}}}"),
    WEB_SOCKET_CLIENT_URL("ws://ops.koreainvestment.com:21000/tryitout/H0STCNT0"),
    WEB_SOCKET_CLIENT_RESPONSE_SPLITER("|"),
    WEB_SOCKET_CLIENT_HEARTBEAT("HEARTBEAT");

    private final String value;
}
