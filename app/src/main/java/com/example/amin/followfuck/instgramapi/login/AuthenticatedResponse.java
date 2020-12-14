package com.example.amin.followfuck.instgramapi.login;


public class AuthenticatedResponse implements Respnse {
    public boolean user;
    public String userId;
    public boolean authenticated;
    public boolean oneTapPrompt;
    public String status;

    @Override
    public String status() {
        return status;
    }

}