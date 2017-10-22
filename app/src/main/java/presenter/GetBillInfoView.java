package presenter;

/**
 * Created by 李英杰 on 2017/10/21.
 */

public interface GetBillInfoView {
    void Fail(String msg);
    void GetBillFail(String msg);
    void GetBillSuccess(String msg);
}
