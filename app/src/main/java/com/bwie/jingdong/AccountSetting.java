package com.bwie.jingdong;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.usher.greendao_demo.greendao.gen.UserDao;

import org.greenrobot.greendao.internal.DaoConfig;

import bean.User;
import butterknife.BindView;
import butterknife.OnClick;
import common.Api;
import manager.GreenDaoManager;
import utils.SharedPreferencesUtil;

import static com.bwie.jingdong.R.drawable.type;

/**
 * Created by 李英杰 on 2017/10/10.
 */

public class AccountSetting extends BaseActivity {

    @BindView(R.id.lin_settingLogin)
    LinearLayout lin_settingLogin;
    @BindView(R.id.iv_settingBack)
    ImageView iv_settingBack;
    @BindView(R.id.iv_settingIcon)
    ImageView iv_settingIcon;
    @BindView(R.id.tv_settingNickName)
    TextView tv_settingNickName;
    @BindView(R.id.rel_settingAddress)
    RelativeLayout rel_settingAddress;
    @BindView(R.id.rel_settingSet)
    RelativeLayout rel_settingSet;
    @BindView(R.id.btn_exit)
    Button btn_exit;

    private SharedPreferences preferences;
    private SharedPreferences.Editor edit;
    private UserDao userDao;

    @OnClick({R.id.iv_settingBack,R.id.lin_settingLogin,R.id.rel_settingAddress,R.id.rel_settingSet,R.id.btn_exit})
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.iv_settingBack:
                finish();
                break;
            case R.id.lin_settingLogin:
                int userId = preferences.getInt("UserID", 0);
                if (userId==0){
                    Intent intent=new Intent(this,LoginActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent=new Intent(this,PersonalInfo.class);
                    startActivity(intent);
                }

                break;
            case R.id.rel_settingAddress:
                int id = preferences.getInt("UserID", 0);
                if (id==0){
                    Intent intent=new Intent(this,LoginActivity.class);
                    startActivity(intent);
                }else {
                    Intent address=new Intent(this,Address.class);
                    startActivity(address);
                }
                break;
            case R.id.rel_settingSet:
                Intent intent=new Intent(this,Setting.class);
                startActivity(intent);


                break;
            case R.id.btn_exit:
                AlertDialog.Builder ab=new AlertDialog.Builder(this);
                ab.setMessage("确定退出吗？");
                ab.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        userDao.deleteAll();
                        edit.putInt("UserID",0).commit();
                        finish();
                    }
                });
                ab.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                ab.show();
                break;
        }
    }

    @Override
    public int bindLayout() {
        return R.layout.setting_view;
    }

    @Override
    public void setListener() {
    }

    @Override
    public void Click(View view) {
    }

    @Override
    public void initView() {
        preferences = SharedPreferencesUtil.getPreferences();
        edit = preferences.edit();
        int userID = preferences.getInt("UserID", 0);
    }

    @Override
    public void initData() {
        int userID = preferences.getInt("UserID", 0);
        userDao = GreenDaoManager.getmDaoSession().getUserDao();
        if (userID!=0){
            User load = userDao.load((long) 1);
            String iconUrl = load.getIconUrl();
            String nickName=load.getNickname();
            String userName=load.getUsername();
            if (!iconUrl.equals("null")){
                Glide.with(this).load(iconUrl).into(iv_settingIcon);
            }
            if (!nickName.equals("null")){
                tv_settingNickName.setText(nickName);
            }else {
                tv_settingNickName.setText(userName);
            }
            btn_exit.setVisibility(View.VISIBLE);
        }else {
            btn_exit.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        int userID = preferences.getInt("UserID", 0);
        userDao = GreenDaoManager.getmDaoSession().getUserDao();
        if (userID!=0){
            User load = userDao.load((long) 1);
            String iconUrl = load.getIconUrl();
            String nickName=load.getNickname();
            String userName=load.getUsername();
            if (!iconUrl.equals("null")){
                Glide.with(this).load(iconUrl).into(iv_settingIcon);
            }
            if (!nickName.equals("null")){
                tv_settingNickName.setText(nickName);
            }else {
                tv_settingNickName.setText(userName);
            }
            btn_exit.setVisibility(View.VISIBLE);
        }else {
            btn_exit.setVisibility(View.GONE);
        }
    }
}
