package fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;

import com.bwie.jingdong.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import adapter.GoodsAdapter;
import adapter.GoodsChileAdapter;
import adapter.GridRecycler;
import bean.GoodsBean;
import bean.GoodsEntity;
import butterknife.BindView;
import butterknife.ButterKnife;
import common.Api;
import presenter.AlertPresent;
import presenter.PresentAlertView;
import utils.OkHttpMethed;

/**
 * Created by 李英杰 on 2017/10/13.
 */

public class GoodsFragment extends Fragment implements PresentAlertView{

    private View mRoot;
    private AlertPresent alertPresent;
    private List<GoodsEntity> listG=new ArrayList<>();
    private static GoodsFragment goodsFragment;


    RecyclerView mRecyGoods;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRoot==null){
            mRoot=inflater.inflate(R.layout.goods_view,null);
        }
        ViewGroup parent = (ViewGroup) mRoot.getParent();
        if (parent!=null){
            parent.removeView(mRoot);
        }
        return mRoot;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRoot.findViewById(R.id.mRecyGoods);
        int cid = getArguments().getInt("cid", 0);
        alertPresent = new AlertPresent(this,getContext());
            Map<String,Object> parms=new HashMap<String,Object>();
            parms.put("cid",cid);
            alertPresent.GoodsType(OkHttpMethed.POST, Api.GoodsType,parms);
    }

    public static GoodsFragment getIntencec(int cid){
        Bundle bundle=new Bundle();
        bundle.putInt("cid",cid);
/*        if (goodsFragment==null){
            GoodsFragment goodsFragment = new GoodsFragment();
        }*/
        GoodsFragment goodsFragment = new GoodsFragment();
        goodsFragment.setArguments(bundle);
        return goodsFragment;
    }

    @Override
    public void AlertSuccess(String result) {

    }

    @Override
    public void AlertFailure(String result) {

    }

    @Override
    public void Fail(String e, String msg) {

    }

    @Override
    public void GoodsSuccess(String result) {
        System.out.println("商品子分类--成功"+result);
        Gson gson=new Gson();
        GoodsBean goodsBean = gson.fromJson(result, GoodsBean.class);
        List<GoodsBean.DataBean> data = goodsBean.data;
        listG.clear();
        for (int i = 0; i < data.size(); i++) {
            GoodsEntity goodsEntity=new GoodsEntity();
            GoodsBean.DataBean dataBean = data.get(i);
            goodsEntity.nameT= dataBean.name;
            List<GoodsBean.DataBean.ListBean> list = dataBean.list;
            for (int j = 0; j < list.size(); j++) {
                GoodsBean.DataBean.ListBean listBean = list.get(i);
                goodsEntity.name=listBean.name;
                goodsEntity.pcid=listBean.pcid;
                goodsEntity.icon=listBean.icon;
                goodsEntity.pscid=listBean.pscid;
            }
            listG.add(goodsEntity);
        }
        System.out.println("mRecyGoods="+mRecyGoods);
        GoodsAdapter goodsAdapter=new GoodsAdapter(getActivity(),listG);
        RecyclerView recyclerView=mRoot.findViewById(R.id.mRecyGoods);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new GridRecycler(0,30));
        recyclerView.setAdapter(goodsAdapter);

    }

    @Override
    public void GoodsFailure(String result) {
        System.out.println("商品子分类--失败"+result);
    }
}
