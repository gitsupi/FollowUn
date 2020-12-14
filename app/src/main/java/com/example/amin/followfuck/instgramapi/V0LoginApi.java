package com.example.amin.followfuck.instgramapi;

import com.example.amin.followfuck.BusinessContext;
import com.example.amin.followfuck.LoginConfig;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * created By gOD on 12/13/2020 11:33 PM
 */

public class V0LoginApi {


    public static void setLoginPropertiesASync(String username, String password) throws IOException, JSONException {
        Document document = Jsoup.connect("https://www.instagram.com/accounts/login/")
                .get();


        Element script = document.getElementsByTag("script").get(3);
        String obj = script.html().split("window._sharedData = ")[1].split(";")[0];
        JSONObject jsonObject = new JSONObject(obj);
        String csrftoken = ((JSONObject) jsonObject.get("config")).get("csrf_token").toString();


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


        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String string = response.body().string();
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

                try {
                    BusinessContext.UserID = new JSONObject(string).getString("userId");
                    BusinessContext.initsetup();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                BusinessContext.cookie = cookie;
                BusinessContext.csrftoken = realcsrf;
            }
        });
    }


    static Request.Builder applyHeaders(Request.Builder req, String csrftoken) {
        req.addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36");
        req.addHeader("Content-Type", "application/x-www-form-urlencoded");
        req.addHeader("x-csrftoken", csrftoken);
        return req;

    }

}
