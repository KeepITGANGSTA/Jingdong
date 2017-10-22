package com.bwie.jingdong;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;

import butterknife.ButterKnife;
import common.Api;
import okhttp3.Call;
import presenter.LoginPresent;
import presenter.PresentLoginView;

/**
 * Created by 李英杰 on 2017/10/10.
 */

public class ResActivity extends BaseActivity implements PresentLoginView{

    private EditText ed_ed_ResPhone;
    private EditText ed_ResPsd;
    private Button btn_Res;
    private LoginPresent loginPresent;

    @Override
    public int bindLayout() {
        return R.layout.res_view;
    }

    @Override
    public void setListener() {

    }

    @Override
    public void Click(View view) {
        switch (view.getId()){
            case R.id.btn_Res:
                String phone=ed_ed_ResPhone.getText().toString();
                String psd=ed_ResPsd.getText().toString();
                if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(psd)){
                    showToast("账号密码不能为空");
                    return;
                }
                loginPresent.Res(Api.RES,phone,psd);
                break;
        }
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        ed_ed_ResPhone= (EditText) findViewById(R.id.ed_ResPhone);
        ed_ResPsd= (EditText) findViewById(R.id.ed_ResPsd);
        btn_Res= (Button) findViewById(R.id.btn_Res);

        ed_ResPsd.setOnClickListener(this);
        ed_ed_ResPhone.setOnClickListener(this);
        btn_Res.setOnClickListener(this);

    }

    @Override
    public void initData() {
        loginPresent=new LoginPresent(this,this);
    }

    @Override
    public void RequestFail(Call call, IOException e) {

    }

    @Override
    public void LoginSuccess(String result) {

    }

    @Override
    public void LoginFail(String result) {

    }

    @Override
    public void ResSuccess(String result) {
        showToast("注册成功");
        finish();
    }

    @Override
    public void ResFail(String result) {
        showToast("注册失败");
    }

    @Override
    public void RequestRes(Call call, IOException e) {
        showToast("请求失败");
    }

    @Override
    public void getInfoSuccess(String result) {

    }

    @Override
    public void getInfoFail(String result) {

    }

    @Override
    public void RequestGetInfoFail(Call call, IOException e) {

    }
}
