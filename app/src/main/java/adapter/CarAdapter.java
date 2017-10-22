package adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bwie.jingdong.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.CarBean;
import bean.CarInfoBean;
import common.Api;
import fragment.FragmentShopping;
import presenter.UpdateCarPresent;
import presenter.UpdateCarView;
import utils.SharedPreferencesUtil;

/**
 * Created by 李英杰 on 2017/10/18.
 */

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> implements UpdateCarView{

    private double mPrice=0;
    private Context context;
    private List<CarBean.DataBean.ListBean> list;
    private final UpdateCarPresent present;
    private final SharedPreferences preferences;
    private int count;
    private int sum=0;
    private int Tsum=0;
    private final CarShopAdapter carShopAdapter;
    private Map<Integer, Boolean> map = new HashMap<>();
    private List<Map<Integer,Boolean>> listMap=new ArrayList<>();


    public CarAdapter(Context context, List<CarBean.DataBean.ListBean> list) {
        this.context = context;
        this.list = list;
        present = new UpdateCarPresent(context,this);
        preferences = SharedPreferencesUtil.getPreferences();
        carShopAdapter = new CarShopAdapter();
        initMap();
    }

    private void initMap() {
        for (int i = 0; i < list.size(); i++) {
            int selected = list.get(i).selected;
            Map<Integer, Boolean> map = new HashMap<>();
            if (selected==1){
                map.put(i,true);
            }else if (selected==0){
                map.put(i,false);
            }
            listMap.add(map);
/*            int selected = list.get(i).selected;
            if (selected==1){
                map.put(i,true);
            }else if (selected==0){
                map.put(i,false);
            }*/
        }
    }

    @Override
    public CarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.car_child_view,null);
        CarViewHolder holder=new CarViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final CarViewHolder holder, final int position) {
       final CarBean.DataBean.ListBean carInfoBean=list.get(position);
        String msg=carInfoBean.images;
        String[] split = msg.split("\\|");
        int selected = carInfoBean.selected;
/*        if (selected==0){
            holder.iv_CarGoodsSele.setChecked(false);
        }else {
            holder.iv_CarGoodsSele.setChecked(true);
        }*/

        Glide.with(context).load(split[0]).into(holder.iv_Car);
        holder.tv_CarTitle.setText(carInfoBean.title);
        holder.tv_CarSubHead.setText(carInfoBean.subhead);
        holder.tv_CarBargain.setText("￥"+(carInfoBean.bargainPrice)*carInfoBean.num);
        holder.ed_CarGoodsNum.setText(carInfoBean.num+"");

/*        holder.iv_CarGoodsSele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int size = list.size();
                boolean checked = holder.iv_CarGoodsSele.isChecked();
                if (checked){
                    sum++;
                    String s = holder.ed_CarGoodsNum.getText().toString();
                    int i = Integer.parseInt(s);
                    int id = preferences.getInt("UserID", 0);
                    Map<String,String> parms=new HashMap<String, String>();
                    parms.put("uid",id+"");
                    parms.put("sellerid",carInfoBean.sellerid+"");
                    parms.put("pid",carInfoBean.pid+"");
                    parms.put("num",i+"");
                    parms.put("selected",1+"");
                    present.UpdateCar(Api.Update_ShopCar,parms);
                }else {
                    if (sum>0){
                        sum--;
                    }
                    String s = holder.ed_CarGoodsNum.getText().toString();
                    int i = Integer.parseInt(s);
                    int id = preferences.getInt("UserID", 0);
                    Map<String,String> parms=new HashMap<String, String>();
                    parms.put("uid",id+"");
                    parms.put("sellerid",carInfoBean.sellerid+"");
                    parms.put("pid",carInfoBean.pid+"");
                    parms.put("num",i+"");
                    parms.put("selected",0+"");
                    present.UpdateCar(Api.Update_ShopCar,parms);
                }
                setHeadTrue.onSetHeadTrue(sum);
            }
        });*/
        holder.iv_CarGoodsSele.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //map.put(position,b);
                listMap.get(position).put(position,b);
                int size = list.size();
                String s = holder.ed_CarGoodsNum.getText().toString();
                int i = Integer.parseInt(s);
                if (b){
                    carInfoBean.selected=1;
                    sum++;
                    String ss = holder.ed_CarGoodsNum.getText().toString();
                    int ii = Integer.parseInt(ss);
                    int id = preferences.getInt("UserID", 0);
                    Map<String,String> parms=new HashMap<String, String>();
                    parms.put("uid",id+"");
                    parms.put("sellerid",carInfoBean.sellerid+"");
                    parms.put("pid",carInfoBean.pid+"");
                    parms.put("num",ii+"");
                    parms.put("selected",1+"");
                    present.UpdateCar(Api.Update_ShopCar,parms);
                    System.out.println("True刷新");
                }else{
                    carInfoBean.selected=0;
                    if (sum>0){
                        sum--;
                    }
                    String ss = holder.ed_CarGoodsNum.getText().toString();
                    int ii = Integer.parseInt(ss);
                    int id = preferences.getInt("UserID", 0);
                    Map<String,String> parms=new HashMap<String, String>();
                    parms.put("uid",id+"");
                    parms.put("sellerid",carInfoBean.sellerid+"");
                    parms.put("pid",carInfoBean.pid+"");
                    parms.put("num",ii+"");
                    parms.put("selected",0+"");
                    present.UpdateCar(Api.Update_ShopCar,parms);
                    System.out.println("False刷新");
                }
                getGoodsInfo.onGetGoodsInfo(carInfoBean.bargainPrice*i,i,b);
                setHeadTrue.onSetHeadTrue(sum);
            }
        });
        Boolean aBoolean = listMap.get(position).get(position);
        System.out.println("Map~~~~~~~~~~~~~~~~~~~~~~"+position+"-"+aBoolean);
        holder.iv_CarGoodsSele.setChecked(aBoolean);
