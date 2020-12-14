package com.example.amin.followfuck;

import com.example.amin.followfuck.instgramapi.services.comments.LeaveCommentRequests;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;
import java.net.URLEncoder;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class CommnetTest {
    @Test
    public void addition_isCorrect() {

        String id = "8916622827";// last post
        id = "38081432117";
        id = "30299824247";
        id = "30134076076";//DAYIPET
        String postid = "2428705559955727132";


        String par2 = String.format("{\"id\":\"%s\",\"include_reel\":true,\"fetch_mutual\":false,\"first\":12}", id);
        String ev2 = URLEncoder.encode(par2);//first 1 i see

        String url = null;
        url = "https://www.instagram.com";
        while (true)
        {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .build();

            LeaveCommentRequests leaveCommentRequests = retrofit.create(LeaveCommentRequests.class);
            Call<ResponseBody> call = leaveCommentRequests.leaveAComment(LoginConfig.cookie, postid,
                    " سلام وسیله بازی سگ دایرکت ✌"
                    , ""
            );
            Response<ResponseBody> execute;
            try {
                execute = call.execute();
                ResponseBody body = execute.body();
                //System.out.println(execute.code());
                String string = body.string();
                JSONObject jsonObject = new JSONObject(string);
                //System.out.println(string);
                Thread.sleep(2 * 60 * 1000);


            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }
}