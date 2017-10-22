package presenter;

import android.app.Activity;
import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.ProcessingInstruction;

import java.io.IOException;

import model.LoginModel;
import okhttp3.Call;
import okhttp3.Response;
import utils.NetCallBack;

/**
 * Created by 李英杰 on 2017/10/10.
 */

public class LoginPresent{

    private LoginModel loginModel;
    private PresentLoginView presentLoginView;
    private Context context;

    public LoginPresent(PresentLoginView presentLoginView,Context context) {
        this.presentLoginView = presentLoginView;
        loginModel=new LoginModel();
        this.context=context;
    }

    public void Login(String url, String phone, String psd){
        System.out.println("LoginPresent");
        loginModel.login(url, phone, psd, new NetCallBack() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("-------------------------");
                presentLoginView.RequestFail(call,e);
            }

            @Override
            public void onResponse(Call call, final Response response) {
                System.out.println("+++++++++++++++++++++++++++++");
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String result=response.body().string();
                            System.out.println("response----"+result);
                            JSONObject o=new JSONObject(result);
                            String code = o.getString("code");
                            if ("0".equals(code)){
                                presentLoginView.LoginSuccess(result);
                            }else {
                                presentLoginView.LoginFail(result);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });


            }
        });
    }

    public void Res(String url, String phone, String psd){
        System.out.println("LoginPresent");
        loginModel.login(url, phone, psd, new NetCallBack() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("-------------------------");
                presentLoginView.RequestRes(call,e);
            }

            @Override
            public void onResponse(Call call, final Response response) {
                System.out.println("+++++++++++++++++++++++++++++");
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String result=response.body().string();
                            System.out.println("response----"+result);
                            JSONObject o=new JSONObject(result);
                            String code = o.getString("code");
                            if ("0".equals(code)){
                                presentLoginView.ResSuccess(result);
                            }else {
                                presentLoginView.ResFail(result);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });


            }
        });
    }


    public void getUserInfo(String url, int uid){
        System.out.println("LoginPresent");
        loginModel.getUserInfo(url, uid, new NetCallBack() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("-------------------------");
                presentLoginView.RequestRes(call,e);
            }

            @Override
            public void onResponse(Call call, final Response response) {
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String result=response.body().string();
                            System.out.println("response----"+result);
                            JSONObject o=new JSONObject(result);
                            String code = o.getString("code");
                            if ("0".equals(code)){
                                presentLoginView.getInfoSuccess(result);
                            }else {
                                presentLoginView.getInfoFail(result);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });


            }
        });
    }



}