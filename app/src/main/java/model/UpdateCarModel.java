package model;

import java.util.Map;

import utils.NetCallBack;
import utils.NetUtils;

/**
 * Created by 李英杰 on 2017/10/18.
 */

public class UpdateCarModel {
    public void UpdateCar(String url, Map<String,String> parms, NetCallBack netCallBack){
        NetUtils.okhttp(url,parms,netCallBack);
    }
}
