package com.example.amin.followfuck;

import com.example.amin.followfuck.instgramapi.Function;
import com.example.amin.followfuck.instgramapi.UsernameFinder;

import org.json.JSONException;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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

    public static void initsetup(Function function) {
        try {
            Future<?> submit = Executors.newSingleThreadExecutor().submit(() -> {
                try {
                    BusinessContext.Username = UsernameFinder.find(UserID);
                    function.apply();
                    //System.out.println("we arfe in " + BusinessContext.Username);
                } catch (IOException e) {

                } catch (JSONException e) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
