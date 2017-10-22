package presenter;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import model.BillModel;
import utils.OkCallBack;
import utils.OkHttpMethed;

/**
 * Created by 李英杰 on 2017/10/21.
 */

public class BillPresent {
    private BillModel billModel;
    private BillView billView;
    private Context context;

    public BillPresent(BillView billView, Context context) {
        this.billView = billView;
        this.context = context;
        billModel=new BillModel();
    }

    public void CreateBill(OkHttpMethed methed, String url, Map<String,Object> parms){
        billModel.CreateBill(context, methed, url, parms, new OkCallBack() {
            @Override
            public void onFailure(String e, String msg) {
                billView.RequestFail(msg);
            }
            @Override
            public void onResponse(String result) {
                try {
                    JSONObject object=new JSONObject(result);
                    String code = object.getString("code");
                    if ("0".equals(code)){
                        billView.CreateSuccess(result);
                    }else {
                        billView.CreateFail(result);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
