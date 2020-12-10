package com.example.amin.followfuck.instgram;


import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface FollowRequestService {

    @Headers({
            "Content-Type: application/json",
            "x-requested-with: XMLHttpRequest",
            "referer:https://www.instagram.com/p/B7nf91_hkaQ/",
            "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.117 Safari/537.36"
    })
    @POST
    Call<ResponseBody> post(@Header("cookie") String cookie, @Header("x-csrftoken") String csrf, @Body String json);


}