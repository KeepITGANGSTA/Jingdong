package model;

import android.content.Context;

import java.util.Map;

import utils.NetCallBack;
import utils.NetUtils;
import utils.OKHttp;
import utils.OkCallBack;
import utils.OkHttpMethed;

/**
 * Created by 李英杰 on 2017/10/17.
 */

public class DetailsModel {
/*    public void GoodsDetails(Context context, OkHttpMethed methed, String url, Map<String,Object> parms, OkCallBack okCallBack){
        OKHttp.getIntence(context).Call(methed,url,parms,okCallBack);
    }*/
    public void GoodsDetails(String url, Map<String,String> parms, NetCallBack netCallBack){
        NetUtils.okhttp(url,parms,netCallBack);
    }
}
