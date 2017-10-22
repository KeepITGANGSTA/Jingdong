package presenter;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import model.GetBillModel;
import utils.OkCallBack;
import utils.OkHttpMethed;

/**
 * Created by 李英杰 on 2017/10/21.
 */

public class GetBillPresent {
    private GetBillModel getBillModel;
    private GetBillInfoView getBillInfoView;
    private Context context;

    public GetBillPresent(GetBillInfoView getBillInfoView, Context context) {
        this.getBillInfoView = getBillInfoView;
        this.context = context;
        getBillModel=new GetBillModel();
    }

    public void getBillInfo(OkHttpMethed methed, String url, Map<String,Object> parms){
        getBillModel.getBillInfo(context, methed, url, parms, new OkCallBack() {
            @Override
            public void onFailure(String e, String msg) {
                getBillInfoView.Fail(msg);
            }

            @Override
            public void onResponse(String result) {
                try {
                    JSONObject object=new JSONObject(result);
                    String code = object.getString("code");
                    if ("0".equals(code)){
                        getBillInfoView.GetBillSuccess(result);
                    }else {
                        getBillInfoView.GetBillFail(result);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
