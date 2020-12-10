package com.example.amin.followfuck.instgram;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * created By gOD on 12/20/2019 2:16 PM
 */

public class Reqs {


    public static String getReq(String url) throws IOException {
        OkHttpClient client =  new OkHttpClient.Builder()
                .connectTimeout(18, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
                .addHeader("cookie",LoginConfig.cookie)
                .url(url)
                .build();

        try (
                Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            //System.out.println(string);
            return string;
        }
    }
}
