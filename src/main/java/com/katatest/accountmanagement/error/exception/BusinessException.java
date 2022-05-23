package com.katatest.accountmanagement.error.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * Business Exception
 * errorKey : the error key
 * message : the message to show to the client
 */
@Getter
@Setter
public class BusinessException extends RuntimeException {
    private final String errorKey;

    public BusinessException(String errorKey) {
        super();
        this.errorKey = errorKey;
    }

    public BusinessException(String errorKey, String message) {
        super(message);
        this.errorKey = errorKey;
    }
}
