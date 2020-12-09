package com.example.amin.followfuck;

import com.example.amin.followfuck.instgram.UsernameFinder;

import org.json.JSONException;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

public class BusinessContext {
    public static String Username;
    public static String UserID = "38081432117";


    public static void initsetup() {
        Callable<Object> callable = Executors.callable(() -> {
            try {
                System.out.println("we arfe in ");
                Username = UsernameFinder.find(UserID);
            } catch (IOException e) {

            } catch (JSONException e) {
            }
        });
        try {
            callable.call();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
