package com.smartvoucher.webEcommercesmartvoucher.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // AOP aspect oriented programming
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ErrorResponse(
                        new Timestamp(System.currentTimeMillis()),
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        ex.getMessage(),
                        "Internal Server Error",
                        "/"
                )
        );
    }

/*    @ExceptionHandler({javax.validation.ConstraintViolationException.class})
    public ResponseEntity<ErrorResponse> handleValidationException(javax.validation.ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        String messageTemplate = "Internal Server Error", propertyPath = "";
        if (violations.size() > 0) {
            for (ConstraintViolation<?> violation : violations) {
                propertyPath = violation.getPropertyPath().toString();
                messageTemplate = violation.getMessageTemplate();
                break;
            }
        }
        return ResponseEntity.status(500).body(
                new ErrorResponse(new Timestamp(System.currentTimeMillis()),
                        HttpStatus.INTERNAL_SERVER_ERROR,
                                 ex.getMessage(),
                                 messageTemplate,
                            "/" + propertyPath));
    }*/

    @ExceptionHandler(DuplicationCodeException.class)
    public ResponseEntity<ErrorResponse> handleDuplicationCodeException(Exception ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ErrorResponse(
                        new Timestamp(System.currentTimeMillis()),
                        HttpStatus.BAD_REQUEST,
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
                        HttpStatus.NOT_FOUND,
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
                        HttpStatus.NOT_ACCEPTABLE,
                        ex.getMessage(),
                        "Object is empty ! please fill all again !",
                        "/"
                )
        );
    }

}
