package model;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import utils.NetCallBack;
import utils.NetUtils;
import utils.OKHttp;
import utils.OkCallBack;
import utils.OkHttpMethed;

/**
 * Created by 李英杰 on 2017/10/11.
 */

public class AlertModel {
/*    public void login(Context context,String mobile, String pwd, OkCallback callback) {
        Map<String, Object> params = new HashMap<>();
        params.put("mobile", mobile);
        params.put("password", pwd);
        OkhttpUtils.getOkhttpInstance(context).call(OkHttpMethod.POST,Api.LOGIN,params,callback);
    }*/
    public void AlertNick(Context context, OkHttpMethed method,String url,Map<String,Object> parms, OkCallBack okCallBack){
        OKHttp.getIntence(context).Call(method,url,parms,okCallBack);
    }

    public void AlertIcon(OkHttpMethed method,String url,Map<String,String> parms, NetCallBack okCallBack){
       NetUtils.okhttp(url,parms,okCallBack);
    }

    /**
     * 获取商品子分类
     * @param context
     * @param methed
     * @param url
     * @param parms
     * @param okCallBack
     */
    public void GoodsType(Context context,OkHttpMethed methed,String url,Map<String,Object> parms,OkCallBack okCallBack){
        OKHttp.getIntence(context).Call(methed,url,parms,okCallBack);
    }

}
