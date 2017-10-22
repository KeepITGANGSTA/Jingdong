package com.bwie.jingdong;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.GoodsDetailsAdapter;
import bean.GDBean;
import bean.GDgson;
import bean.ShopCarBean;
import butterknife.BindView;
import butterknife.OnClick;
import common.Api;
import fragment.FragmentDetails;
import fragment.FragmentGoods;
import fragment.FragmentShopping;
import fragment.FragmentSlide;
import fragment.FragmentTalk;
import presenter.AddCarPresent;
import presenter.AddCarView;
import presenter.DetailsPresent;
import presenter.DetailsView;
import presenter.ShopCarPresent;
import presenter.ShopCarView;
import utils.OkHttpMethed;
import utils.SharedPreferencesUtil;

/**
 * Created by 李英杰 on 2017/10/16.
 */

public class GoodsDetails extends BaseActivity implements DetailsView, AddCarView, ShopCarView{

    private List<Fragment> fragmentList=new ArrayList<>();
    private List<String> title=new ArrayList<String>(){{
        add("商品");
        add("详情");
        add("评价");
    }};

    @BindView(R.id.mTabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.Goods_Vp)
    ViewPager Goods_Vp;
    @BindView(R.id.iv_detailsBack)
    ImageView iv_detailsBack;
    @BindView(R.id.btn_joinCar)
    Button btn_joinCar;
    @BindView(R.id.tv_shoppingCar)
    TextView tv_shoppingCar;

    TextView tv_sumCut;
    TextView tv_sumPlus;
    EditText ed_GoodsNum;

    private int sellerid;
    private int count;

    private DetailsPresent detailsPresen;
    private View inflate;
    private Button choosePhoto;
    private Button takePhoto;
    private Button btn_can;
    private Dialog dialog;
    private SharedPreferences preferences;
    private SharedPreferences.Editor edit;
    private int userid;
    private int pid;
    private AddCarPresent addCarPresent;
    private ShopCarPresent shopCarPresent;
    private String url;

    @Override
    public int bindLayout() {
        return R.layout.goods_details;
    }

