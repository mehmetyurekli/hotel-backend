package com.dedekorkut.hotelbackend.middleware;

import com.dedekorkut.hotelbackend.common.ErrorResponse;
import com.dedekorkut.hotelbackend.common.WillfulException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({WillfulException.class})
    public ResponseEntity<ErrorResponse> handleWillfulException(WillfulException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
