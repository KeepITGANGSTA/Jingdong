package presenter;

/**
 * Created by 李英杰 on 2017/10/18.
 */

public interface GetCarView {
    void Fail(String msg);
    void getShopCarSuccess(String result);
    void getShopCarFail(String result);
}
