package com.bwie.jingdong;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 李英杰 on 2017/10/22.
 */

public class PaySuccess extends BaseActivity {
    @BindView(R.id.iv_paySuccess)
    ImageView iv_paySuccess;
    @BindView(R.id.iv_paySuccessBack)
    ImageView iv_paySuccessBack;
    @Override
    public int bindLayout() {
        return R.layout.pay_success;
    }

    @Override
    public void setListener() {
    }

    @OnClick({R.id.iv_paySuccessBack})
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.iv_paySuccessBack:
                Intent intent=new Intent(PaySuccess.this,BillDetails.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    public void Click(View view) {

    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) iv_paySuccess.getLayoutParams();
        layoutParams.weight=App.screen_width/2;
        layoutParams.height=App.screen_height/2;
        iv_paySuccess.setLayoutParams(layoutParams);
    }
}
