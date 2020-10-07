package com.example.amin.followfuck.instgram;

public interface ResponseAction {

    public void applyBeforeSendRequest(Object  instaUsername);
    public void applyAfterSucces(String  instaUsername);
    public void applyAfterFollowError(String  instaUsername,int errorcode);

}
