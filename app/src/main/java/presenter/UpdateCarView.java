package presenter;

/**
 * Created by 李英杰 on 2017/10/18.
 */

public interface UpdateCarView {
    void Fail(String msg);
    void UpdateSuccess(String result);
    void UpdateFail(String result);
}
