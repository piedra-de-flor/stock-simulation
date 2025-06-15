package com.example.stocksimulation.dto.account;

import java.util.Map;

public record AccountInfoDto(
        String memberName,
        long money,
        Map<String, Integer> trades
) {
}