/*        holder.iv_CarGoodsSele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = holder.iv_CarGoodsSele.isChecked();
                int size = list.size();
                String s = holder.ed_CarGoodsNum.getText().toString();
                int i = Integer.parseInt(s);
                if (checked){
                    listMap.get(position).put(position,true);
                    carInfoBean.selected=1;
                    sum++;
                    String ss = holder.ed_CarGoodsNum.getText().toString();
                    int ii = Integer.parseInt(ss);
                    int id = preferences.getInt("UserID", 0);
                    carInfoBean.num=ii;
                    Map<String,String> parms=new HashMap<String, String>();
                    parms.put("uid",id+"");
                    parms.put("sellerid",carInfoBean.sellerid+"");
                    parms.put("pid",carInfoBean.pid+"");
                    parms.put("num",ii+"");
                    parms.put("selected",1+"");
                    present.UpdateCar(Api.Update_ShopCar,parms);
                }else {
                    listMap.get(position).put(position,false);
                    carInfoBean.selected=0;
                    if (sum>0){
                        sum--;
                    }
                    String ss = holder.ed_CarGoodsNum.getText().toString();
                    int ii = Integer.parseInt(ss);
                    int id = preferences.getInt("UserID", 0);
                    carInfoBean.num=ii;
                    Map<String,String> parms=new HashMap<String, String>();
                    parms.put("uid",id+"");
                    parms.put("sellerid",carInfoBean.sellerid+"");
                    parms.put("pid",carInfoBean.pid+"");
                    parms.put("num",ii+"");
                    parms.put("selected",0+"");
                    present.UpdateCar(Api.Update_ShopCar,parms);
                }
                setHeadTrue.onSetHeadTrue(sum);
            }
        });*/
        if (listMap.get(position)==null){
           listMap.get(position).put(position,false);
        }
       // Boolean aBoolean = map.get(position);

