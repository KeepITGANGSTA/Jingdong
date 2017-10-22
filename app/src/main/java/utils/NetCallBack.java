package utils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by 李英杰 on 2017/10/10.
 */

public interface NetCallBack {
    void onFailure(Call call, IOException e);
    void onResponse(Call call, Response response);
}
