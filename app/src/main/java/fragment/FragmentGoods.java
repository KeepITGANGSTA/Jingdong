package fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bwie.jingdong.AlertNickName;
import com.bwie.jingdong.App;
import com.bwie.jingdong.R;
import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.XBannerGlide;
import bean.GDBean;
import bean.GDgson;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import common.Api;
import presenter.DetailsPresent;
import presenter.DetailsView;
import utils.OkHttpMethed;

/**
 * Created by 李英杰 on 2017/10/16.
 */

public class FragmentGoods  extends Fragment implements DetailsView{

    private View mRoot;
    private DetailsPresent detailsPresent;
    private List<String> images=new ArrayList<>();
    @BindView(R.id.iv_detailsBack)
    ImageView iv_detailsBack;
    @BindView(R.id.tv_shoppingCar)
    TextView tv_shoppingCar;
    @BindView(R.id.btn_joinCar)
    Button btn_joinCar;
    //@BindView(R.id.details_banner)
    Banner details_banner;
/*    @BindView(R.id.tv_detailsTitle)
    TextView tv_detailsTitle;*/
    //@BindView(R.id.tv_detailsSubhead)
    TextView tv_detailsSubhead;
   // @BindView(R.id.tv_detailsBargain)
    TextView tv_detailsBargain;
   // @BindView(R.id.iv_shopIcon)
    ImageView iv_shopIcon;
    //@BindView(R.id.tv_shopDes)
    TextView tv_shopDes;
    //@BindView(R.id.tv_shopName)
    TextView tv_shopName;
   // @BindView(R.id.tv_shopScore)
    TextView tv_shopScore;
    //@BindView(R.id.tv_shopNum)
    TextView tv_shopNum;
    //@BindView(R.id.tv_shopId)
    TextView tv_shopId;
    @BindView(R.id.rel_chatShop)
    RelativeLayout rel_chatShop;
    @BindView(R.id.rel_enterShop)
    RelativeLayout rel_enterShop;
    private TextView tv_detailsTitle;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRoot==null){
            mRoot=inflater.inflate(R.layout.goods_f_view,null);
        }
        ViewGroup parent = (ViewGroup) mRoot.getParent();
        if (parent!=null){
            parent.removeView(mRoot);
        }
        ButterKnife.bind(this.mRoot);
        return mRoot;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        detailsPresent = new DetailsPresent(this,getContext());
        int pid = getArguments().getInt("pid");
        initView();

        Map<String,String> parms =new HashMap<>();
        parms.put("pid",pid+"");
        detailsPresent.GoodsDetails( Api.GOODS_DETAILS,parms);

    }


    private void initView() {
        tv_detailsTitle = mRoot.findViewById(R.id.tv_detailsTitle);
        details_banner=mRoot.findViewById(R.id.details_banner);
        ViewGroup.LayoutParams layoutParams = details_banner.getLayoutParams();
        layoutParams.height= App.screen_height-App.screen_height/3;
        details_banner.setLayoutParams(layoutParams);
        tv_detailsSubhead=mRoot.findViewById(R.id.tv_detailsSubhead);
        tv_detailsBargain=mRoot.findViewById(R.id.tv_detailsBargain);
        iv_shopIcon=mRoot.findViewById(R.id.iv_shopIcon);
        tv_shopDes=mRoot.findViewById(R.id.tv_shopDes);
        tv_shopName=mRoot.findViewById(R.id.tv_shopName);
        tv_shopScore=mRoot.findViewById(R.id.tv_shopScore);
        tv_shopNum=mRoot.findViewById(R.id.tv_shopNum);
        tv_shopId=mRoot.findViewById(R.id.tv_shopId);
    }

    @OnClick({R.id.iv_detailsBack,R.id.tv_shoppingCar,R.id.btn_joinCar})
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.iv_detailsBack:
                getActivity().finish();
                break;
            case R.id.tv_shoppingCar:
                Toast.makeText(getContext(), "购物车", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_joinCar:
                Toast.makeText(getContext(), "加入购物车", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void Fail(String msg) {

    }

    @Override
    public void SearchSuccess(String result) {
        System.out.println("详情-----"+result);
        Gson gson=new Gson();
        GDgson gDgson = gson.fromJson(result, GDgson.class);
        final GDgson.SellerBean seller = gDgson.seller;
        final GDBean gdBean=new GDBean();
        gdBean.description=seller.description;
        gdBean.sicon=seller.icon;
        gdBean.sname=seller.name;
        gdBean.productNums=seller.productNums;
        gdBean.score=seller.score;
        gdBean.sellerid=seller.sellerid;
        final GDgson.DataBean data = gDgson.data;
        gdBean.bargainPrice=data.bargainPrice;
        gdBean.detailUrl=data.detailUrl;
        gdBean.dimages=data.images;
        gdBean.dpid=data.pid;
        gdBean.dprice=data.price;
        gdBean.dpscid=data.pscid;
        gdBean.salenum=data.salenum;
        gdBean.dsubhead=data.subhead;
        gdBean.dtitle=data.title;
        String imgs=data.images;
        String[] split = imgs.split("\\|");
        images.clear();
        for (int i = 0; i < split.length; i++) {
            images.add(split[i]);
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                System.out.println("tv_detailsTitle"+tv_detailsTitle);
                details_banner.setImages(images);
                details_banner.setImageLoader(new XBannerGlide());
                details_banner.setBannerStyle(BannerConfig.NUM_INDICATOR);
                details_banner.isAutoPlay(false);
                details_banner.start();
                tv_detailsTitle.setText(data.title);
                tv_detailsSubhead.setText(data.subhead);
                tv_detailsBargain.setText("￥"+data.bargainPrice);
                //Glide.with(getContext()).load(seller.icon).into(iv_shopIcon);
                tv_shopDes.setText(seller.description);
                tv_shopName.setText(seller.name);
                tv_shopScore.setText(seller.score+"");
                tv_shopNum.setText(data.salenum+"");
                tv_shopId.setText(seller.sellerid+"");
            }
        });

    }

    @Override
    public void SearchFail(String resulet) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        detailsPresent.onDestory();
    }
}
