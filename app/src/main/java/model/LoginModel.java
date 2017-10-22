package model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import utils.NetCallBack;
import utils.NetUtils;

/**
 * Created by 李英杰 on 2017/10/10.
 */

public class LoginModel {



    public void login(String url,String phone, String psd, NetCallBack callBack){
        System.out.println("LoginModel");
        Map<String, String> params = new HashMap<>();
        params.put("mobile", phone);
        params.put("password", psd);
        NetUtils.okhttp(url,params,callBack);
    }

    public void res(String url,String phone, String psd, NetCallBack callBack){
        System.out.println("LoginModel");
        Map<String, String> params = new HashMap<>();
        params.put("mobile", phone);
        params.put("password", psd);
        NetUtils.okhttp(url,params,callBack);
    }

    public void getUserInfo(String url,int uid,NetCallBack callBack){
        Map<String, Integer> params = new HashMap<>();
        params.put("uid", uid);
        NetUtils.okGetUserInfo(url,params,callBack);
    }



/*    public void setLoginModel(ILogin iLogin){
        this.iLogin=iLogin;
    }*/
/*    public interface ILogin{
        void RequestFail(Call call, IOException e);
        void LoginSuccess(String result);
        void LoginFail(String result);
    }*/
}