package com.example.amin.followfuck.instgram.services.comments;


import com.example.amin.followfuck.instgram.LoginConfig;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface LeaveCommentRequests {

//    @Headers({
//            "x-requested-with: XMLHttpRequest",
//            "x-csrftoken: " + LoginConfig.csrftoken,
//            "x-ig-www-claim: " + LoginConfig.IGCLAIM,
//            "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.117 Safari/537.36"
//    })
//    @GET("graphql/query/")
//    Call<ResponseBody> getLastFollowings(@Header("cookie") String cookie,
//                                         @QueryMap Map<String, String> options);

    @Headers({
            "x-requested-with: XMLHttpRequest",
            "x-csrftoken: " + LoginConfig.csrftoken,
            "x-ig-www-claim: " + LoginConfig.IGCLAIM,
            "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.117 Safari/537.36"
    })
    @FormUrlEncoded
    @POST("/web/comments/{postid}/add/")
    Call<ResponseBody> leaveAComment(@Header("cookie") String cookie, @Path("postid") String postid
            , @Field("comment_text") String comment_text, @Field("replied_to_comment_id") String replied_to_comment_id);
}