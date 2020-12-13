package com.example.amin.followfuck;

import com.example.amin.followfuck.instgram.Reqs;
import com.example.amin.followfuck.instgram.ResponseAction;
import com.example.amin.followfuck.instgram.StatusCodes;
import com.example.amin.followfuck.instgram.models.ContinuedEdges;
import com.example.amin.followfuck.instgram.services.likerofpost.LikersOfPostUsersApi;
import com.example.amin.followfuck.instgram.services.likerofpost.UsersofLikedService;

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
        LikersOfPostUsersApi likersOfPostUsersApi = retrofit.create(LikersOfPostUsersApi.class);
        objectObjectHashMap.put("query_hash", "d5d763b1e2acf209d62d22d184488e57");
        objectObjectHashMap.put("variables", par2);
        Call<ResponseBody> call = likersOfPostUsersApi.getLastFollowings(LoginConfig.cookie, objectObjectHashMap);

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

    @Test
    public void DD() {

        for (int i = 0; true; i++) {


            String shortcode = "CIikZ6QFSfZ";
            UsersofLikedService usersofLikedService = new UsersofLikedService();
            try {
                shortcode = usersofLikedService.lastpostShortcode(fullname -> {

                    System.out.println(fullname + " followers");
                    System.out.println(fullname + " selected");

                });
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (shortcode.equals(""))
                continue;

            try {
                ContinuedEdges firstsfollowers = usersofLikedService.findfirstlikmers(shortcode);
                usersofLikedService.startfollowFollowers(firstsfollowers.postedges, new ResponseAction() {
                    @Override
                    public void applyBeforeSendRequest(Object instaUsername) {
                        System.out.println(((String) instaUsername) + " is requesting...");
                    }

                    @Override
                    public void applyAfterSucces(String instaUsername) {
                        System.out.println(instaUsername + " followed succfully 😍");
                    }

                    @Override
                    public void applyAfterFollowError(String instaUsername, int errorcode) {
                        if (errorcode == StatusCodes.TOMONAYREQUEST) {
                            System.out.println(errorcode);

                        }


                    }
                });

            } catch (Exception e) {


            }


            long millis = (long) ((long) (Math.random() * (60 * 1.5 * 1000)) + 60 * 15 * 1000);
            try {
                String resp = Reqs.getReq("https://www.instagram.com/" + BusinessContext.Username + "/?__a=1");
                JSONObject jsonObject = new JSONObject(resp);
                JSONObject user = (JSONObject) ((JSONObject) jsonObject.get("graphql")).get("user");
                Integer countedge_follow = ((Integer) ((JSONObject) user.get("edge_follow")).get("count"));
                Integer edge_followed_by = ((Integer) ((JSONObject) user.get("edge_followed_by")).get("count"));
                String title = BusinessContext.Username + " " + edge_followed_by + "/" + countedge_follow;

                System.out.println(title);


            } catch (IOException e) {

            } catch (JSONException e) {
                e.printStackTrace();
            }

            int k = 0;
            while (k < 10) {
                try {

                    Thread.sleep(millis / 10);
                    k++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }



}
