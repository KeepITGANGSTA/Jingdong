package presenter;

import android.content.Context;

import java.util.Map;

import model.GetCarModel;
import model.ShopCarModel;
import utils.OkCallBack;
import utils.OkHttpMethed;

/**
 * Created by 李英杰 on 2017/10/18.
 */

public class GetCarPresent {
    private Context context;
    private GetCarModel shopCarModel;
    private GetCarView shopCarView;

    public GetCarPresent(Context context, GetCarView shopCarView) {
        this.context = context;
        this.shopCarView = shopCarView;
        shopCarModel=new GetCarModel();
    }

    public void getShopCar(OkHttpMethed methed, String url, Map<String,Object> parms){
        shopCarModel.getShopCar(context, methed, url, parms, new OkCallBack() {
            @Override
            public void onFailure(String e, String msg) {
                shopCarView.Fail(msg);
            }

            @Override
            public void onResponse(String result) {
                shopCarView.getShopCarSuccess(result);
            }
        });
    }

}
