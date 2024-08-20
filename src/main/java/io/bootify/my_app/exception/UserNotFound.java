package io.bootify.my_app.exception;

import org.springframework.http.HttpStatus;

public class UserNotFound extends Throwable {
    public UserNotFound(String userNotFound) {
        super(userNotFound);
    }
}
