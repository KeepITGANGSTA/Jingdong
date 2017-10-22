package adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.jingdong.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.BillInfoBean;
import common.Api;
import pay.H5PayDemoActivity;
import pay.PayDemoActivity;
import presenter.AlertBillPresent;
import presenter.AlertBillPresentView;
import utils.OkHttpMethed;
import utils.SharedPreferencesUtil;

/**
 * Created by 李英杰 on 2017/10/21.
 */

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.BillViewHolder> implements AlertBillPresentView{

    private Context context;
    private List<BillInfoBean> list;
    private final AlertBillPresent abp;
    private String[] choose={"取消订单","待支付","已支付"};
    private final SharedPreferences preferences;
    private final Map<String, Object> parms;
    private AlertDialog show;

    public BillAdapter(Context context, List<BillInfoBean> list) {
        this.context = context;
        this.list = list;
        abp = new AlertBillPresent(this,context);
        preferences = SharedPreferencesUtil.getPreferences();
        parms = new HashMap<String, Object>();
    }

    @Override
    public BillViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.recy_bill_details,null);
        BillViewHolder holder=new BillViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(BillViewHolder holder, int position) {
        final BillInfoBean billInfoBean=list.get(position);
        holder.tv_billTime.setText(billInfoBean.billTime);
        holder.tv_billPrice.setText("￥"+billInfoBean.billPrice);
        holder.tv_billOrderid.setText("序列号："+billInfoBean.orderid);
        int tv_billState = billInfoBean.status;
        if (0==tv_billState){
            holder.tv_billState.setText("等待付款");
            holder.btn_payBill.setVisibility(View.VISIBLE);
        }else if (1==tv_billState){
            holder.tv_billState.setText("已支付");
            holder.btn_payBill.setVisibility(View.GONE);
        }else if (2==tv_billState){
            holder.tv_billState.setText("已取消");
            holder.btn_payBill.setVisibility(View.GONE);
        }

        holder.btn_payBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, PayDemoActivity.class);
                intent.putExtra("orderid",billInfoBean.orderid);
                context.startActivity(intent);
                ((Activity)context).finish();
            }
        });

        holder.btn_cancleBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder ab=new AlertDialog.Builder(context);
                ab.setTitle("修改订单");
                ab.setItems(choose, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int id = preferences.getInt("UserID", 0);
                        parms.clear();
                        switch (i){
                            case 0:
                                billInfoBean.status=2;
                                parms.put("uid",id);
                                parms.put("status",2);
                                parms.put("orderId",billInfoBean.orderid);
                                abp.AlertBill(OkHttpMethed.POST,Api.AlertBill,parms);
                                break;
                            case 1:
                                billInfoBean.status=0;
                                parms.put("uid",id);
                                parms.put("status",0);
                                parms.put("orderId",billInfoBean.orderid);
                                abp.AlertBill(OkHttpMethed.POST,Api.AlertBill,parms);
                                break;
                            case 2:
                                billInfoBean.status=1;
                                parms.put("uid",id);
                                parms.put("status",1);
                                parms.put("orderId",billInfoBean.orderid);
                                abp.AlertBill(OkHttpMethed.POST,Api.AlertBill,parms);
                                break;
                        }
                    }
                });
                show = ab.show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void Fail(String msg) {

    }

    @Override
    public void AlertBillFail(String resutl) {
    }

    @Override
    public void AlertBillSuccessFail(String resutl) {
        if (show!=null){
            show.dismiss();
        }
        notifyDataSetChanged();
    }

    static class BillViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_billOrderid;
        private TextView tv_billTime;
        private TextView tv_billState;
        private TextView tv_billPrice;
        private Button btn_cancleBill;
        private Button btn_payBill;
        public BillViewHolder(View itemView) {
            super(itemView);
            tv_billOrderid=itemView.findViewById(R.id.tv_billOrderid);
            tv_billPrice=itemView.findViewById(R.id.tv_billPrice);
            tv_billTime=itemView.findViewById(R.id.tv_billTime);
            tv_billState=itemView.findViewById(R.id.tv_billState);
            btn_cancleBill=itemView.findViewById(R.id.btn_cancleBill);
            btn_payBill=itemView.findViewById(R.id.btn_payBill);
        }
    }
}
