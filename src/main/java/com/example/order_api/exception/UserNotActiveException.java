package com.example.order_api.exception;

import org.springframework.http.HttpStatus;

public class UserNotActiveException extends ApiException {
    public UserNotActiveException() {
        super("User account is not active", HttpStatus.FORBIDDEN, "USER_NOT_ACTIVE");
    }

    public UserNotActiveException(String email) {
        super("User account is not active: " + email, HttpStatus.FORBIDDEN, "USER_NOT_ACTIVE");
    }
}