    @OnClick({R.id.iv_detailsBack,R.id.tv_shoppingCar,R.id.btn_joinCar})
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.iv_detailsBack:
                finish();
                break;
            case R.id.tv_shoppingCar:
                Intent intent=new Intent(this,GoodsActivity.class);
                intent.putExtra("shopping",1);
                startActivity(intent);
                break;
            case R.id.btn_joinCar:
                lastDialog();
                break;

        }
    }


    private void lastDialog() {
        dialog = new Dialog(this,R.style.DialogSelect);
        inflate = LayoutInflater.from(this).inflate(R.layout.btn_join_shopcar, null);
        btn_can = (Button) inflate.findViewById(R.id.btn_diaJoinCar);
        tv_sumCut=inflate.findViewById(R.id.tv_sumCut);
        tv_sumPlus=inflate.findViewById(R.id.tv_sumPlus);
        ed_GoodsNum=inflate.findViewById(R.id.ed_GoodsNum);
        btn_can.setOnClickListener(this);
        tv_sumPlus.setOnClickListener(this);
        tv_sumCut.setOnClickListener(this);

        dialog.setContentView(inflate);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity( Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width=App.screen_width;
        lp.height=App.screen_height-App.screen_height/3;
        dialogWindow.setAttributes(lp);
        dialog.show();
    }

    @Override
    public void setListener() {

    }

    @Override
    public void Click(View view) {
        switch (view.getId()){
            case R.id.tv_sumPlus:
                String s = ed_GoodsNum.getText().toString();
                if (!TextUtils.isEmpty(s)){
                    count = Integer.parseInt(s);
                    if (count!=200){
                        count++;
                        if (count==200){
                            tv_sumPlus.setTextColor(Color.parseColor("#cccccc"));
                            ed_GoodsNum.setText(count+"");
                        }else {
                            //tv_sumPlus.setText(count+"");
                            tv_sumPlus.setTextColor(Color.parseColor("#000000"));
                            ed_GoodsNum.setText(count+"");
                        }
                    }
                }
                break;
            case R.id.tv_sumCut:
                String s1 = ed_GoodsNum.getText().toString();
                if (!TextUtils.isEmpty(s1)){
                    count = Integer.parseInt(s1);
                 if (count!=1){
                     count--;
                     if (count==1){
                         tv_sumCut.setTextColor(Color.parseColor("#cccccc"));
                         ed_GoodsNum.setText(count+"");
                     }else {
                         tv_sumCut.setTextColor(Color.parseColor("#000000"));
                         ed_GoodsNum.setText(count+"");
                     }
                 }
                }
                break;
            case R.id.btn_diaJoinCar:
                edit.putInt("count",count).commit();
                Map<String,Object> parms=new HashMap<>();
                parms.put("uid",userid);
                parms.put("pid",pid);
                parms.put("sellerid",sellerid);
                addCarPresent.addCar(OkHttpMethed.POST,Api.Add_ShopCar,parms);
                break;
        }
    }

    @Override
    public void initView() {
        detailsPresen = new DetailsPresent(this,this);
        addCarPresent = new AddCarPresent(this,this);
        shopCarPresent = new ShopCarPresent(this,this);
    }

    @Override
    public void initData() {
        preferences = SharedPreferencesUtil.getPreferences();
        edit = preferences.edit();
        userid = preferences.getInt("UserID", 0);
        pid = getIntent().getIntExtra("pid",0);
        Map<String,String> parms =new HashMap<>();
        parms.put("pid", pid +"");
        detailsPresen.GoodsDetails( Api.GOODS_DETAILS,parms);

        Map<String,Object> shopid=new HashMap<>();
        shopid.put("uid",userid);
        shopCarPresent.getShopCar(OkHttpMethed.POST,Api.Search_ShopCar,shopid);

        String url=getIntent().getStringExtra("url");
/*        FragmentGoods goods=new FragmentGoods();
        Bundle bundle=new Bundle();
        bundle.putInt("pid",pid);
        goods.setArguments(bundle);*/
        FragmentSlide slide=new FragmentSlide();
        Bundle bundle=new Bundle();
        bundle.putInt("pid", pid);
        bundle.putString("url",url);
        slide.setArguments(bundle);
        FragmentDetails fragmentDetails=new FragmentDetails();
        Bundle bundle1=new Bundle();
        bundle1.putString("url", url);;
        fragmentDetails.setArguments(bundle1);
        FragmentTalk fragmentTalk=new FragmentTalk();
        fragmentList.add(slide);
        fragmentList.add(fragmentDetails);
        fragmentList.add(fragmentTalk);
        GoodsDetailsAdapter adapter=new GoodsDetailsAdapter(getSupportFragmentManager(),this,fragmentList,title);
        Goods_Vp.setAdapter(adapter);
        Goods_Vp.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Goods_Vp.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mTabLayout.setupWithViewPager(Goods_Vp);
    }


    @Override
    public void Fail(String msg) {

    }

    @Override
    public void getCarSuccess(String result) {
        Gson gson=new Gson();
        ShopCarBean shopCarBean = gson.fromJson(result, ShopCarBean.class);
        List<ShopCarBean.DataBean> data = shopCarBean.data;
        if (data.size()==0){
            tv_shoppingCar.setText("购物车");
        }else {
            tv_shoppingCar.setText("购物车"+"("+data.size()+")");
        }

    }

    @Override
    public void getCarFail(String result) {
        Toast.makeText(this, "查询购物车失败", Toast.LENGTH_SHORT).show();
        System.out.println("查询购物车失败："+result);
    }

    @Override
    public void AddSuccess(String result) {
        System.out.println(result);
        Toast toast=Toast.makeText(this,"加购成功",Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
        dialog.dismiss();


        Map<String,Object> shopid=new HashMap<>();
        shopid.put("uid",userid);
        shopCarPresent.getShopCar(OkHttpMethed.POST,Api.Search_ShopCar,shopid);
    }

    @Override
    public void AddFail(String reuslt) {
        System.out.println("加购失败————————————————"+reuslt);
        Toast toast=new Toast(this);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.setText("加购失败");
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void SearchSuccess(String result) {
        Gson gson=new Gson();
        GDgson gDgson = gson.fromJson(result, GDgson.class);
        GDgson.SellerBean seller = gDgson.seller;
        GDgson.DataBean data = gDgson.data;
        sellerid=seller.sellerid;
    }

    @Override
    public void SearchFail(String resulet) {

    }
}