/*        if (holder.iv_CarGoodsSele.isChecked()){
            sum++;
            setHeadTrue.onSetHeadTrue(sum);
        }else {
            sum--;
            setHeadTrue.onSetHeadTrue(Tsum);
        }*/
        holder.tv_CarSumCut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.iv_CarGoodsSele.setChecked(true);
                String s = holder.ed_CarGoodsNum.getText().toString();
                count = Integer.parseInt(s);
                if (count!=1){
                    count--;
                    if (count==1){
                        holder.tv_CarSumCut.setTextColor(Color.parseColor("#cccccc"));
                        holder.ed_CarGoodsNum.setText(count+"");
                        holder.tv_CarBargain.setText("￥"+count*carInfoBean.bargainPrice);
                        //getGoodsInfo.onGetGoodsInfo(carInfoBean.bargainPrice*count,count,false);

                    }else {
                        if (count<200){
                            holder.tv_CarSumPlus.setTextColor(Color.parseColor("#000000"));
                        }
                        holder.tv_CarSumCut.setTextColor(Color.parseColor("#000000"));
                        holder.ed_CarGoodsNum.setText(count+"");
                        holder.tv_CarBargain.setText("￥"+count*carInfoBean.bargainPrice);
                       // getGoodsInfo.onGetGoodsInfo(carInfoBean.bargainPrice*count,count,false);
                    }
                    cutPlusI.onCutPlus(carInfoBean.bargainPrice,1,"cut");
                }
