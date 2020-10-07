package com.example.amin.followfuck;

import com.example.amin.followfuck.instgram.LoginConfig;
import com.example.amin.followfuck.instgram.UsernameFinder;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {

        try {
            System.out.println(UsernameFinder.find("373148161"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        String id = "8916622827";// last post
        id = "6875751076";// username: "ryhwne_mi" full_name: "ＲＥＹＨＡＮＥ🌙🌸️💫"
//        id = "305851563";// username: reza golzar
        //TODO NEED TO BE AUTHENTICATED WITH COOCKIES
        int number = 16;
        String par = String.format("{\"id\":\"%s\",\"include_reel\":true,\"fetch_mutual\":true,\"first\":}", id, number);
        String ev = URLEncoder.encode(par);//first 1 i see
        String par2 = String.format("{\"id\":\"%s\",\"include_reel\":true,\"fetch_mutual\":false,\"first\":24}", id);
        String ev2 = URLEncoder.encode(par2);//first 1 i see

        String url = "https://www.instagram.com/graphql/query/?query_hash=c76146de99bb02f6415203be841dd25a&variables=" + ev;
        System.out.println(url);
        url = "https://www.instagram.com/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .build();

        FindFollowingService followService = retrofit.create(FindFollowingService.class);
        HashMap<String, String> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("query_hash", "c76146de99bb02f6415203be841dd25a");
        objectObjectHashMap.put("variables", par);
//
        Call<ResponseBody> call = followService.get(LoginConfig.cookie, objectObjectHashMap);
//
//
        Response<ResponseBody> execute = null;
        try {
            execute = call.execute();
            ResponseBody body = execute.body();
            String string = body.string();

            JSONObject jsonObject = new JSONObject(string);

            System.out.println(string);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
//


    }
}