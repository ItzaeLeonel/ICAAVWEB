package com.sistemasmig.icaavWeb.accounting.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ExistentResourceException extends Exception {
    public ExistentResourceException(String message) {
        super(message);
    }
}
