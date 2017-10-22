package presenter;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import model.AlertModel;
import okhttp3.Call;
import okhttp3.Response;
import utils.NetCallBack;
import utils.OkCallBack;
import utils.OkHttpMethed;

/**
 * Created by 李英杰 on 2017/10/11.
 */

public class AlertPresent {
    private AlertModel alertModel;
    private PresentAlertView presentAlertView;
    private Context context;

    public AlertPresent(PresentAlertView presentAlertView, Context context) {
        this.presentAlertView = presentAlertView;
        this.context = context;
        alertModel=new AlertModel();
    }

    public void AlertNickName(OkHttpMethed method, String url, Map<String,Object> parms){
        alertModel.AlertNick(context, method, url, parms, new OkCallBack() {
            @Override
            public void onFailure(String e, String msg) {
                presentAlertView.Fail(e,msg);
            }

            @Override
            public void onResponse(String result) {
                try {
                    JSONObject object=new JSONObject(result);
                    String code = object.getString("code");
                    if ("0".equals(code)){
                        presentAlertView.AlertSuccess(result);
                    }else {
                        presentAlertView.AlertFailure(result);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void AlertIco(OkHttpMethed method, String url, Map<String,String> parms){
        alertModel.AlertIcon(method, url, parms, new NetCallBack() {
            @Override
            public void onFailure(Call call, IOException e) {
                String s = e.toString();
                presentAlertView.AlertFailure(s);
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    String s=response.body().string();
                    JSONObject object=new JSONObject(s);
                    String code = object.getString("code");
                    if ("0".equals(code)){
                        presentAlertView.AlertSuccess(s);
                    }else {
                        presentAlertView.AlertFailure(s);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void GoodsType(OkHttpMethed method, String url, Map<String,Object> parms){
        alertModel.GoodsType(context, method, url, parms, new OkCallBack() {
            @Override
            public void onFailure(String e, String msg) {
                presentAlertView.Fail(e,msg);
            }

            @Override
            public void onResponse(String result) {
                try {
                    JSONObject object=new JSONObject(result);
                    String code = object.getString("code");
                    if ("0".equals(code)){
                        presentAlertView.GoodsSuccess(result);
                    }else {
                        presentAlertView.GoodsFailure(result);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }



    public void onDestory(){
        presentAlertView=null;
    }



}
