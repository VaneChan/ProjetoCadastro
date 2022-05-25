package com.study.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class FuncionarioNotFoundException extends RuntimeException{

    public FuncionarioNotFoundException(String msg) {
        super(msg);
    }
}
