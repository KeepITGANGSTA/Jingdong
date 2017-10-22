package presenter;

/**
 * Created by 李英杰 on 2017/10/16.
 */

public interface DetailsView {
    void Fail(String msg);
    void SearchSuccess(String result);
    void SearchFail(String resulet);
}
