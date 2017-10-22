package model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 李英杰 on 2017/10/7.
 */

public class GridModel {
    private IGridModel iGridModel;

    public void getGrid(String url){
        OkHttpClient ok=new OkHttpClient();
        Request request=new Request.Builder().get().url(url).build();
        ok.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                iGridModel.faile();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result=response.body().string();
                try {
                    JSONObject object=new JSONObject(result);
                    String code = object.getString("code");
                    if (code.equals("0")){
                        iGridModel.success(result);
                    }else {
                        iGridModel.requestFaile();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void setModel(IGridModel iGridModel){

    }
    public interface IGridModel{
        void success(String result);
        void faile();
        void requestFaile();
    }
}
