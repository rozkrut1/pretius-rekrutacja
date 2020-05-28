package org.jakub.rozkrut.controller.config;

import org.jakub.rozkrut.exceptions.BusinessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

@org.springframework.web.bind.annotation.ControllerAdvice
class ControllerAdvice {

    @ExceptionHandler(BusinessException.class)
    ResponseEntity handleBusinessException(BusinessException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
