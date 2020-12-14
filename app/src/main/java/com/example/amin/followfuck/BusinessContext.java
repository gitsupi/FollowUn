package com.example.amin.followfuck;

import com.example.amin.followfuck.instgramapi.UsernameFinder;

import org.json.JSONException;

import java.io.IOException;
import java.util.concurrent.Executors;

public class BusinessContext {
    public static String Username;
    public static String UserID;
    public static String cookie;
    public static String csrftoken;




    public static void initsetup() {
        try {
            Executors.callable(() -> {
                try {
                    BusinessContext.Username = UsernameFinder.find(UserID);
                    //System.out.println("we arfe in " + BusinessContext.Username);
                } catch (IOException e) {

                } catch (JSONException e) {
                }
            }).call();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
