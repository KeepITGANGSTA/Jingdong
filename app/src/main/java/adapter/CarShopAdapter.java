package adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwie.jingdong.GoodsDetails;
import com.bwie.jingdong.R;

import java.util.ArrayList;
import java.util.List;

import bean.CarBean;
import bean.CarInfoBean;

/**
 * Created by 李英杰 on 2017/10/18.
 */

public class CarShopAdapter extends RecyclerView.Adapter<CarShopAdapter.CarShopViewHolder> {
    private Context context;
    private List<CarBean.DataBean>  list;
    private List<Boolean> cbList=new ArrayList<>();

    public CarShopAdapter(Context context,List<CarBean.DataBean> list) {
        this.context = context;
        this.list = list;
        //initCb();
    }

/*    private void initCb() {
        for (int i = 0; i < list.size(); i++) {
            CarBean.DataBean dataBean = list.get(i);
            String sellerid = dataBean.sellerid;
            if (sellerid!=null){
                cbList.add(true);
            }
        }
    }*/


    public CarShopAdapter() {}

    @Override
    public CarShopViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.car_item,null);
        CarShopViewHolder holder=new CarShopViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(final CarShopViewHolder holder, int position) {
        final CarBean.DataBean carInfoBean=list.get(position);
        holder.tv_CarShopName.setText(carInfoBean.sellerName);
        final CarAdapter carAdapter=new CarAdapter(context,carInfoBean.list);
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        holder.recyclerView.setAdapter(carAdapter);
        if (flag==1){
            holder.iv_CarShopAll.setChecked(true);
            carAdapter.setAllChecked(holder.recyclerView);
        }else if (flag==2){
                System.out.println("Flag___flase");
                holder.iv_CarShopAll.setChecked(false);
                carAdapter.setAllCheckedFalse(holder.recyclerView);
        }

        carAdapter.setOnCarItemClick(new CarAdapter.CarItemClick() {
            @Override
            public void onCarItemClick(int pid, String url) {
                Intent intent=new Intent(context, GoodsDetails.class);
                intent.putExtra("pid",pid);
                intent.putExtra("url",url);
                context.startActivity(intent);
            }
        });
        carAdapter.setOnSetHeadTrue(new CarAdapter.SetHeadTrue() {
            @Override
            public void onSetHeadTrue(int current) {
                if (current==carInfoBean.list.size()){
                    holder.iv_CarShopAll.setChecked(true);
                }else {
                    holder.iv_CarShopAll.setChecked(false);
                }
            }
        });
        holder.iv_CarShopAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = holder.iv_CarShopAll.isChecked();
                if (checked){
                    carAdapter.setAllChecked(holder.recyclerView);
                }else {
                    carAdapter.setAllCheckedFalse(holder.recyclerView);
                }
            }
        });
        carAdapter.setGetGoodsInfo(new CarAdapter.GetGoodsInfo() {
            @Override
            public void onGetGoodsInfo(double Dprice, int Dcount,boolean state) {
                    fragGetGoods.onFragGetGoods(Dprice,Dcount,state);

            }
        });
        carAdapter.setCutPlusI(new CarAdapter.CutPlusI() {
            @Override
            public void onCutPlus(double price, int num,String flag) {
                cutPlusOne.onCutPlusOne(price,num,flag);
            }
        });


/*        holder.iv_CarShopAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    carAdapter.setAllChecked(holder.recyclerView);
                }else {
                    carAdapter.setAllCheckedFalse(holder.recyclerView);
                }
            }
        });*/
    }

    public void setTrue(){
        for (int i = 0; i < list.size(); i++) {
            CarBean.DataBean dataBean = list.get(i);
        }
    }

    //public PriceCount priceCount;
    public int flag;
    public void getPriceCount(int flag){
        this.flag=flag;
    }
/*    public interface PriceCount{
        void onPriceCount(double price,int count);
    }*/

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class CarShopViewHolder extends RecyclerView.ViewHolder{
        private CheckBox iv_CarShopAll;
        private TextView tv_CarShopName;
        private RecyclerView recyclerView;
        public CarShopViewHolder(View itemView) {
            super(itemView);
            iv_CarShopAll=itemView.findViewById(R.id.iv_CarShopAll);
            tv_CarShopName=itemView.findViewById(R.id.tv_carShopName);
            recyclerView=itemView.findViewById(R.id.Recy_car);
        }
    }


    private FragGetGoods fragGetGoods;
    public void setFragGetGoods(FragGetGoods fragGetGoods){
        this.fragGetGoods=fragGetGoods;
    }
    public interface FragGetGoods{
        void onFragGetGoods(double price,int count,boolean state);
    }

    public CutPlusOne cutPlusOne;
    public void setCutPlusOne(CutPlusOne cutPlusOne){
        this.cutPlusOne=cutPlusOne;
    }
    public interface CutPlusOne{
        void onCutPlusOne(double price,int num,String flag);
    }
}