/*                if (count>1){
                    //getGoodsInfo.onGetGoodsInfo(carInfoBean.bargainPrice*count,count,false);
                    cutPlusI.onCutPlus(carInfoBean.bargainPrice,1,"cut");
                }*/
                carInfoBean.num=count;
                int id = preferences.getInt("UserID", 0);
                Map<String,String> parms=new HashMap<String, String>();
                parms.put("uid",id+"");
                parms.put("sellerid",carInfoBean.sellerid+"");
                parms.put("pid",carInfoBean.pid+"");
                parms.put("num",count+"");
                parms.put("selected",1+"");
                present.UpdateCar(Api.Update_ShopCar,parms);
            }
        });

        holder.tv_CarSumPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.iv_CarGoodsSele.setChecked(true);
                String s = holder.ed_CarGoodsNum.getText().toString();
                count = Integer.parseInt(s);
                if (count!=200){
                    count++;
                    if (count==200){
                        holder.tv_CarSumPlus.setTextColor(Color.parseColor("#cccccc"));
                        holder.ed_CarGoodsNum.setText(count+"");
                        holder.tv_CarBargain.setText("￥"+count*carInfoBean.bargainPrice);
                        //getGoodsInfo.onGetGoodsInfo(carInfoBean.bargainPrice*count,count,true);
                    }else {
                        if (count>1){
                            holder.tv_CarSumCut.setTextColor(Color.parseColor("#000000"));
                        }
                        holder.tv_CarSumPlus.setTextColor(Color.parseColor("#000000"));
                        holder.ed_CarGoodsNum.setText(count+"");
                        holder.tv_CarBargain.setText("￥"+count*carInfoBean.bargainPrice);
                        //getGoodsInfo.onGetGoodsInfo(carInfoBean.bargainPrice*count,count,true);
                    }
                }
                if (count!=200){
                    //getGoodsInfo.onGetGoodsInfo(carInfoBean.bargainPrice*count,count,true);
                    cutPlusI.onCutPlus(carInfoBean.bargainPrice,1,"plus");
                }

                carInfoBean.num=count;
                int id = preferences.getInt("UserID", 0);
                Map<String,String> parms=new HashMap<String, String>();
                parms.put("uid",id+"");
                parms.put("sellerid",carInfoBean.sellerid+"");
                parms.put("pid",carInfoBean.pid+"");
                parms.put("num",count+"");
                parms.put("selected",1+"");
                present.UpdateCar(Api.Update_ShopCar,parms);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                carItemClick.onCarItemClick(carInfoBean.pid,carInfoBean.detailUrl);
            }
        });



    }

    public SetHeadTrue setHeadTrue;

    public void setOnSetHeadTrue(SetHeadTrue setHeadTrue){
        this.setHeadTrue=setHeadTrue;
    }

    public interface SetHeadTrue{
        void onSetHeadTrue(int current);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setAllChecked(RecyclerView recyclerView){
        for (int i = 0; i < list.size(); i++) {
            CarBean.DataBean.ListBean listBean = list.get(i);
            listBean.selected=1;
            listMap.get(i).put(i,true);
/*            int id = preferences.getInt("UserID", 0);
            Map<String,String> parms=new HashMap<String, String>();
            parms.put("uid",id+"");
            parms.put("sellerid",listBean.sellerid+"");
            parms.put("pid",listBean.pid+"");
            parms.put("num",listBean.num+"");
            parms.put("selected",1+"");
            present.UpdateCar(Api.Update_ShopCar,parms);*/

        }
        new Handler().post(task);
/*        if ( recyclerView.getScrollState()==RecyclerView.SCROLL_STATE_IDLE || (recyclerView.isComputingLayout()==false)){
            notifyDataSetChanged();
        }*/

    }

/*    Runnable taskUpdate=new Runnable() {
        @Override
        public void run() {
            int id = preferences.getInt("UserID", 0);
            Map<String,String> parms=new HashMap<String, String>();
            parms.put("uid",id+"");
            parms.put("sellerid",listBean.sellerid+"");
            parms.put("pid",listBean.pid+"");
            parms.put("num",listBean.num+"");
            parms.put("selected",1+"");
            present.UpdateCar(Api.Update_ShopCar,parms);
        }
    };*/

    Runnable task=new Runnable() {
        @Override
        public void run() {
            notifyDataSetChanged();
        }
    };

    public void setAllCheckedFalse(RecyclerView recyclerView){

        for (int i = 0; i < list.size(); i++) {
            CarBean.DataBean.ListBean listBean = list.get(i);
            listBean.selected=0;
            listMap.get(i).put(i,false);
            int id = preferences.getInt("UserID", 0);
            Map<String,String> parms=new HashMap<String, String>();
            parms.put("uid",id+"");
            parms.put("sellerid",listBean.sellerid+"");
            parms.put("pid",listBean.pid+"");
            parms.put("num",listBean.num+"");
            parms.put("selected",0+"");
            present.UpdateCar(Api.Update_ShopCar,parms);
        }
        new Handler().post(taskF);
        System.out.println("走不走");
/*        if ( recyclerView.getScrollState()==RecyclerView.SCROLL_STATE_IDLE || (recyclerView.isComputingLayout()==false)){
            notifyDataSetChanged();
        }*/
    }
    Runnable taskF=new Runnable() {
        @Override
        public void run() {
            System.out.println("为啥不走这个刷新");
            notifyDataSetChanged();
        }
    };

    @Override
    public void Fail(String msg) {
        System.out.println("请求失败："+msg);

    }

    @Override
    public void UpdateSuccess(String result) {
        System.out.println("result成功--"+result);
    }

    @Override
    public void UpdateFail(String result) {
        System.out.println("result失败--"+result);
    }

    static class CarViewHolder extends RecyclerView.ViewHolder{
        private CheckBox iv_CarGoodsSele;
        private ImageView iv_Car;
        private TextView tv_CarTitle;
        private TextView tv_CarSubHead;
        private TextView tv_CarBargain;
        private TextView tv_CarSumPlus;
        private TextView tv_CarSumCut;
        private EditText ed_CarGoodsNum;

        public CarViewHolder(View itemView) {
            super(itemView);
            iv_CarGoodsSele=itemView.findViewById(R.id.iv_carGoodsSele);
            iv_Car=itemView.findViewById(R.id.iv_carItem);
            tv_CarTitle=itemView.findViewById(R.id.tv_carTitle);
            tv_CarSubHead=itemView.findViewById(R.id.tv_carSubHead);
            tv_CarBargain=itemView.findViewById(R.id.tv_carBargain);
            tv_CarSumPlus=itemView.findViewById(R.id.tv_CarsumPlus);
            tv_CarSumCut=itemView.findViewById(R.id.tv_CarSumCut);
            ed_CarGoodsNum=itemView.findViewById(R.id.ed_CarGoodsNum);
        }
    }


    private CarItemClick carItemClick;

    public void setOnCarItemClick(CarItemClick carItemClick){
        this.carItemClick=carItemClick;
    }
    public interface CarItemClick{
        void onCarItemClick(int pid,String url);
    }


    private GetGoodsInfo getGoodsInfo;
    public void setGetGoodsInfo(GetGoodsInfo getGoodsInfo){
        this.getGoodsInfo=getGoodsInfo;
    }
    public interface GetGoodsInfo{
        void onGetGoodsInfo(double price,int count,boolean state);
    }


    private CutPlusI cutPlusI;
    public void setCutPlusI(CutPlusI cutPlusI){
        this.cutPlusI=cutPlusI;
    }
    public interface CutPlusI{
        void onCutPlus(double price,int num,String flag);
    }


}
