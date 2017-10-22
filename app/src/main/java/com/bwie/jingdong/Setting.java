package com.bwie.jingdong;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cacheUtils.FileSize;

/**
 * Created by 李英杰 on 2017/10/12.
 */

public class Setting extends BaseActivity {

    @BindView(R.id.rel_clearCache)
    RelativeLayout rel_clearCache;
    @BindView(R.id.iv_setBack)
    ImageView iv_setBack;
    @BindView(R.id.tv_CacheSize)
    TextView tv_CacheSize;
    private long size;

    @Override
    public int bindLayout() {
        return R.layout.setting;
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

    @OnClick({R.id.rel_clearCache,R.id.iv_setBack})
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.rel_clearCache:
                AlertDialog.Builder ab=new AlertDialog.Builder(this);
                ab.setMessage("确定清除吗");
                ab.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FileSize.clearCache(getCacheDir());
                        FileSize.clearCache(getFilesDir());
                        tv_CacheSize.setText("0K");
                    }
                });
                ab.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                ab.show();
                break;
            case R.id.iv_setBack:
                finish();
                break;
        }
    }

    @Override
    public void initData() {

        size+= FileSize.getFileSize(getCacheDir());
        size+=FileSize.getFileSize(getFilesDir());
        String fileM = FileSize.getFileM(size);
        tv_CacheSize.setText(fileM);


    }
}
