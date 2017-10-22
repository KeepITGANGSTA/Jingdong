package presenter;

/**
 * Created by 李英杰 on 2017/10/16.
 */

public interface SearchView {
    void Fail(String msg);
    void SearchSuccess(String result);
    void SearchFail(String resulet);
}
