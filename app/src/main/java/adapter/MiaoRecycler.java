package adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bwie.jingdong.R;

import java.util.List;

import bean.MiaoBean;

/**
 * Created by 李英杰 on 2017/10/9.
 */

public class MiaoRecycler extends RecyclerView.Adapter<MiaoRecycler.MiaoViewHolder> {

    private Context context;
    private List<MiaoBean> list;

    public MiaoRecycler(Context context, List<MiaoBean> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public MiaoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.miao_item,null);
        MiaoViewHolder holder=new MiaoViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MiaoViewHolder holder, int position) {
        final MiaoBean mb=list.get(position);
        Glide.with(context).load(mb.images).into(holder.iv);
        holder.tv_bargain.setText("￥"+mb.bargainPrice);
        holder.tv_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.tv_price.setText("￥"+mb.price);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                miaoItemClick.onMiaoItemClick(mb.pid,mb.detailUrl);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class MiaoViewHolder extends RecyclerView.ViewHolder{
        private ImageView iv;
        private TextView tv_bargain;
        private TextView tv_price;
        public MiaoViewHolder(View itemView) {
            super(itemView);
            iv=itemView.findViewById(R.id.iv_miao);
            tv_bargain=itemView.findViewById(R.id.tv_bargain);
            tv_price=itemView.findViewById(R.id.tv_price);
        }
    }

    public MiaoItemClick miaoItemClick;

    public void setOnMiaoItemClick(MiaoItemClick miaoItemClick){
        this.miaoItemClick=miaoItemClick;
    }
    public interface MiaoItemClick{
        void onMiaoItemClick(int pid,String url);
    }

}