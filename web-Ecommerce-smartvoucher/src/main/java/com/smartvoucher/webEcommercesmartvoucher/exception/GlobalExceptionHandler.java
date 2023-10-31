package com.smartvoucher.webEcommercesmartvoucher.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.Timestamp;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // AOP aspect oriented programming
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ErrorResponse(
                        new Timestamp(System.currentTimeMillis()),
                        500,
                        ex.getMessage(),
                        "Internal Server Error",
                        "/"
                )
        );
    }

    @ExceptionHandler(DuplicationCodeException.class)
    public ResponseEntity<ErrorResponse> handleDuplicationCodeException(Exception ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ErrorResponse(
                        new Timestamp(System.currentTimeMillis()),
                        400,
                        ex.getMessage(),
                        "Code is duplicated ! please try again !",
                        "/"
                )
        );
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleObjectNotFoundException(Exception ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ErrorResponse(
                        new Timestamp(System.currentTimeMillis()),
                        404,
                        ex.getMessage(),
                        "Object not found ! please try again !",
                        "/"
                )
        );
    }

    @ExceptionHandler(ObjectEmptyException.class)
    public ResponseEntity<ErrorResponse> handleObjectEmptyException(Exception ex){
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                new ErrorResponse(
                        new Timestamp(System.currentTimeMillis()),
                        406,
                        ex.getMessage(),
                        "Object is empty ! please fill all again !",
                        "/"
                )
        );
    }

    @ExceptionHandler(InputOutputException.class)
    public ResponseEntity<ErrorResponse> handleInputOutputException(Exception ex){
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                new ErrorResponse(
                        new Timestamp(System.currentTimeMillis()),
                        501,
                        ex.getMessage(),
                        "Input or Ouput is error !",
                        "/"
                )
        );
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(Exception ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ErrorResponse(
                        new Timestamp(System.currentTimeMillis()),
                        404,
                        ex.getMessage(),
                        "User not found or not exist !",
                        "/"
                )
        );
    }

    @ExceptionHandler(JwtFilterException.class)
    public ResponseEntity<ErrorResponse> handleJwtFilterException(Exception ex){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                new ErrorResponse(
                        new Timestamp(System.currentTimeMillis()),
                        403,
                        ex.getMessage(),
                        "Filter is blocked !",
                        "/"
                )
        );
    }

}
