package utils;

import java.io.IOException;
import java.util.Map;

import interceptor.LogInterceptor;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 李英杰 on 2017/9/29.
 */

public class NetUtils {
    private Request.Builder request;

    public static void okhttp(String url, Map<String,String> parms, final NetCallBack callBack){

        OkHttpClient.Builder oc=new OkHttpClient.Builder();
        oc.addInterceptor(new LogInterceptor());
        OkHttpClient ok=oc.build();
        FormBody.Builder formBody=new FormBody.Builder();
        for (Map.Entry<String, String> stringStringEntry : parms.entrySet()) {
            formBody.add(stringStringEntry.getKey(),stringStringEntry.getValue());
        }
        RequestBody body= formBody.build();
        Request request=new Request.Builder()
                .post(body)
                .url(url)
                .build();
        ok.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.onFailure(call,e);
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                callBack.onResponse(call,response);
            }
        });


    }


    public static void okGetUserInfo(String url, Map<String,Integer> parms, final NetCallBack callBack){
        System.out.println("NetUtils");
        OkHttpClient.Builder oc=new OkHttpClient.Builder();
        oc.addInterceptor(new LogInterceptor());
        OkHttpClient ok=oc.build();
        FormBody.Builder formBody=new FormBody.Builder();
        for (Map.Entry<String, Integer> stringStringEntry : parms.entrySet()) {
            formBody.add(stringStringEntry.getKey(),stringStringEntry.getValue()+"");
        }
        RequestBody body= formBody.build();
        Request request=new Request.Builder()
                .post(body)
                .url(url)
                .build();
        ok.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.onFailure(call,e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                callBack.onResponse(call,response);
            }
        });
    }

}
