package com.example.stocksimulation.dto.exception;

import com.example.stocksimulation.domain.vo.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LockException extends RuntimeException{
    private final ErrorCode errorCode;
}
