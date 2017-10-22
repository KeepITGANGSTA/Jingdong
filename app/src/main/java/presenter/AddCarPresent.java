package presenter;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import model.AddCarModel;
import utils.OkCallBack;
import utils.OkHttpMethed;

/**
 * Created by 李英杰 on 2017/10/18.
 */

public class AddCarPresent {
    private Context context;
    private AddCarModel addCarModel;
    private AddCarView addCarView;

    public AddCarPresent(Context context, AddCarView addCarView) {
        this.context = context;
        this.addCarView = addCarView;
        addCarModel=new AddCarModel();
    }

    public void addCar(OkHttpMethed methed, String url, Map<String,Object> parms){
        addCarModel.addCar(context, methed, url, parms, new OkCallBack() {
            @Override
            public void onFailure(String e, String msg) {
                addCarView.Fail(msg);
            }

            @Override
            public void onResponse(String result) {
                try {
                    JSONObject object=new JSONObject(result);
                    String code = object.getString("code");
                    if ("0".equals(code)){
                        addCarView.AddSuccess(result);
                    }else {
                        addCarView.AddFail(result);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
