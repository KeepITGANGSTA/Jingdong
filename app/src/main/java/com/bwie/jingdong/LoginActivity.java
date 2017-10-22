package com.bwie.jingdong;

import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.usher.greendao_demo.greendao.gen.UserDao;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import bean.User;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import common.Api;
import manager.GreenDaoManager;
import okhttp3.Call;
import presenter.LoginPresent;
import presenter.PresentLoginView;
import utils.SharedPreferencesUtil;

/**
 * Created by 李英杰 on 2017/10/10.
 */

public class LoginActivity extends BaseActivity implements PresentLoginView{

    @BindView(R.id.iv_login_Back)
    ImageView iv_login_Back;
    @BindView(R.id.ed_phone)
    EditText ed_phone;
    @BindView(R.id.ed_psd)
    EditText ed_psd;
    @BindView(R.id.btn_login)
    Button btn_login;
    @BindView(R.id.tv_Res)
    TextView tv_Res;
    @BindView(R.id.tv_ForgetPsd)
    TextView tv_ForgetPsd;
    private SharedPreferences preferences;
    private SharedPreferences.Editor edit;
    private LoginPresent loginPresent;
    private SharedPreferences preferences1;
    private SharedPreferences.Editor edit1;
    private UserDao userDao;

    @Override
    public int bindLayout() {
        return R.layout.login_view;
    }

    @Override
    public void setListener() {

    }

    @Override
    public void Click(View view) {}

    @OnClick({R.id.iv_login_Back,R.id.btn_login,R.id.tv_ForgetPsd,R.id.tv_Res})
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.iv_login_Back:
                finish();
                break;
            case R.id.btn_login:
                String phone=ed_phone.getText().toString();
                String psd=ed_psd.getText().toString();
                if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(psd)){
                    showToast("账号密码不能为空");
                    return;
                }
                loginPresent.Login(Api.LOGIN,phone,psd);
                break;
            case R.id.tv_ForgetPsd:

                break;
            case R.id.tv_Res:
                Intent intent=new Intent(this,ResActivity.class);
                startActivity(intent);
                break;
        }
    }


    @Override
    public void initView() {
        ButterKnife.bind(this);
        preferences1 = SharedPreferencesUtil.getPreferences();
        edit1 = preferences1.edit();
/*        ImageView imageView= (ImageView) findViewById(R.id.iv_login_Back);
        imageView.setOnClickListener(this);*/
    }

    @Override
    public void initData() {
        preferences = SharedPreferencesUtil.getPreferences();
        edit = preferences.edit();
        loginPresent = new LoginPresent(this,this);
        userDao = GreenDaoManager.getmDaoSession().getUserDao();
    }

    @Override
    public void RequestFail(Call call, IOException e) {
        showToast("请求失败");
    }

    @Override
    public void LoginSuccess(String result) {
        showToast("登录成功");
        try {
            JSONObject object=new JSONObject(result);
            JSONObject o=object.getJSONObject("data");
            userDao.insert(new User(1,o.getString("icon"),o.getString("nickname"),o.getString("username"),o.getInt("uid")));
            edit.putInt("UserID",o.getInt("uid")).commit();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        finish();
    }

    @Override
    public void LoginFail(String result) {
        showToast(result);
    }

    @Override
    public void ResSuccess(String result) {

    }

    @Override
    public void ResFail(String result) {

    }

    @Override
    public void RequestRes(Call call, IOException e) {

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