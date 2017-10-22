package presenter;

import android.content.Context;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import model.UpdateCarModel;
import okhttp3.Call;
import okhttp3.Response;
import utils.NetCallBack;

/**
 * Created by 李英杰 on 2017/10/18.
 */

public class UpdateCarPresent {
    private Context context;
    private UpdateCarModel updateCarModel;
    private UpdateCarView updateCarView;

    public UpdateCarPresent(Context context, UpdateCarView updateCarView) {
        this.context = context;
        this.updateCarView = updateCarView;
        updateCarModel=new UpdateCarModel();
    }

    public void UpdateCar(String url, Map<String,String> parms){
        updateCarModel.UpdateCar(url, parms, new NetCallBack() {
            @Override
            public void onFailure(Call call, IOException e) {
                updateCarView.Fail(e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) {

                try {
                    String result=response.body().string();
                    System.out.println("onResponse------"+result);
                    JSONObject object=new JSONObject(result);
                    String code = object.getString("code");
                    if ("0".equals(code)){
                        updateCarView.UpdateSuccess(result);
                    }else {
                        updateCarView.UpdateFail(result);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
