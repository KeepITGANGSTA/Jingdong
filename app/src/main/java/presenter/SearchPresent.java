package presenter;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import model.SearchModel;
import utils.OkCallBack;
import utils.OkHttpMethed;

/**
 * Created by 李英杰 on 2017/10/16.
 */

public class SearchPresent {
    private SearchModel searchModel;
    private SearchView searchView;
    private Context context;

    public SearchPresent(SearchView searchView, Context context) {
        this.searchView = searchView;
        this.context = context;
        searchModel=new SearchModel();
    }

    public void SearchGoods(OkHttpMethed methed, String url, Map<String,Object> parms){
        searchModel.SearchGoods(context, methed, url, parms, new OkCallBack() {
            @Override
            public void onFailure(String e, String msg) {
                searchView.Fail(msg);
            }

            @Override
            public void onResponse(String result) {
                try {
                    JSONObject object=new JSONObject(result);
                    String code = object.getString("code");
                    if ("0".equals(code)){
                        searchView.SearchSuccess(result);
                    }else {
                        searchView.SearchFail(result);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
