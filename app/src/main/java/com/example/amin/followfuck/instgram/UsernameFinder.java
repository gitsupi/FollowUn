package com.example.amin.followfuck.instgram;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class UsernameFinder {

    public static String find( String id) throws IOException, JSONException {
        String url = "https://www.instagram.com/graphql/query/?query_hash=c9100bf9110dd6361671f113dd02e7d6&variables=%7B%22user_id%22%3A%22" + id + "%22%2C%22include_chaining%22%3Atrue%2C%22include_reel%22%3Atrue%2C%22include_suggested_users%22%3Afalse%2C%22include_logged_out_extras%22%3Afalse%2C%22include_highlight_reels%22%3Atrue%2C%22include_related_profiles%22%3Afalse%7D";
        System.out.println(url);
        String resp = Reqs.getReq(url);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(resp);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String username = (String) ((JSONObject) ((JSONObject) ((JSONObject) ((JSONObject) jsonObject.get("data")).get("user")).get("reel"))
                .get("user")).get("username");
        System.out.println("username is " + username);
        return username;

    }


}
