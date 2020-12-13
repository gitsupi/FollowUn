package com.example.amin.followfuck;

import com.example.amin.followfuck.instgram.UnFollowingRequests;

import org.json.JSONArray;
import org.json.JSONException;

import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UnfollowUnittest {
    @Test
    public void addition_isCorrect() {

        String id = "8916622827";// last post
        id = "38081432117";
        id = "30299824247";
        id = "30134076076";//DAYIPET


        String par2 = String.format("{\"id\":\"%s\",\"include_reel\":true,\"fetch_mutual\":false,\"first\":12}", id);
        String ev2 = URLEncoder.encode(par2);//first 1 i see

        String url = "https://www.instagram.com/graphql/query/?query_hash=d04b0a864b4b54837c0d870b0e77e076&variables=" + ev2;
        //System.out.println(url);
        url = "https://www.instagram.com/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .build();

        UnFollowingRequests unFollowingRequests = retrofit.create(UnFollowingRequests.class);
        HashMap<String, String> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("query_hash", "d04b0a864b4b54837c0d870b0e77e076");
        objectObjectHashMap.put("variables", par2);
        Call<ResponseBody> call = unFollowingRequests.getLastFollowings(LoginConfig.cookie, objectObjectHashMap);
//
//
        Response<ResponseBody> execute;
        try {
            execute = call.execute();
            ResponseBody body = execute.body();
            String string = body.string();

            JSONObject jsonObject = new JSONObject(string);

            JSONArray postedges = (JSONArray) ((JSONObject) ((JSONObject) ((JSONObject) jsonObject
                    .get("data"))
                    .get("user")).get("edge_follow"))
                    .get("edges");


            String end_cursor = (String) ((JSONObject) ((JSONObject) ((JSONObject) ((JSONObject) jsonObject
                    .get("data"))
                    .get("user")).get("edge_follow")).get("page_info")).get("end_cursor");

            boolean has_next_page = (boolean) ((JSONObject) ((JSONObject) ((JSONObject) ((JSONObject) jsonObject
                    .get("data"))
                    .get("user")).get("edge_follow")).get("page_info")).get("has_next_page");


            for (int i = 0; i < postedges.length(); i++) {
                JSONObject node = (JSONObject) postedges.getJSONObject(i).get("node");
                //System.out.println(node.get("username"));
                String followingid = (String) node.get("id");
                Response<ResponseBody> bodyResponse = unFollowingRequests.unfollow(LoginConfig.cookie, followingid).execute();

                //System.out.println(bodyResponse.code());

                String status = Objects.requireNonNull(bodyResponse.body()).string();

                //System.out.println(status);

                Thread.sleep(1000);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//


    }
}