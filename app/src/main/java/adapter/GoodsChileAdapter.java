package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bwie.jingdong.R;

import java.util.List;

import bean.GoodsEntity;
import bean.GridMsgBean;

/**
 * Created by 李英杰 on 2017/10/8.
 */

public class GoodsChileAdapter extends RecyclerView.Adapter<GoodsChileAdapter.GridViewHolder>{
    private Context context;
    private List<GoodsEntity> list;

    public GoodsChileAdapter(Context context, List<GoodsEntity> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public GridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.grid_item,null);
        GridViewHolder holder=new GridViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(GridViewHolder holder, final int position) {
        holder.tv.setText(list.get(position).name);
        Glide.with(context).load(list.get(position).icon).into(holder.iv);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.onItemClick(position,list.get(position).pscid);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    static class GridViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv;
        private TextView tv;

        public GridViewHolder(View itemView) {
            super(itemView);
            iv=itemView.findViewById(R.id.iv_grid);
            tv=itemView.findViewById(R.id.tv_grid);
        }
    }

    private ItemClick itemClick;
    public void setOnItemClick(ItemClick itemClick){
        this.itemClick=itemClick;
    }

    public interface ItemClick{
        void onItemClick(int id,int pscid);
    }

}