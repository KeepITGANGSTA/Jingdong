package presenter;

/**
 * Created by 李英杰 on 2017/10/21.
 */

public interface BillView {

    void CreateFail(String msg);
    void CreateSuccess(String msg);
    void RequestFail(String msg);

}
