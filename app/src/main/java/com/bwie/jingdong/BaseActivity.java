package com.bwie.jingdong;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;

import butterknife.ButterKnife;
import butterknife.OnClick;

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener{

    private boolean isStatus = false;//沉浸式状态栏（是否支持透明）
    private boolean isShowActionBar = false;//actionbar是否显示
    private boolean isFullScreen = false;//是否支持全屏



    public abstract int bindLayout();//绑定布局，绑定子类的根布局
    public abstract void setListener();//设置监听事件
    public abstract void Click(View view);//点击事件的回调方法


    public abstract void initView();//初始化views
    public abstract void initData();//初始化数据

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(bindLayout());
        ButterKnife.bind(this);
        initView();
        setListener();
        initData();

    }




    /**
     * 设置透明状态：沉浸式
     * @param status
     */
    public void setStatus(boolean status) {
        isStatus = status;
        if (isStatus){

            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){

                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            }
        }
    }

    /**
     * 设置actionbar是否显示
     * @param showActionBar
     */
    public void setShowActionBar(boolean showActionBar) {
        isShowActionBar = showActionBar;
        if (isShowActionBar){
            getSupportActionBar().show();
        }else{
            getSupportActionBar().hide();
        }
    }

    /**
     * 设置全屏
     * @param fullScreen
     */
    public void setFullScreen(boolean fullScreen) {
        isFullScreen = fullScreen;
        if (isFullScreen){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    /**
     * 点击事件实现
     * @param view
     */
    @Override
    public void onClick(View view) {

        Click(view);

    }

    /**
     * 显示toast
     * @param msg
     */
    public void showToast(String msg){

        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 无参跳转
     * @param clz
     */
    public void startActivity(Class<?> clz){
        Intent intent = new Intent(this,clz);
        startActivity(intent);
    }

    /**
     * 有参跳转
     * @param clz
     * @param bundle
     */
    public void startActivity(Class<?> clz,Bundle bundle){
        Intent intent = new Intent(this,clz);
        intent.putExtras(bundle);
        startActivity(intent);
    }



}
