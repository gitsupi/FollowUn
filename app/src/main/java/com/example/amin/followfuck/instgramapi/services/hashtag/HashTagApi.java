package com.example.amin.followfuck.instgramapi.services.hashtag;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface HashTagApi {

    @Headers({
            "x-requested-with: XMLHttpRequest",
            "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.117 Safari/537.36"
    })
    @GET("/explore/tags/{hashtag}/?__a=1")
    Call<ResponseBody> gethashtagdata(@Header("cookie") String cookie,
                                      @Path("hashtag") String hashtag);
    

}