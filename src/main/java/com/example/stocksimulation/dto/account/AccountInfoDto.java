package com.example.stocksimulation.dto.account;

import com.example.stocksimulation.dto.trade.TradeResponseDto;

import java.util.List;

public record AccountInfoDto(
        String memberName,
        long money,
        List<TradeResponseDto> trades
) {
}
