package com.example.amin.followfuck;

import com.example.amin.followfuck.instgram.LoginConfig;
import com.example.amin.followfuck.instgram.Reqs;
import com.example.amin.followfuck.instgram.models.Followings;
import com.example.amin.followfuck.instgram.services.likerofpost.LikersOfPostUsersReq;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.example.amin.followfuck.BusinessContext.initsetup;

public class D {
    @Test
    public void k() {
        try {
            initsetup();
            String resp = Reqs.getReq("https://www.instagram.com/" + BusinessContext.Username + "/?__a=1");
            JSONObject jsonObject = new JSONObject(resp);
            JSONObject user = (JSONObject) ((JSONObject) jsonObject.get("graphql")).get("user");
            Integer countedge_follow = ((Integer) ((JSONObject) user.get("edge_follow")).get("count"));
            Integer edge_followed_by = ((Integer) ((JSONObject) user.get("edge_followed_by")).get("count"));
            String title = BusinessContext.Username + " " + edge_followed_by + "/" + countedge_follow;
            //System.out.println(title);


        } catch (IOException e) {

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void s() {

        HashMap<String, String> objectObjectHashMap = new HashMap<>();
        String shortcode = "CIikZ6QFSfZ";
        String par2 = String.format("{\"shortcode\":\"%s\",\"include_reel\":true,\"first\":24}", shortcode);
        String url = "https://www.instagram.com/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .build();
        LikersOfPostUsersReq likersOfPostUsersReq = retrofit.create(LikersOfPostUsersReq.class);
        objectObjectHashMap.put("query_hash", "d5d763b1e2acf209d62d22d184488e57");
        objectObjectHashMap.put("variables", par2);
        Call<ResponseBody> call = likersOfPostUsersReq.getLastFollowings(LoginConfig.cookie, objectObjectHashMap);

        Response<ResponseBody> execute;
        try {
            execute = call.execute();
            ResponseBody body = execute.body();
            String string = Objects.requireNonNull(body).string();
            System.out.println(string);

            JSONObject jsonObject = new JSONObject(string);

            JSONObject data = (JSONObject) jsonObject
                    .get("data");
            JSONObject jsonObject1 = (JSONObject) ((JSONObject) data
                    .get("shortcode_media"))
                    .get("edge_liked_by");
            JSONArray postedges = (JSONArray) jsonObject1
                    .get("edges");


            String end_cursor = (String) ((JSONObject) (jsonObject1).get("page_info")).get("end_cursor");

            boolean has_next_page = (boolean) ((JSONObject) (jsonObject1).get("page_info")).get("has_next_page");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
