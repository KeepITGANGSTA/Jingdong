package presenter;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import model.DetailsModel;
import okhttp3.Call;
import okhttp3.Response;
import utils.NetCallBack;
import utils.OkCallBack;
import utils.OkHttpMethed;

/**
 * Created by 李英杰 on 2017/10/17.
 */

public class DetailsPresent {
    private DetailsModel detailsModel;
    private DetailsView detailsView;
    private Context context;

    public DetailsPresent( DetailsView detailsView, Context context) {
        this.detailsView = detailsView;
        this.context = context;
        detailsModel=new DetailsModel();
    }

    public void GoodsDetails(String url,Map<String,String> parms){
        detailsModel.GoodsDetails(url, parms, new NetCallBack() {
            @Override
            public void onFailure(Call call, IOException e) {
                detailsView.Fail(e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    String result=response.body().string();
                    JSONObject object=new JSONObject(result);
                    String code = object.getString("code");
                    if ("0".equals(code)){
                        detailsView.SearchSuccess(result);
                    }else {
                        detailsView.SearchFail(result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

  /*  public void GoodsDetails(OkHttpMethed methed, String url, Map<String,Object> parms){
        detailsModel.GoodsDetails(context, methed, url, parms, new OkCallBack() {
            @Override
            public void onFailure(String e, String msg) {
                detailsView.Fail(msg);
            }

            @Override
            public void onResponse(String result) {
                System.out.println("DetailsPresent-"+result);
                try {
                    JSONObject object=new JSONObject(result);
                    String code = object.getString("code");
                    if ("0".equals(code)){
                        detailsView.SearchSuccess(result);
                    }else {
                        detailsView.SearchFail(result);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }*/

    public void onDestory(){
        detailsView=null;
    }
}
