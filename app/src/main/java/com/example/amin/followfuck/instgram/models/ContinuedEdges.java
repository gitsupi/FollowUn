package com.example.amin.followfuck.instgram.models;

import org.json.JSONArray;

public class ContinuedEdges {
    public JSONArray postedges;
    public String end_cursor;
    public boolean has_next_page;

    public ContinuedEdges(JSONArray postedges, String end_cursor, boolean has_next_page) {
        this.postedges = postedges;
        this.end_cursor = end_cursor;
        this.has_next_page = has_next_page;
    }
}
