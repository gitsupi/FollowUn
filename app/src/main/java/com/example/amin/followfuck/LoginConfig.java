package com.example.amin.followfuck;

/**
 * created By gOD on 7/31/2020 9:56 AM
 */

public class LoginConfig {
    public static String cookie = "ig_cb=2; ig_did=246836D0-1F7B-4C95-AD18-78A58EF7B588; mid=X8JgNQABAAGsDNtFcro-U2C4MdqM; csrftoken=1T73Zp5rrj9meoxEA0Nr1RpgGwoD1S7L; ds_user_id=30299824247; shbid=9033; ig_direct_region_hint=FRC; shbts=1607738432.4921987; rur=FRC; sessionid=30299824247%3AMqYfYkP1053hXi%3A0;";
    public static final String IGCLAIM = "hmac.AR1-7bxLsGG2uEzgHR_NTJAwnGLv9FAjwSV6p7X5dy37xFcE";
    public static final String csrftoken = "1T73Zp5rrj9meoxEA0Nr1RpgGwoD1S7L";

//    public static String X_INSTAGRAM_AJAX = "81a581bb9399";
    public static String X_IG_WWW_CLAIM = IGCLAIM;
    public static String XIG_APP_ID = "936619743392459";


    public static class CookieParamsForSendingFollow {
        public final static String mid = "mid";
        //        public final static String ds_user_id = "ds_user_id";
        public final static String sessionid = "sessionid";
        public final static String ig_did = "ig_did";
        public final static String csrftoken = "csrftoken";
        public final static String shbid = "shbid";
        public final static String shbts = "shbts";
        public final static String rur = "rur";
        public final static String[] all = {mid, sessionid, ig_did, csrftoken, shbid, shbts, rur};
//        public final static String urlgen = "urlgen";

    }


}
