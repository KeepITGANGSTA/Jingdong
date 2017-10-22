package fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.jingdong.GoodsDetails;
import com.bwie.jingdong.R;
import com.bwie.jingdong.SearchActivity;
import com.google.gson.Gson;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.widget.SpringView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.CarAdapter;
import adapter.CarShopAdapter;
import bean.CarBean;
import bean.CarInfoBean;
import bean.CarInfoChildBean;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import common.Api;
import head.MHead;
import presenter.BillPresent;
import presenter.BillView;
import presenter.GetCarPresent;
import presenter.GetCarView;
import presenter.ShopCarPresent;
import presenter.ShopCarView;
import utils.OkHttpMethed;
import utils.SharedPreferencesUtil;

/**
 * Created by 李英杰 on 2017/9/30.
 */

public class FragmentShopping extends Fragment implements ShopCarView, GetCarView,BillView{

    private DecimalFormat decimalFormat=new DecimalFormat("0.00");
    private double price=0;
    private int GoodsCount=0;
    private View mContentView;
    private SharedPreferences preferences;
    private SharedPreferences.Editor edit;
    private ShopCarPresent shopCarPresent;
    private List<CarBean.DataBean.ListBean> Carlist=new ArrayList<>();
    private List<CarInfoChildBean> CarChildlist=new ArrayList<>();

    @BindView(R.id.Sv_shop)
    SpringView sv_shop;
    @BindView(R.id.iv_shopBack)
    ImageView iv_shopBack;
    @BindView(R.id.btn_Bill)
    Button btn_Bill;
    @BindView(R.id.iv_selectAll)
    CheckBox iv_selectAll;
    @BindView(R.id.tv_selectAll)
    TextView tv_selectAll;
    @BindView(R.id.Recy_Cargoods)
    RecyclerView Recy_CarGoods;
  //  @BindView(R.id.tv_AllPrice)
    public static TextView tv_AllPrice;
    private GetCarPresent getCarPresent;
    private CarShopAdapter carAdapter;
    private BillPresent billPresent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mContentView==null){
            mContentView=inflater.inflate(R.layout.fragment_shopping,container,false);
        }
        ViewGroup viewGroup= (ViewGroup) mContentView.getParent();
        if (viewGroup!=null){
            viewGroup.removeView(mContentView);
        }
        ButterKnife.bind(this,mContentView);
        return mContentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tv_AllPrice=mContentView.findViewById(R.id.tv_AllPrice);
        preferences = SharedPreferencesUtil.getPreferences();
        edit = preferences.edit();
        int count = preferences.getInt("count", -1);
        final int id = preferences.getInt("UserID", 0);
        getCarPresent = new GetCarPresent(getContext(),this);
        shopCarPresent = new ShopCarPresent(getContext(),this);
        billPresent = new BillPresent(this,getContext());
        sv_shop.setHeader(new MHead(null,getContext()));
        sv_shop.setFooter(new DefaultFooter(getContext()));
        sv_shop.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sv_shop.onFinishFreshAndLoad();
                    }
                },2000);
            }

            @Override
            public void onLoadmore() {
            }
        });
        Map<String,Object> parms=new HashMap<>();
        parms.put("uid",id);
        shopCarPresent.getShopCar(OkHttpMethed.POST, Api.Search_ShopCar,parms);
