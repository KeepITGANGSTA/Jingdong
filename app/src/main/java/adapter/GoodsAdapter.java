package adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bwie.jingdong.R;
import com.bwie.jingdong.SearchActivity;

import java.util.List;

import bean.GoodsEntity;

/**
 * Created by 李英杰 on 2017/10/13.
 */

public class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.GoodsViewHolder> {

    private Context context;
    private List<GoodsEntity> list;

    public GoodsAdapter(Context context, List<GoodsEntity> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public GoodsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.goods_item,null);
        GoodsViewHolder holder=new GoodsViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(GoodsViewHolder holder, int position) {
        holder.tv_nameT.setText(list.get(position).nameT);
        GoodsChileAdapter adapter=new GoodsChileAdapter(context,list);
        holder.recyclerView.setLayoutManager(new GridLayoutManager(context,3));
        holder.recyclerView.addItemDecoration(new GoodsOffset(15,20));
        holder.recyclerView.setAdapter(adapter);
        adapter.setOnItemClick(new GoodsChileAdapter.ItemClick() {
            @Override
            public void onItemClick(int id, int pscid) {
                Intent intent=new Intent(context, SearchActivity.class);
                intent.putExtra("pscid",pscid);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class GoodsViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_nameT;
        private RecyclerView recyclerView;
        public GoodsViewHolder(View itemView) {
            super(itemView);
            tv_nameT=itemView.findViewById(R.id.tv_nameT);
            recyclerView=itemView.findViewById(R.id.Recy_childGoods);
        }
    }
}
