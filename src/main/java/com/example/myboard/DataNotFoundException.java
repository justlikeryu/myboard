package com.example.myboard;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "entity not found")
public class DataNotFoundException extends RuntimeException { //실행 시 발생하는 예외
    private static final long serialVersionUID = 1L;

    public DataNotFoundException(String message) {
        super(message);//오류 메세지 반환
    }
}