/*        iv_selectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    Map<String,Object> map=new HashMap<>();
                    map.put("uid",id);
                    getCarPresent.getShopCar(OkHttpMethed.POST,Api.Search_ShopCar,map);
                    carAdapter.getPriceCount(new CarShopAdapter.PriceCount() {
                        @Override
                        public void onPriceCount(double price, int count) {
                        }
                    });
                    carAdapter.notifyDataSetChanged();
                }else {
                    tv_AllPrice.setText("合计：￥0.00");
                    btn_Bill.setText("去结算");
                    carAdapter.getPriceCount(null);
                    carAdapter.notifyDataSetChanged();
                    price=0;
                    GoodsCount=0;
                }
            }
        });*/
        iv_selectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = iv_selectAll.isChecked();
                if (checked){
                    Map<String,Object> map=new HashMap<>();
                    map.put("uid",id);
                    getCarPresent.getShopCar(OkHttpMethed.POST,Api.Search_ShopCar,map);
/*                    carAdapter.getPriceCount(new CarShopAdapter.PriceCount() {
                        @Override
                        public void onPriceCount(double price, int count) {
                        }
                    },true);
                    carAdapter.notifyDataSetChanged();*/
                }else {
                    tv_AllPrice.setText("合计：￥0.00");
                    btn_Bill.setText("去结算");
                    carAdapter.getPriceCount(2);
                    carAdapter.notifyDataSetChanged();
                    price=0;
                    GoodsCount=0;
                }
            }
        });
    }

    @OnClick({R.id.iv_shopBack,R.id.btn_Bill,R.id.iv_selectAll})
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.iv_shopBack:
                getActivity().finish();
                break;
            case R.id.btn_Bill:
                int id = preferences.getInt("UserID", 0);
                Map<String,Object> parms=new HashMap<>();
                parms.put("uid",id);
                parms.put("price",price);
                billPresent.CreateBill(OkHttpMethed.POST,Api.CreatBill,parms);
                break;
        }
    }



    @Override
    public void Fail(String msg) {

    }

    @Override
    public void getShopCarSuccess(String result) {
        System.out.println("GetCarInfo~~~~~~~~~~~~~~~~~~~~~~"+result);
        price=0;
        GoodsCount=0;
        if (result!=null){
            Gson gson=new Gson();
            CarBean carBean = gson.fromJson(result, CarBean.class);
            List<CarBean.DataBean> data = carBean.data;
            for (int i = 0; i < data.size(); i++) {
                CarBean.DataBean dataBean = data.get(i);
                List<CarBean.DataBean.ListBean> list = dataBean.list;
                for (int j = 0; j < list.size(); j++) {
                    CarBean.DataBean.ListBean listBean = list.get(j);
                    price+=listBean.bargainPrice*listBean.num;
                    GoodsCount+=listBean.num;
                }
            }
            //DecimalFormat decimalFormat=new DecimalFormat("0.00");
            String format = decimalFormat.format(price);
            tv_AllPrice.setText("合计：￥："+format);
            btn_Bill.setText("去结算"+"("+GoodsCount+")");
            carAdapter.getPriceCount(1);
            carAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void getShopCarFail(String result) {

    }

    @Override
    public void getCarSuccess(String result) {
        if (result!=null){
            Gson gson=new Gson();
            CarBean carBean = gson.fromJson(result, CarBean.class);
            List<CarBean.DataBean> data = carBean.data;
/*            for (int i = 0; i < data.size(); i++) {
                CarInfoBean carInfoBean=new CarInfoBean();
                CarBean.DataBean dataBean = data.get(i);
                carInfoBean.CsellerName= dataBean.sellerName;
                carInfoBean.Csellerid=dataBean.sellerid;
                List<CarBean.DataBean.ListBean> list = dataBean.list;
                for (int i1 = 0; i1 < list.size(); i1++) {
                    CarBean.DataBean.ListBean listBean = list.get(i1);
                    CarInfoChildBean carInfoChildBean=new CarInfoChildBean();
                    carInfoChildBean.CbargainPrice=listBean.bargainPrice;
                    carInfoChildBean.Curl=listBean.detailUrl;
                    carInfoChildBean.Cnum=listBean.num;
                    carInfoChildBean.Cpid=listBean.pid;
                    carInfoChildBean.Cpscid=listBean.pscid;
                    carInfoChildBean.Cselected=listBean.selected;
                    carInfoChildBean.Csubhead=listBean.subhead;
                    carInfoChildBean.Ctitle=listBean.title;
                    carInfoChildBean.CserrierID=listBean.sellerid;
                   // carInfoBean.CImages=listBean.images;
                    String img=listBean.images;
                    String[] split = img.split("\\|");
                    carInfoChildBean.CImages=split[0];
                    CarChildlist.add(carInfoChildBean);
                }

                Carlist.add(carInfoBean);
            }*/
            carAdapter = new CarShopAdapter(getContext(),data);
            Recy_CarGoods.setLayoutManager(new LinearLayoutManager(getContext()));
            int scrollState = Recy_CarGoods.getScrollState();
            if (scrollState==RecyclerView.SCROLL_STATE_IDLE){
                Recy_CarGoods.setAdapter(carAdapter);
            }
            carAdapter.setFragGetGoods(new CarShopAdapter.FragGetGoods() {
                @Override
                public void onFragGetGoods(double Dprice, int Dcount,boolean state) {
                    if (state){
                        price+=Dprice;
                        GoodsCount+=Dcount;
                        String pricePlus = decimalFormat.format(price);
                        tv_AllPrice.setText("合计：￥："+pricePlus);
                        btn_Bill.setText("去结算"+"("+GoodsCount+")");
                    }else {
                        price-=Dprice;
                        GoodsCount-=Dcount;
                        String priceCut = decimalFormat.format(price);
                        tv_AllPrice.setText("合计：￥："+priceCut);
                        btn_Bill.setText("去结算"+"("+GoodsCount+")");
                    }
                }
            });
            carAdapter.setCutPlusOne(new CarShopAdapter.CutPlusOne() {
                @Override
                public void onCutPlusOne(double pr, int num,String flag) {
                    if ("plus".equals(flag)){
                        price+=pr;
                        GoodsCount+=num;
                        tv_AllPrice.setText("合计：￥："+price);
                        btn_Bill.setText("去结算"+"("+GoodsCount+")");
                    }else if ("cut".equals(flag)){
                        price-=pr;
                        GoodsCount-=num;
                        tv_AllPrice.setText("合计：￥："+price);
                        btn_Bill.setText("去结算"+"("+GoodsCount+")");
                    }

                }
            });
/*            carAdapter.setOnCarItemClick(new CarAdapter.CarItemClick() {
                @Override
                public void onCarItemClick(int pid, String url) {
                    Intent intent=new Intent(getContext(), GoodsDetails.class);
                    intent.putExtra("pid",pid);
                    intent.putExtra("url",url);
                    startActivity(intent);
                }
            });*/

        }
    }

    @Override
    public void getCarFail(String result) {
    }

    @Override
    public void CreateFail(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void CreateSuccess(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void RequestFail(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
