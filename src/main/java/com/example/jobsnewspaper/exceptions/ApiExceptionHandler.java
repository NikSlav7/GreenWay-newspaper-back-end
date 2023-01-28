package com.example.jobsnewspaper.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.channels.NonWritableChannelException;
import java.text.ParseException;

@ControllerAdvice
public class ApiExceptionHandler {


    @ExceptionHandler(value = {WrongEmailDetailsException.class, ParseException.class, NonWritableChannelException.class})
    public ResponseEntity<Object> handleException(Exception exception){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(exception, status);
    }

}
