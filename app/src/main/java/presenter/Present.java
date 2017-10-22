package presenter;

import model.Model;

/**
 * Created by 李英杰 on 2017/9/29.
 */

public class Present implements Model.IModel{

    private Model model;
    private PresentView pv;

    public Present(PresentView presentView){
        this.pv=presentView;
        model=new Model();
        model.setiModel(this);
    }


    public void Prequest(String url){
        model.Mrequest(url);
    }

    public void getGrid(String url){
        model.getGridView(url);
    }

    @Override
    public void success(String result) {
        pv.success(result);
    }

    @Override
    public void requestFaile() {

    }

    @Override
    public void fail() {

    }


    @Override
    public void GridSuccess(String result) {
        pv.GridSuccess(result);
    }

    @Override
    public void Gridfail() {
        pv.Gridfail();
    }

    @Override
    public void GridRequestFail() {
        pv.requestFail();
    }

    public void Destory(){
        pv=null;
    }

}
