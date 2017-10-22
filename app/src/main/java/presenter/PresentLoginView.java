package presenter;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by 李英杰 on 2017/10/10.
 */

public interface PresentLoginView {
    void RequestFail(Call call, IOException e);
    void LoginSuccess(String result);
    void LoginFail(String result);

    void ResSuccess(String result);
    void ResFail(String result);
    void RequestRes(Call call, IOException e);

    void getInfoSuccess(String result);
    void getInfoFail(String result);
    void RequestGetInfoFail(Call call, IOException e);
}
