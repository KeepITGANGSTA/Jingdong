package presenter;

/**
 * Created by 李英杰 on 2017/9/29.
 */

public interface PresentView {
    void success(String result);
    void requestFail();
    void fail();
    void GridSuccess(String result);
    void Gridfail();


}
