package com.example.amin.followfuck.instgramapi.login;


public abstract class LoginResponse<T> {

    private String authenticated;
    private String status;
    private String user;

    public abstract T get();

    static enum Status {
        fail,
        ok
    }
}


