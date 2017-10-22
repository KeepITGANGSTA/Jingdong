package utils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by 李英杰 on 2017/10/11.
 */

public interface OkCallBack {
    void onFailure(String e,String msg);
    void onResponse(String result);
}
