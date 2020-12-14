package com.example.amin.followfuck.instgramapi.login;

import com.example.amin.followfuck.BusinessContext;
import com.example.amin.followfuck.LoginConfig;
import com.example.amin.followfuck.instgramapi.UsernameFinder;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * created By gOD on 12/13/2020 11:33 PM
 */

public class V0LoginApi {


    public static Respnse performLogin(String username, String password) throws JsoupException, JSONException,
            OKHttpExecuteException, ResponseTostringException, NullStringResponseException, ExceptionalRespnse {
//
//        Document document = null;
//        try {
//            document = Jsoup.connect("https://www.instagram.com/accounts/login/")
//                    .get();
//        } catch (IOException e) {
//            throw new JsoupException(e.getMessage());
//        }
//
//
//        Element script = document.getElementsByTag("script").get(3);
//        String obj = script.html().split("window._sharedData = ")[1].split(";")[0];
//        JSONObject jsonObject = new JSONObject(obj);
//        String csrftoken = ((JSONObject) jsonObject.get("config")).get("csrf_token").toString();
//
//        System.out.println(csrftoken);

       String csrftoken = "ZZP87GVtStLB0mYgDlWmPskuwwc83YwC";



        Request.Builder builder = new Request.Builder()
                .url("https://www.instagram.com/accounts/login/ajax/");
//        String username = "adidasberan";
//        String password = "godisgreat";
        String encpass = String.format("#PWD_INSTAGRAM_BROWSER:0:%d:%s", System.currentTimeMillis() / 1000, password);


        FormBody body = new FormBody.Builder()
                .add("username", username)
                .add("enc_password", encpass)
                .add("queryParams", "{}")
                .add("optIntoOneTap", "false")
                .build();

        Request request = applyHeaders(builder, csrftoken).post(body).build();


        Call call = new OkHttpClient().newCall(request);

        Response response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            throw new OKHttpExecuteException(e.getMessage());
        }


        String string = null;
        try {
            string = response.body().string();

        } catch (IOException e) {
            throw new ResponseTostringException(e.getMessage());
        } catch (NullPointerException nullPointerException) {
            throw new NullStringResponseException(nullPointerException.getMessage());
        }

        System.err.println(string);

        JSONObject respjson = new JSONObject(string);
        final String status = (String) respjson.get("status");

        if (status.equals(LoginResponse.Status.fail))
            throw new ExceptionalRespnse(status);


        boolean authenticated = (boolean) respjson.get("authenticated");


        if (authenticated) {

            System.out.println(string);
//                List<String> headers = response.headers("Set-Cookie");
            String cookie = "";
            String realcsrf = "";
            for (String header : response.headers("set-cookie")) {
                if (header.contains(LoginConfig.CookieParamsForSendingFollow.csrftoken)) {
                    cookie = cookie + " " + header.split(";")[0] + ";";
                    realcsrf = header.split(";")[0];
                }
                if (header.contains(LoginConfig.CookieParamsForSendingFollow.sessionid))
                    cookie = cookie + " " + header.split(";")[0] + ";";
                if (header.contains(LoginConfig.CookieParamsForSendingFollow.mid))
                    cookie = cookie + " " + header.split(";")[0] + ";";
                if (header.contains(LoginConfig.CookieParamsForSendingFollow.ig_did))
                    cookie = cookie + " " + header.split(";")[0] + ";";
                if (header.contains(LoginConfig.CookieParamsForSendingFollow.shbid))
                    cookie = cookie + " " + header.split(";")[0] + ";";
                if (header.contains(LoginConfig.CookieParamsForSendingFollow.shbts))
                    cookie = cookie + " " + header.split(";")[0] + ";";
                if (header.contains(LoginConfig.CookieParamsForSendingFollow.rur))
                    cookie = cookie + " " + header.split(";")[0] + ";";
            }

            BusinessContext.UserID = respjson.getString("userId");
            BusinessContext.cookie = cookie;
            BusinessContext.csrftoken = realcsrf;



            AuthenticatedResponse authenticatedResponse = new Gson().fromJson(string, AuthenticatedResponse.class);
            return authenticatedResponse;
        } else {
            boolean user = (boolean) respjson.get("user");
            if (user)
                throw new ExceptionalRespnse("password is wrong");
            else
                throw new ExceptionalRespnse("user is not found");


        }



    }


    static Request.Builder applyHeaders(Request.Builder req, String csrftoken) {
        req.addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36");
        req.addHeader("Content-Type", "application/x-www-form-urlencoded");
        req.addHeader("x-csrftoken", csrftoken);
        return req;

    }

}
