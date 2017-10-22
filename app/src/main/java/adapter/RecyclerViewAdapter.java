package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bwie.jingdong.App;
import com.bwie.jingdong.R;

import java.util.List;

import bean.MiaoBean;


/**
 * Created by 李英杰 on 2017/9/8.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context context;
    private List<MiaoBean> list;

    public RecyclerViewAdapter(Context context, List<MiaoBean> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.tuijian_view,null);
        MyViewHolder myViewHolder=new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final MiaoBean miaoBean=list.get(position);
        holder.tv_tuijian_price.setText("￥"+miaoBean.price);
        holder.tv_tuijian.setText(miaoBean.title);
        int i = App.screen_width / 2;

        ViewGroup.MarginLayoutParams mp=new ViewGroup.MarginLayoutParams(holder.iv_tuijian.getLayoutParams());
        mp.setMargins(10,10,10,10);
        LinearLayout.LayoutParams ll=new LinearLayout.LayoutParams(mp);
        ll.weight=i;
        ll.height=App.screen_height/4;
        holder.iv_tuijian.setLayoutParams(ll);
        Glide.with(context).load(miaoBean.images).into(holder.iv_tuijian);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tuiItemClick.onTuiItemClick(miaoBean.pid,miaoBean.detailUrl);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_tuijian;
        private TextView tv_tuijian_price;
        private ImageView iv_tuijian;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_tuijian=itemView.findViewById(R.id.tv_tuijian);
            tv_tuijian_price=itemView.findViewById(R.id.tv_tuijian_price);
            iv_tuijian=itemView.findViewById(R.id.iv_tuijian);
        }
    }

    private TuiItemClick tuiItemClick;

    public void setOnTuiItemClick(TuiItemClick tuiItemClick){
        this.tuiItemClick=tuiItemClick;
    }
    public interface TuiItemClick{
        void onTuiItemClick(int pid,String url);
    }

}
