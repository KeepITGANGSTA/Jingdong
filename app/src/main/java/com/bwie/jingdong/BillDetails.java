package com.bwie.jingdong;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.BillAdapter;
import bean.BillBean;
import bean.BillInfoBean;
import butterknife.BindView;
import butterknife.OnClick;
import common.Api;
import presenter.GetBillInfoView;
import presenter.GetBillPresent;
import utils.OkHttpMethed;
import utils.SharedPreferencesUtil;

/**
 * Created by 李英杰 on 2017/10/21.
 */

public class BillDetails extends BaseActivity implements GetBillInfoView{

    @BindView(R.id.iv_billBack)
    ImageView iv_billBack;
    @BindView(R.id.recy_bill)
    RecyclerView recy_bill;
    private SharedPreferences preferences;
    private SharedPreferences.Editor edit;
    private GetBillPresent getBillPresent;

    private List<BillInfoBean> list=new ArrayList<>();
    private BillAdapter billAdapter;

    @Override
    public int bindLayout() {
        return R.layout.bill_details;
    }

    @OnClick({R.id.iv_billBack})
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.iv_billBack:
                finish();
                break;
        }
    }


    @Override
    public void setListener() {

    }

    @Override
    public void Click(View view) {

    }

    @Override
    public void initView() {
    }

    @Override
    public void initData() {
        getBillPresent = new GetBillPresent(this,this);
        preferences = SharedPreferencesUtil.getPreferences();
        int userID = preferences.getInt("UserID", 0);
        Map<String,Object> parms=new HashMap<>();
        parms.put("uid",userID);
        getBillPresent.getBillInfo(OkHttpMethed.POST, Api.GetBillInfo,parms);
    }

    @Override
    public void Fail(String msg) {
        System.out.println("失败~~~~~~~~"+msg);
    }

    @Override
    public void GetBillFail(String msg) {
        System.out.println("订单信息~~~~~~~~"+msg);
    }

    @Override
    public void GetBillSuccess(String msg) {
        System.out.println("订单信息请求成功~~~~~~~~"+msg);
        Gson gson=new Gson();
        BillBean billBean = gson.fromJson(msg, BillBean.class);
        List<BillBean.DataBean> data = billBean.data;
        list.clear();
        for (int i = 0; i < data.size(); i++) {
            BillBean.DataBean dataBean = data.get(i);
            BillInfoBean billInfoBean=new BillInfoBean();
            billInfoBean.billPrice=dataBean.price;
            billInfoBean.billTime=dataBean.createtime;
            billInfoBean.orderid=dataBean.orderid;
            billInfoBean.status=dataBean.status;
            list.add(billInfoBean);
        }
        billAdapter = new BillAdapter(this,list);
        recy_bill.setLayoutManager(new LinearLayoutManager(this));
        recy_bill.setAdapter(billAdapter);
    }

/*    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences p = SharedPreferencesUtil.getPreferences();
        if (getBillPresent!=null){
            int userID = p.getInt("UserID", 0);
            Map<String,Object> parms=new HashMap<>();
            parms.put("uid",userID);
            getBillPresent.getBillInfo(OkHttpMethed.POST, Api.GetBillInfo,parms);
        }

    }*/
}
