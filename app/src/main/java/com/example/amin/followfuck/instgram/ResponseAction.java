package com.example.amin.followfuck.instgram;

public interface ResponseAction {

    public void applyBeforeSendFollowRequest(String  instaUsername);
    public void applyAfterFollowSucces(String  instaUsername);
    public void applyAfterFollowError(String  instaUsername,int errorcode);

}
