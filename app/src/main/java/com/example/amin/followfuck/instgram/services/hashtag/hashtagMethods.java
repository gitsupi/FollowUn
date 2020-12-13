package com.example.amin.followfuck.instgram.services.hashtag;

import com.example.amin.followfuck.instgram.models.ContinuedEdges;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class hashtagMethods {

    public hashtagMethods(JSONObject graphqlhashtag) {
        this.graphqlhashtag = graphqlhashtag;
    }

    public JSONObject graphqlhashtag;

    public JSONArray topposts() {
        try {
            return ((JSONArray) ((JSONObject) graphqlhashtag.get("edge_hashtag_to_top_posts")).get("edges"));

        } catch (JSONException e) {
            return null;
        }
    }

    public ContinuedEdges timelineposts() {
        try {

            JSONObject edge_hashtag_to_media = (JSONObject) graphqlhashtag.get("edge_hashtag_to_media");
            JSONArray jsonArray = ((JSONArray) edge_hashtag_to_media.get("edges"));

            JSONObject page_info = (JSONObject) edge_hashtag_to_media.get("page_info");
            String end_cursor = page_info.get("end_cursor").toString();
            boolean has_end_cursor = (boolean) page_info.get("has_end_cursor");

            return new ContinuedEdges(jsonArray, end_cursor, has_end_cursor);

        } catch (JSONException e) {
            return null;
        }
    }




}
