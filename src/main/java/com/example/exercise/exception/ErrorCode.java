package com.example.exercise.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    CUSTOMER_NOT_FOUND("KB_404", "고객이 없습니다"),
    CUSTOMER_DUPLICATED("KB_409", "고객이 이미 존재합니다");


    private final String code;
    private final String message;
}
