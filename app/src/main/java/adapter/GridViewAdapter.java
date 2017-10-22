package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bwie.jingdong.R;

import java.util.List;

import bean.GridMsgBean;

/**
 * Created by 李英杰 on 2017/10/8.
 */

public class GridViewAdapter extends RecyclerView.Adapter<GridViewAdapter.GridViewHolder>{
    private Context context;
    private List<GridMsgBean> list;

    public GridViewAdapter(Context context, List<GridMsgBean> list) {
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
    public void onBindViewHolder(GridViewHolder holder, int position) {
        holder.tv.setText(list.get(position).title);
        System.out.println("图片地址："+list.get(position).icon);
        Glide.with(context).load(list.get(position).icon).into(holder.iv);
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
}