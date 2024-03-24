package com.smartvoucher.webEcommercesmartvoucher.exception;

import com.google.gson.JsonSyntaxException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.Timestamp;
import java.util.Objects;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    // AOP aspect oriented programming
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex){
        log.info(ex.getMessage());
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

    @ExceptionHandler({JsonSyntaxException.class, IllegalStateException.class})
    public ResponseEntity<ErrorResponse> handleJsonSyntaxException(Exception ex){
        log.info(ex.getMessage());
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

    @ExceptionHandler(VerificationTokenException.class)
    public ResponseEntity<ErrorResponse> handleVerificationTokenException(Exception ex){
        log.info(ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ErrorResponse(
                        new Timestamp(System.currentTimeMillis()),
                        500,
                        ex.getMessage(),
                        "Verification token is error !",
                        "/"
                )
        );
    }

    @ExceptionHandler(InputPhoneException.class)
    public ResponseEntity<ErrorResponse> handleInputPhoneException(Exception ex){
        log.info(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                new ErrorResponse(
                        new Timestamp(System.currentTimeMillis()),
                        501,
                        ex.getMessage(),
                        "Phone number must be 10 digit or not blank, white space or un correct format !",
                        "/"
                )
        );
    }

    @ExceptionHandler(OAuth2LoginException.class)
    public ResponseEntity<ErrorResponse> handleOAuth2LoginException(Exception ex){
        log.info(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ErrorResponse(
                        new Timestamp(System.currentTimeMillis()),
                        400,
                        ex.getMessage(),
                        "OAuth 2.0 login is error !",
                        "/"
                )
        );
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(Exception ex){
        log.info(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ErrorResponse(
                        new Timestamp(System.currentTimeMillis()),
                        400,
                        ex.getMessage(),
                        "OAuth 2.0 login is error !",
                        "/"
                )
        );
    }

    @ExceptionHandler(ResetPasswordException.class)
    public ResponseEntity<ErrorResponse> handleChangePasswordException(Exception ex){
        log.info(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                new ErrorResponse(
                        new Timestamp(System.currentTimeMillis()),
                        501,
                        ex.getMessage(),
                        "Reset password error !",
                        "/"
                )
        );
    }

    @ExceptionHandler(DuplicationCodeException.class)
    public ResponseEntity<ErrorResponse> handleDuplicationCodeException(Exception ex){
        log.info(ex.getMessage());
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
        log.info(ex.getMessage());
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
        log.info(ex.getMessage());
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
        log.info(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                new ErrorResponse(
                        new Timestamp(System.currentTimeMillis()),
                        501,
                        ex.getMessage(),
                        "Input or Output is error !",
                        "/"
                )
        );
    }

    @ExceptionHandler(CheckCapacityException.class)
    public ResponseEntity<ErrorResponse> handleCheckCapacityException(Exception ex) {
        log.info(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                new ErrorResponse(
                        new Timestamp(System.currentTimeMillis()),
                        406,
                        ex.getMessage(),
                        "Capacity is full !",
                        "/"
                )
        );
    }

        @ExceptionHandler(UserNotFoundException.class)
        public ResponseEntity<ErrorResponse> handleUserNotFoundException(Exception ex){
            log.info(ex.getMessage());
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

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistException(Exception ex){
        log.info(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                new ErrorResponse(
                        new Timestamp(System.currentTimeMillis()),
                        406,
                        ex.getMessage(),
                        "User already exist !",
                        "/"
                )
        );
    }

    @ExceptionHandler(TokenRefreshException.class)
    public ResponseEntity<ErrorResponse> handleTokenRefreshException(Exception ex){
        log.info(ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                new ErrorResponse(
                        new Timestamp(System.currentTimeMillis()),
                        403,
                        ex.getMessage(),
                        "Refresh token is expired or not exist !",
                        "/"
                )
        );
    }

    @ExceptionHandler(PermissionDenyException.class)
    public ResponseEntity<ErrorResponse> handlePermissionDenyException(Exception ex){
        log.info(ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                new ErrorResponse(
                        new Timestamp(System.currentTimeMillis()),
                        403,
                        ex.getMessage(),
                        "Permission Denied !",
                        "/"
                )
        );
    }

        @ExceptionHandler(JwtFilterException.class)
        public ResponseEntity<ErrorResponse> handleJwtFilterException(Exception ex){
            log.info(ex.getMessage());
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
        // ConstraintViolationException and MethodArgumentNotValidException
        // MethodArgumentNotValidException : sử dụng để check cho các trường hợp sử dụng @Valid (ví dụ: objectDTO ,...)
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex){
            log.info(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ErrorResponse(
                            new Timestamp(System.currentTimeMillis()),
                            500,
                            Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage(),
                            "Error Validation !",
                            "/"
                    )
            );
        }

        @ExceptionHandler(ExpiredVoucherException.class)
        public ResponseEntity<ErrorResponse> handleExpiredVoucherException(Exception ex) {
            log.info(ex.getMessage());
            return ResponseEntity.status(HttpStatus.GONE).body(
                    new ErrorResponse(
                            new Timestamp(System.currentTimeMillis()),
                            410,
                            ex.getMessage(),
                            "Expired Voucher !",
                            "/"
                    )
            );
        }

    @ExceptionHandler(UsedVoucherException.class)
    public ResponseEntity<ErrorResponse> handleUsedVoucherException(Exception ex){
        log.info(ex.getMessage());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(
                new ErrorResponse(
                        new Timestamp(System.currentTimeMillis()),
                        405,
                        ex.getMessage(),
                        "Voucher used !",
                        "/"
                )
        );
    }

    @ExceptionHandler(CheckStatusWarehouseException.class)
    public ResponseEntity<ErrorResponse> handleCheckStatusWarehouseException(Exception ex) {
        log.info(ex.getMessage());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(
                new ErrorResponse(
                        new Timestamp(System.currentTimeMillis()),
                        405,
                        ex.getMessage(),
                        "Warehouse inactive !",
                        "/"
                )
        );
    }
}
