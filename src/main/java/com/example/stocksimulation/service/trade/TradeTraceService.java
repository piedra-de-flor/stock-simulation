package com.example.stocksimulation.service.trade;

import com.example.stocksimulation.domain.entity.*;
import com.example.stocksimulation.domain.vo.TradeType;
import com.example.stocksimulation.dto.trade.TradeTraceDto;
import com.example.stocksimulation.repository.MemberRepository;
import com.example.stocksimulation.repository.TradeTraceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TradeTraceService {
    private final TradeTraceRepository repository;
    private final MemberRepository memberRepository;

    public List<TradeTraceDto> readAll(String memberEmail) {
        Member member = memberRepository.getMemberByEmail(memberEmail);
        List<TradeTrace> traces = repository.findAllByAccount(member.getAccount());
        List<TradeTraceDto> response = new ArrayList<>();

        for (TradeTrace trace : traces) {
            response.add(TradeTraceDto.create(trace));
        }

        return response;
    }

    public void recordTrace(Account account, Stock stock, int quantity, TradeType tradeType) {
        TradeTrace trace = TradeTrace.builder()
                .account(account)
                .quantity(quantity)
                .stock(stock)
                .tradeType(tradeType)
                .build();

        repository.save(trace);
    }
}
