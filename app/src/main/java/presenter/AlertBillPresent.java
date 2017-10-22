package presenter;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import model.AlertBillModel;
import model.AlertModel;
import utils.OkCallBack;
import utils.OkHttpMethed;

/**
 * Created by 李英杰 on 2017/10/22.
 */
public class AlertBillPresent {
    private AlertBillModel alertBillModel;
    private AlertBillPresentView alertBillPresentView;
    private Context context;

    public AlertBillPresent(AlertBillPresentView alertBillPresent, Context context) {
        this.alertBillPresentView = alertBillPresent;
        this.context = context;
        alertBillModel=new AlertBillModel();
    }

    public void AlertBill(OkHttpMethed methed, String url, Map<String,Object> parms){
        alertBillModel.alertBill(context, methed, url, parms, new OkCallBack() {
            @Override
            public void onFailure(String e, String msg) {
                alertBillPresentView.Fail(msg);
            }

            @Override
            public void onResponse(String result) {
                try {
                    JSONObject object=new JSONObject(result);
                    String code = object.getString("code");
                    if ("0".equals(code)){
                        alertBillPresentView.AlertBillSuccessFail(result);
                    }else {
                        alertBillPresentView.AlertBillFail(result);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
