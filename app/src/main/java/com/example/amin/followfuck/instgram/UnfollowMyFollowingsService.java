package com.example.amin.followfuck.instgram;

import com.example.amin.followfuck.BusinessContext;
import com.example.amin.followfuck.LoginConfig;
import com.example.amin.followfuck.instgram.models.ContinuedEdges;

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

    public void startUnfollowMyfollowing(JSONArray myfollowings
            , ResponseAction responseAction) throws JSONException, IOException {

        for (int i = 0; i < myfollowings.length(); i++) {
            System.out.println(i);
            JSONObject node = (JSONObject) myfollowings.getJSONObject(i).get("node");
            String followingid = (String) node.get("id");

            String username = (String) node.get("username");
            responseAction.applyBeforeSendRequest(username);

            int resonsecode = sendUnfollow(followingid);

            if (resonsecode == StatusCodes.OK) responseAction.applyAfterSucces("i="+(i+1)+username);
            else responseAction.applyAfterFollowError(username, resonsecode);

            try {
                Thread.sleep(((long) (Math.random() * 2000)));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public ContinuedEdges findFirstsfollowings() throws Exception {

        String par2 = String.format("{\"id\":\"%s\",\"include_reel\":true,\"fetch_mutual\":false,\"first\":14}", BusinessContext.UserID);
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

            return new ContinuedEdges(postedges, end_cursor, has_next_page);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    public ContinuedEdges findAftersfollowings(String endcursor) throws Exception {

        String id = BusinessContext.UserID;// my id

        String par22 = String.format("{\"id\":\"%s\",\"include_reel\":true,\"fetch_mutual\":false,\"first\":14," +
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

            return new ContinuedEdges(postedges, end_cursor, has_next_page);
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
