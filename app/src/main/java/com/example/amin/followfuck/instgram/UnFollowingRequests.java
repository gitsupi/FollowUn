package com.example.amin.followfuck.instgram;


import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface UnFollowingRequests {

    @Headers({
            "x-requested-with: XMLHttpRequest",
            "x-csrftoken: " + LoginConfig.csrftoken,
            "x-ig-www-claim: " + LoginConfig.IGCLAIM,
            "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.117 Safari/537.36"
    })
    @GET("graphql/query/")
    Call<ResponseBody> getLastFollowings(@Header("cookie") String cookie,
                                         @QueryMap Map<String, String> options);

    @Headers({
            "x-requested-with: XMLHttpRequest",
            "x-csrftoken: " + LoginConfig.csrftoken,
            "x-ig-www-claim: " + LoginConfig.IGCLAIM,
            "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.117 Safari/537.36"
    })
    @POST("/web/friendships/{id}/unfollow/")
    Call<ResponseBody> unfollow(@Header("cookie") String cookie, @Path("id") String id);
}