package com.bwie.jingdong;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.usher.greendao_demo.greendao.gen.UserDao;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import bean.User;
import butterknife.BindView;
import butterknife.OnClick;
import common.Api;
import manager.GreenDaoManager;
import presenter.AlertPresent;
import presenter.PresentAlertView;
import utils.OkHttpMethed;

/**
 * Created by 李英杰 on 2017/10/11.
 */

public class AlertNickName extends BaseActivity implements PresentAlertView{

    @BindView(R.id.iv_alertBack)
    ImageView iv_alertBack;
    @BindView(R.id.tv_alertNameOk)
    TextView tv_alertNameOk;
    @BindView(R.id.ed_nickName)
    EditText ed_nickName;
    private AlertPresent alertPresent;
    private UserDao userDao;
    private User load;

    @Override
    public int bindLayout() {
        return R.layout.alert_nickname;
    }

    @Override
    public void setListener() {}

    @Override
    public void Click(View view) {

    }

    @OnClick({R.id.iv_alertBack,R.id.tv_alertNameOk})
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.iv_alertBack:
                finish();
                break;
            case R.id.tv_alertNameOk:
                String s = ed_nickName.getText().toString();
                int length = s.length();
                if (length<=4){
                    AlertDialog.Builder ab=new AlertDialog.Builder(AlertNickName.this);
                    ab.setMessage("请输入4-20个字符作为昵称");
                    ab.setPositiveButton("我知道了", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    ab.show();
                }else {
                    Map<String,Object> parms=new HashMap<String,Object>();
                    parms.put("uid",load.getUid());
                    parms.put("nickname",ed_nickName.getText().toString());
                    alertPresent.AlertNickName(OkHttpMethed.POST, Api.ALTER_NICKNAME,parms);
                    load.setNickname(ed_nickName.getText().toString());
                    userDao.update(load);
                    showToast("修改成功");
                }
                break;
        }
    }

    @Override
    public void initView() {
        alertPresent = new AlertPresent(this,this);
    }

    @Override
    public void initData() {
        userDao = GreenDaoManager.getmDaoSession().getUserDao();
        load = userDao.load((long) 1);
    }

    @Override
    public void AlertSuccess(String result) {
        showToast(result);
    }

    @Override
    public void AlertFailure(String result) {
        showToast(result);
    }

    @Override
    public void Fail(String e, String msg) {
        showToast(e+"-----"+msg);
        System.out.println("------"+e+"++++++++"+msg);
    }

    @Override
    public void GoodsSuccess(String result) {

    }

    @Override
    public void GoodsFailure(String result) {

    }
}
