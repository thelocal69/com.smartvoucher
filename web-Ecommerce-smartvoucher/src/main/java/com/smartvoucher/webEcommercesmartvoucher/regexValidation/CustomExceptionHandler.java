package com.smartvoucher.webEcommercesmartvoucher.regexValidation;

import com.smartvoucher.webEcommercesmartvoucher.payload.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolation;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

@ControllerAdvice
@RestController
public class CustomExceptionHandler {

    @ExceptionHandler({javax.validation.ConstraintViolationException.class})
    public ResponseEntity<?> handleValidationException(javax.validation.ConstraintViolationException ex) {

        // save list constraintViolations
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();

        String messageTemplate = "Internal Server Error", propertyPath = "";

        if (violations.size() > 0) {
            for (ConstraintViolation<?> violation : violations) {
                // Lấy thông báo từ chú thích kiểm tra hợp lệ

                propertyPath = violation.getPropertyPath().toString();
                messageTemplate = violation.getMessageTemplate();
                break; // Thoát khỏi vòng lặp khi tìm thấy thông báo
            }
        }

        Date date = new Date();

        return ResponseEntity.status(501).body(
                new ErrorResponse(new Timestamp(date.getTime()),
                                 501,
                                 ex.getMessage(),
                                 messageTemplate,
                            "/" + propertyPath));
    }

}
