package com.example.amin.followfuck.instgram.services.likerofpost;

import com.example.amin.followfuck.instgram.LoginConfig;
import com.example.amin.followfuck.instgram.Reqs;
import com.example.amin.followfuck.instgram.ResponseAction;
import com.example.amin.followfuck.instgram.ShowTitleNotification;
import com.example.amin.followfuck.instgram.StatusCodes;
import com.example.amin.followfuck.instgram.UsernameFinder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UsersofLikedService {


    public void startfollowFollowers(JSONArray followersOfinfluencers, ResponseAction responseAction) throws JSONException, IOException {

        for (int i = 0; i < followersOfinfluencers.length(); i++) {
            //System.out.println(i);
            JSONObject node = (JSONObject) followersOfinfluencers.getJSONObject(i).get("node");
            String followingid = (String) node.get("id");
            boolean followed_by_viewer = (boolean) node.get("followed_by_viewer");
            String username = (String) node.get("username");

            if (followed_by_viewer) {
                responseAction.applyAfterFollowError(username, StatusCodes.USERFOLLOWEDBEFORE);
                continue;
            }

            responseAction.applyBeforeSendRequest(username);
            int resonsecode = sendFollowingRequest(followingid);
            if (resonsecode == StatusCodes.OK) responseAction.applyAfterSucces(username);
            else responseAction.applyAfterFollowError(username, resonsecode);
        }

    }

    public String lastpostShortcode(ShowTitleNotification showTitleNotification) throws IOException, JSONException {
        String id = selectRandomCelebrityId(showTitleNotification);
        String usr = UsernameFinder.find(id);

        String resp = Reqs.getReq("https://www.instagram.com/" + usr + "/?__a=1");
        JSONObject jsonObject = new JSONObject(resp);
        JSONObject user = (JSONObject) ((JSONObject) jsonObject.get("graphql")).get("user");
        String shortcode = ((String) ((JSONObject) user.get("shortcode")).get("count"));
        return shortcode;
    }


    public String selectRandomCelebrityId(ShowTitleNotification showTitleNotification) {

        String[] ids = {"305851563"};

/*
* , "1296464116", "373148161", "241999282", "1705191588", "438501706", "639139147"
                , "1791861245", "994174586", "360507308", "1929875881", "540559218", "461064840", "145715496"
                , "682153522", "28025883"
                */
        String idofInbfluencer = ids[(int) (Math.random() * 1000000) % ids.length];
        try {
            String s = UsernameFinder.find(idofInbfluencer);
            showTitleNotification.apply(s);

        } catch (IOException e) {
            showTitleNotification.apply(e.getMessage());
        } catch (JSONException e) {
            showTitleNotification.apply(e.getLocalizedMessage());
        }
        return idofInbfluencer;
    }


    public int sendFollowingRequest(String followerId) throws IOException {
        String uri = "https://www.instagram.com/web/friendships/" + followerId + "/follow/";

        RequestBody reqbody = RequestBody.create(null, new byte[0]);
        Request.Builder builder = new Request.Builder()
                .url(uri)
                .method("POST", reqbody);

        builder.addHeader("Content-type", "application/x-www-form-urlencoded");
        builder.addHeader("referer", "https://www.instagram.com/p/B7nf91_hkaQ/");
        builder.addHeader("x-csrftoken", LoginConfig.csrftoken);
        builder.addHeader("x-ig-app-id", LoginConfig.XIG_APP_ID);
        builder.addHeader("x-ig-www-claim", LoginConfig.X_IG_WWW_CLAIM);
//        builder.addHeader("x-instagram-ajax", LoginConfig.X_INSTAGRAM_AJAX);
        builder.addHeader("x-requested-with", "XMLHttpRequest");
        builder.addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36");
        builder.addHeader("cookie", LoginConfig.cookie);

        Request request = builder
                .build();
        final OkHttpClient client = new OkHttpClient();

        okhttp3.Response re = client.newCall(request).execute();
        return re.code();

    }


    public JSONArray find(String shortcode) throws Exception {
        HashMap<String, String> objectObjectHashMap = new HashMap<>();

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

            JSONObject jsonObject = new JSONObject(string);

            JSONArray postedges = (JSONArray) ((JSONObject) ((JSONObject) ((JSONObject) jsonObject
                    .get("data"))
                    .get("shortcode_media"))
                    .get("edge_liked_by"))
                    .get("edges");


            String end_cursor = (String) ((JSONObject) ((JSONObject) ((JSONObject) ((JSONObject) jsonObject
                    .get("data"))
                    .get("user")).get("edge_follow")).get("page_info")).get("end_cursor");

            boolean has_next_page = (boolean) ((JSONObject) ((JSONObject) ((JSONObject) ((JSONObject) jsonObject
                    .get("data"))
                    .get("user")).get("edge_follow")).get("page_info")).get("has_next_page");

            return postedges;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }


}
