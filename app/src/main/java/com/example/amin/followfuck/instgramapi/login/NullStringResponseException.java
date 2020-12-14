package com.example.amin.followfuck.instgramapi.login;

import java.io.IOException;

public class NullStringResponseException extends IOException {

    public NullStringResponseException() {
    }

    public NullStringResponseException(String message) {
        super(message);
    }

    public NullStringResponseException(String message, Throwable cause) {
        super(message, cause);
    }

    public NullStringResponseException(Throwable cause) {
        super(cause);
    }
}
