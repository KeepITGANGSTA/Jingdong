package presenter;

import android.content.Context;

import java.util.Map;

import model.ShopCarModel;
import utils.OkCallBack;
import utils.OkHttpMethed;

/**
 * Created by 李英杰 on 2017/10/18.
 */

public class ShopCarPresent {
    private Context context;
    private ShopCarModel shopCarModel;
    private ShopCarView shopCarView;

    public ShopCarPresent(Context context, ShopCarView shopCarView) {
        this.context = context;
        this.shopCarView = shopCarView;
        shopCarModel=new ShopCarModel();
    }

    public void getShopCar(OkHttpMethed methed, String url, Map<String,Object> parms){
        shopCarModel.getShopCar(context, methed, url, parms, new OkCallBack() {
            @Override
            public void onFailure(String e, String msg) {
                shopCarView.Fail(msg);
            }

            @Override
            public void onResponse(String result) {
                shopCarView.getCarSuccess(result);
            }
        });
    }

}
