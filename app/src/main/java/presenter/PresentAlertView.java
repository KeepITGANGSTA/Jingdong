package presenter;

/**
 * Created by 李英杰 on 2017/10/11.
 */

public interface PresentAlertView {
    void AlertSuccess(String result);
    void AlertFailure(String result);
    void Fail(String e,String msg);

    void GoodsSuccess(String result);
    void GoodsFailure(String result);

}
