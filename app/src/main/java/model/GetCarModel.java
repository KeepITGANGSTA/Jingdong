package model;

import android.content.Context;

import java.util.Map;

import utils.OKHttp;
import utils.OkCallBack;
import utils.OkHttpMethed;

/**
 * Created by 李英杰 on 2017/10/18.
 */

public class GetCarModel {
    public void getShopCar(Context context, OkHttpMethed methed, String url, Map<String,Object> parms, OkCallBack okCallBack){
        OKHttp.getIntence(context).Call(methed,url,parms,okCallBack);
    }
}
