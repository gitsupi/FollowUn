package com.example.amin.followfuck.instgram;

import com.example.amin.followfuck.BusinessContext;
import com.example.amin.followfuck.instgram.models.Followings;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UnfollowMyFollowingsService {

    public void startUnfollowMyfollowing(JSONArray followersOfinfluencers
            , ResponseAction responseAction) throws JSONException, IOException {

        for (int i = 0; i < followersOfinfluencers.length(); i++) {
            System.out.println(i);
            JSONObject node = (JSONObject) followersOfinfluencers.getJSONObject(i).get("node");
            String followingid = (String) node.get("id");

            String username = (String) node.get("username");
            responseAction.applyBeforeSendRequest(username);

            int resonsecode = sendUnfollow(followingid);

            if (resonsecode == StatusCodes.OK) responseAction.applyAfterSucces(username);
            else responseAction.applyAfterFollowError(username, resonsecode);

            try {
                Thread.sleep(((long) (Math.random() * 2000)));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public Followings findFirstsfollowings() throws Exception {

        String id = "38081432117";// my id
        String par2 = String.format("{\"id\":\"%s\",\"include_reel\":true,\"fetch_mutual\":false,\"first\":12}", id);
        String url = "https://www.instagram.com/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .build();
        UnFollowingRequests unFollowingRequests = retrofit.create(UnFollowingRequests.class);
        HashMap<String, String> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("query_hash", "d04b0a864b4b54837c0d870b0e77e076");
        objectObjectHashMap.put("variables", par2);
        Call<ResponseBody> call = unFollowingRequests.getLastFollowings(LoginConfig.cookie, objectObjectHashMap);

        Response<ResponseBody> execute;
        try {
            execute = call.execute();
            ResponseBody body = execute.body();
            String string = Objects.requireNonNull(body).string();

            JSONObject jsonObject = new JSONObject(string);

            JSONArray postedges = (JSONArray) ((JSONObject) ((JSONObject) ((JSONObject) jsonObject
                    .get("data"))
                    .get("user"))
                    .get("edge_follow"))
                    .get("edges");


            String end_cursor = (String) ((JSONObject) ((JSONObject) ((JSONObject) ((JSONObject) jsonObject
                    .get("data"))
                    .get("user")).get("edge_follow")).get("page_info")).get("end_cursor");

            boolean has_next_page = (boolean) ((JSONObject) ((JSONObject) ((JSONObject) ((JSONObject) jsonObject
                    .get("data"))
                    .get("user")).get("edge_follow")).get("page_info")).get("has_next_page");

            return new Followings(postedges, end_cursor, has_next_page);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Followings findAftersfollowings(String endcursor) throws Exception {

        String id = BusinessContext.UserID;// my id

        String par22 = String.format("{\"id\":\"%s\",\"include_reel\":true,\"fetch_mutual\":false,\"first\":16," +
                "\"after\":\"%s\"}", id, endcursor);
        String url = "https://www.instagram.com/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .build();

        UnFollowingRequests unFollowingRequests = retrofit.create(UnFollowingRequests.class);
        HashMap<String, String> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("query_hash", "d04b0a864b4b54837c0d870b0e77e076");
        objectObjectHashMap.put("variables", par22);
        Call<ResponseBody> call = unFollowingRequests.getLastFollowings(LoginConfig.cookie, objectObjectHashMap);
        Response<ResponseBody> execute;
        try {
            execute = call.execute();
            ResponseBody body = execute.body();
            String string = Objects.requireNonNull(body).string();

            JSONObject jsonObject = new JSONObject(string);
            JSONArray postedges = (JSONArray) ((JSONObject) ((JSONObject) ((JSONObject) jsonObject
                    .get("data"))
                    .get("user"))
                    .get("edge_follow"))
                    .get("edges");

            String end_cursor = (String) ((JSONObject) ((JSONObject) ((JSONObject) ((JSONObject) jsonObject
                    .get("data"))
                    .get("user")).get("edge_follow")).get("page_info")).get("end_cursor");

            boolean has_next_page = (boolean) ((JSONObject) ((JSONObject) ((JSONObject) ((JSONObject) jsonObject
                    .get("data"))
                    .get("user")).get("edge_follow")).get("page_info")).get("has_next_page");

            return new Followings(postedges, end_cursor, has_next_page);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }


    private int sendUnfollow(String followingid) throws IOException {
        String url = "https://www.instagram.com/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .build();
        UnFollowingRequests unFollowingRequests = retrofit.create(UnFollowingRequests.class);
        Response<ResponseBody> bodyResponse = unFollowingRequests.unfollow(LoginConfig.cookie, followingid).execute();
        return bodyResponse.code();
    }


}
