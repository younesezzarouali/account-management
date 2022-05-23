package com.katatest.accountmanagement.error.handler;

import com.katatest.accountmanagement.error.exception.BusinessException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Translate exceptions to http response with badRequest status
 */
@Log4j2
@ControllerAdvice
public class RestResponseEntityExceptionHandler {

    /**
     * Translation of BusinessException in http response with code 400 with error code/message
     *
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Map<String, String>> handleBadRequestAlertExceptions(BusinessException ex) {
        log.debug("Exception has occurred {}", ex.getErrorKey());

        Map<String, String> errors = new HashMap<>();
        errors.put("errorKey", ex.getErrorKey());
        errors.put("message", ex.getMessage());
        return ResponseEntity.badRequest().body(errors);
    }
}
