package utils;

import android.app.Activity;
import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import interceptor.LogInterceptor;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 李英杰 on 2017/10/11.
 */

public class OKHttp {
    private static OKHttp okHttp;
    private Context context;
    private OKHttp(Context context){
        this.context=context;
    }

    public static OKHttp getIntence(Context context){
        if (okHttp==null){
            synchronized (OKHttp.class){
                if (okHttp==null){
                    okHttp=new OKHttp(context);
                }
            }
        }
        return okHttp;
    }

    public void Call(OkHttpMethed methed, String url, Map<String,Object> parms,final OkCallBack callBack){
        Request request=null;
        OkHttpClient.Builder builder=new OkHttpClient.Builder();
        builder.addInterceptor(new LogInterceptor());
        builder.connectTimeout(5, TimeUnit.SECONDS);
        builder.readTimeout(5, TimeUnit.SECONDS);
        builder.writeTimeout(5, TimeUnit.SECONDS);
        OkHttpClient client=builder.build();
        String mUrl=url+"?";
        if (methed == OkHttpMethed.GET) {//get方式请求
            if (parms != null && parms.size() > 0) {
                for (Map.Entry<String, Object> stringObjectEntry : parms.entrySet()) {
                    mUrl += stringObjectEntry.getKey() + "=" + stringObjectEntry.getValue() + "&";
                }
                request = new Request.Builder().url(mUrl).build();
            }
        } else if (methed == OkHttpMethed.POST) {//post方式请求
            FormBody.Builder fbuilder = new FormBody.Builder();
            if (parms != null && parms.size() > 0) {
                for (Map.Entry<String, Object> stringObjectEntry : parms.entrySet()) {
                    fbuilder.add(stringObjectEntry.getKey(), stringObjectEntry.getValue().toString());
                    request = new Request.Builder().url(url).post(fbuilder.build()).build();
/*                    if (stringObjectEntry.getValue() instanceof File){
                        body=new MultipartBody.Builder().setType(MultipartBody.FORM);
                        for (Map.Entry<String, Object> objectEntry : parms.entrySet()) {
                            System.out.println("用户id-------------------"+stringObjectEntry.getValue()+"");
                            if (objectEntry.getKey().equals("uid")){
                                body.addFormDataPart(stringObjectEntry.getKey(),stringObjectEntry.getValue()+"");
                            }else {
                                body.addFormDataPart(stringObjectEntry.getKey(),((File) stringObjectEntry.getValue()).getName(), RequestBody.create(MediaType.parse("image*//*"), (File) stringObjectEntry.getValue()));
                            }
                            request = new Request.Builder().url(url).post(body.build()).build();
                        }
                    }else if (stringObjectEntry.getValue() instanceof String){
                        fbuilder.add(stringObjectEntry.getKey(), stringObjectEntry.getValue().toString());
                        request = new Request.Builder().url(url).post(fbuilder.build()).build();
                    }*/
                }
            }

        }
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                final String msg=e.toString();
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (callBack!=null){
                            callBack.onFailure(msg,"请求失败");
                        }
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final StringBuffer result = new StringBuffer();
                InputStream inputStream = null;
                BufferedReader bufferedReader = null;
                try {
/*                    String msg=response.body().string();
                    System.out.println("上传头像-------------------------------"+msg);*/
                    if (response.isSuccessful()){
                        System.out.println("请求成功===================");
                        inputStream = response.body().byteStream();
                        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                        String content = "";
                        System.out.println("bufferedReader~~~~~~~~~~~~~"+bufferedReader);
                        while ((content=bufferedReader.readLine())!=null){
                            result.append(content);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    bufferedReader.close();
                    inputStream.close();
                }
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (callBack!=null){
                            callBack.onResponse(result.toString());
                        }
                    }
                });
            }
        });

    }

    public void AlertIcon(String url,int uid,File file,final OkCallBack callBack){
        OkHttpClient.Builder builder=new OkHttpClient.Builder();
        builder.addInterceptor(new LogInterceptor());
        OkHttpClient client = builder.build();
        MultipartBody.Builder body=new MultipartBody.Builder().setType(MultipartBody.FORM);
        body.addFormDataPart("uid",uid+"");
        body.addFormDataPart("file",file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
        Request request= new Request.Builder().url(url).post(body.build()).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                final String msg=e.toString();
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (callBack!=null){
                            callBack.onFailure(msg,"请求失败");
                        }
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String msg=response.body().string();
                System.out.println("上传头像-------------------------------"+msg);
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (callBack!=null){
                            callBack.onResponse(msg);
                        }
                    }
                });
            }
        });

    }
}
