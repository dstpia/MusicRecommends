package com.ragnarock.musicrecommends.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UnExistedItemException extends RuntimeException {
    public UnExistedItemException(String message) {
        super(message);
    }
}
