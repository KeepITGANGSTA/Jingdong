package com.bwie.jingdong;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import adapter.RecyclerViewAdapter;
import adapter.RecyclerViewDivide;
import adapter.XBannerGlide;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fragment.FragmentFind;
import fragment.FragmentMine;
import fragment.FragmentOne;
import fragment.FragmentShopping;
import fragment.FragmentType;
import presenter.Present;
import presenter.PresentView;

public class GoodsActivity extends AppCompatActivity implements PresentView,TabHost.OnTabChangeListener{

    private FloatingActionButton floatingActionButton;
    private AppBarLayout mAb;
    private Present present;

    @BindView(R.id.mFtab)
    FragmentTabHost mTabHost;
    private ArrayList<Fragment> fragments=new ArrayList<Fragment>(){{
        add(new FragmentOne());
        add(new FragmentType());
        add(new FragmentFind());
        add(new FragmentShopping());
        add(new FragmentMine());
    }};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_goods);
        ButterKnife.bind(this);


        mTabHost.setup(this, getSupportFragmentManager(),R.id.mContent );
        mTabHost.getTabWidget().setDividerDrawable(null); // 去掉分割线
        mTabHost.setOnTabChangedListener(this);
        for (int i = 0; i < 5; i++) {
            View view = getLayoutInflater().inflate(R.layout.tab_item, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.tab_iv_image);

            switch (i){
                case 0:
                    imageView.setImageResource(R.drawable.first);
                    break;
                case 1:
                    imageView.setImageResource(R.drawable.type);
                    break;
                case 2:
                    imageView.setImageResource(R.drawable.find);
                    break;
                case 3:
                    imageView.setImageResource(R.drawable.shopping);
                    break;
                case 4:
                    imageView.setImageResource(R.drawable.mine);
                    break;
            }

            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(i+"").setIndicator(view);
            mTabHost.addTab(tabSpec, fragments.get(i).getClass(), null);
            mTabHost.setTag(i);
            int shopping = getIntent().getIntExtra("shopping", 0);
            System.out.println("shopping--"+shopping);
            if (shopping==1){
                System.out.println("跳转~~~~~~~~~~~~~~~~~~");
                mTabHost.setCurrentTab(3);
            }
        }




        //present = new Present(this);


    }






    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result=IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (result!=null){
            if (result.getContents()!=null){
                Toast.makeText(this, "扫描成功==="+result.getContents(), Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "扫描失败", Toast.LENGTH_SHORT).show();
            }
        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }






    @Override
    public void success(String result) {
    }

    @Override
    public void requestFail() {

    }

    @Override
    public void fail() {

    }

    @Override
    public void GridSuccess(String result) {

    }

    @Override
    public void Gridfail() {

    }

    @Override
    public void onTabChanged(String s) {

    }

}
