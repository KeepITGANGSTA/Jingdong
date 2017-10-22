package adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bwie.jingdong.R;

import org.w3c.dom.Text;

import java.util.List;

import bean.SearchChildBean;

/**
 * Created by 李英杰 on 2017/10/15.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ChildViewHolder> {

    private Context context;
    private List<SearchChildBean> list;

    public SearchAdapter(Context context, List<SearchChildBean> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public ChildViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.child_view,null);
        ChildViewHolder holder=new ChildViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ChildViewHolder holder, int position) {
        final SearchChildBean childBean=list.get(position);
        Glide.with(context).load(childBean.img).into(holder.imageView);
        holder.tv_title.setText(childBean.tle);
        holder.tv_price.setText("￥"+childBean.price);
        holder.tv_saleNum.setText("销量："+childBean.num);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchItemClick.onSearchItemClick(childBean.pid,childBean.deUrl);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ChildViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView tv_price;
        private TextView tv_title;
        private TextView tv_saleNum;
        public ChildViewHolder(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.iv_childVer);
            tv_price=itemView.findViewById(R.id.tv_childVer_price);
            tv_title=itemView.findViewById(R.id.tv_childVer_title);
            tv_saleNum=itemView.findViewById(R.id.tv_childVer_saleNum);
        }
    }
    private SearchItemClick searchItemClick;

    public void setOnSearchItemClick(SearchItemClick searchItemClick){
        this.searchItemClick=searchItemClick;
    }
    public interface SearchItemClick{
        void onSearchItemClick(int pid,String url);
    }

}
