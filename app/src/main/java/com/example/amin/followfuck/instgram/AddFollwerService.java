package com.example.amin.followfuck.instgram;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class AddFollwerService {


    public void startfollowFollowers(JSONArray followersOfinfluencers, ResponseAction responseAction) throws JSONException, IOException {

        for (int i = 0; i < followersOfinfluencers.length(); i++) {
            System.out.println(i);
            JSONObject node = (JSONObject) followersOfinfluencers.getJSONObject(i).get("node");
            String followingid = (String) node.get("id");

            String username = (String) node.get("username");
            responseAction.applyBeforeSendRequest(username);
            int resonsecode = sendFollowingRequest(followingid);
            if (resonsecode == StatusCodes.OK) responseAction.applyAfterSucces(username);
            else responseAction.applyAfterFollowError(username, resonsecode);
        }

    }

    public String selectRandomCelebrityId(ShowTitleNotification showTitleNotification) {

        String[] ids = {"1296464116", "305851563", "373148161", "241999282", "1705191588", "438501706", "639139147"
                , "1791861245", "994174586", "360507308", "1929875881", "540559218", "461064840", "145715496"
                , "682153522", "28025883"};

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


    public JSONArray findFirstsfollowers(String idofInfluencer) throws Exception {

//        String id = "8916622827";// last post
//        id = "6875751076";// username: "ryhwne_mi" full_name: "ＲＥＹＨＡＮＥ🌙🌸️💫"
//        id = "305851563";// username: reza golzar
        //TODO NEED TO BE AUTHENTICATED WITH COOCKIES
        int number = 14;
        String par = String.format("{\"id\":\"%s\",\"include_reel\":true,\"fetch_mutual\":true,\"first\":%d}", idofInfluencer, number);
        String ev = URLEncoder.encode(par);//first 1 i see

        String url = "https://www.instagram.com/graphql/query/?query_hash=c76146de99bb02f6415203be841dd25a&variables=" + ev;
        // String url = "https://www.instagram.com/";

//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(url)
//                .build();

//        FindFollowingService followService = retrofit.create(FindFollowingService.class);
//        HashMap<String,String> objectObjectHashMap = new HashMap<>();
//        objectObjectHashMap.put("query_hash","c76146de99bb02f6415203be841dd25a");
//        objectObjectHashMap.put("variables", ev);
//
//        Call<String> call = followService.get(LoginConfig.cookie, objectObjectHashMap);
//
//
//        Response<String> execute = call.execute();
//
//        String body = execute.body();


        Request request = new Request.Builder()
                .url(url)
                .addHeader("cookie", LoginConfig.cookie)
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.117 Safari/537.36")
                .build();
        final OkHttpClient client = new OkHttpClient();

        try (okhttp3.Response re = client.newCall(request).execute()) {
            if (!re.isSuccessful()) throw new IOException("Unexpected code " + re);

            JSONObject jsonObject = new JSONObject(re.body().string());

            return (JSONArray) ((JSONObject) ((JSONObject) ((JSONObject) jsonObject
                    .get("data"))
                    .get("user")).get("edge_followed_by"))
                    .get("edges");
        }


    }

    private int sendFollowingRequest(String followerId) throws IOException {
        String uri = "https://www.instagram.com/web/friendships/" + followerId + "/follow/";

//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(uri)
//                .build();
//
//        FollowRequestService followRequestService = retrofit.create(FollowRequestService.class);
//        Call<String> post = followRequestService.post(LoginConfig.cookie, LoginConfig.csrf, "");

        RequestBody reqbody = RequestBody.create(null, new byte[0]);
        Request.Builder builder = new Request.Builder()
                .url(uri)
                .method("POST", reqbody);

        builder.addHeader("Content-type", "application/x-www-form-urlencoded");
        builder.addHeader("referer", "https://www.instagram.com/p/B7nf91_hkaQ/");
        builder.addHeader("x-csrftoken", LoginConfig.csrf);
        builder.addHeader("x-ig-app-id", LoginConfig.XIG_APP_ID);
        builder.addHeader("x-ig-www-claim", LoginConfig.X_IG_WWW_CLAIM);
        builder.addHeader("x-instagram-ajax", LoginConfig.X_INSTAGRAM_AJAX);
        builder.addHeader("x-requested-with", "XMLHttpRequest");
        builder.addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36");
        builder.addHeader("cookie", LoginConfig.cookie);

        Request request = builder
                .build();
        final OkHttpClient client = new OkHttpClient();

        okhttp3.Response re = client.newCall(request).execute();
        System.out.println(re.body().string());
        return re.code();

//        httpPost.setEntity(null);
//        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
//        httpPost.setHeader("referer", "https://www.instagram.com/p/B7nf91_hkaQ/");
//        httpPost.setHeader("x-csrftoken", LoginConfig.csrf);
//        httpPost.setHeader("x-ig-app-id", "936619743392459");
//        httpPost.setHeader("x-ig-www-claim", "hmac.AR050a6T1x8GV3ajRljbbHZ8PdDvHeGf92e5aat3GEOxYby_\n");
//        httpPost.setHeader("x-instagram-ajax", "4c064cca12e4");
//        httpPost.setHeader("x-requested-with", "XMLHttpRequest");

    }


}
