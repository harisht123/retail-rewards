package com.charter.rewards.handler;

import com.charter.rewards.model.Error;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        Map<String, List<Error>> body = new HashMap<>();

        List<Error> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> new Error(fieldError.getDefaultMessage(), fieldError.getField(), String.valueOf(fieldError.getRejectedValue())))
                .collect(Collectors.toList());

        body.put("errors", errors);
        log.error("Bad request received, errors on thr request: {}", errors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

}
