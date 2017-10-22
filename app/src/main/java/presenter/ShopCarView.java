package presenter;

/**
 * Created by 李英杰 on 2017/10/18.
 */

public interface ShopCarView {
    void Fail(String msg);
    void getCarSuccess(String result);
    void getCarFail(String result);
}
