package model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import common.Api;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 李英杰 on 2017/9/29.
 */

public class Model {
    private IModel iModel;

    public void Mrequest(String url)  {
        OkHttpClient oc=new OkHttpClient();
        Request request=new Request.Builder().get().url(url).build();
        oc.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                iModel.fail();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String result=response.body().string();
                System.out.println("result========"+result);
                try {
                    JSONObject object=new JSONObject(result);
                    String code = object.getString("code");
                    if (code.equals("0")){
                        iModel.success(result);
                    }else {
                        iModel.requestFaile();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getGridView(String url)  {
        OkHttpClient oc=new OkHttpClient();
        Request request=new Request.Builder().get().url(url).build();
        oc.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                iModel.GridRequestFail();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String result=response.body().string();
                System.out.println("result========"+result);
                try {
                    JSONObject object=new JSONObject(result);
                    String code = object.getString("code");
                    if (code.equals("0")){
                        iModel.GridSuccess(result);
                    }else {
                        iModel.Gridfail();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public void setiModel(IModel iModel){
        this.iModel=iModel;
    }



    public interface IModel{
        void success(String result);
        void requestFaile();
        void fail();
        void GridSuccess(String result);
        void Gridfail();
        void GridRequestFail();
    }

}
