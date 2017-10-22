package com.bwie.jingdong;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 李英杰 on 2017/10/18.
 */

public class Address extends BaseActivity {

    @BindView(R.id.Recy_address)
    RecyclerView Recy_address;
    @BindView(R.id.rel_addAddress)
    RelativeLayout rel_addAddress;
    @BindView(R.id.iv_addressBack)
    ImageView iv_addressBack;

    @Override
    public int bindLayout() {
        return R.layout.address_view;
    }

    @OnClick({R.id.iv_addressBack,R.id.rel_addAddress})
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.iv_addressBack:
                finish();
                break;
            case R.id.rel_addAddress:

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

    }
}
