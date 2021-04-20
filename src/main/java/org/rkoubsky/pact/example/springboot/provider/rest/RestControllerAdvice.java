package org.rkoubsky.pact.example.springboot.provider.rest;

import javax.servlet.http.HttpServletRequest;

import org.rkoubsky.pact.example.springboot.provider.NoEntityException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NoEntityException.class)
    @ResponseBody
    public ResponseEntity<String> handleControllerException(HttpServletRequest request, Throwable ex) {
        return ResponseEntity.notFound().build();
    }
}
