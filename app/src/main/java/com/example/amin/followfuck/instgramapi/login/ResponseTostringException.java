package com.example.amin.followfuck.instgramapi.login;

import java.io.IOException;

public class ResponseTostringException extends IOException{
    public ResponseTostringException(String message) {
        super(message);
    }

    public ResponseTostringException(String message, Throwable cause) {
        super(message, cause);
    }
}
