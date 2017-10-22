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

import bean.InfoBean;

/**
 * Created by 李英杰 on 2017/10/16.
 */

public class SearchGoodsAdapter  extends RecyclerView.Adapter<SearchGoodsAdapter.ViewHolderImg> {

    private Context context;
    private List<InfoBean> list;

    public SearchGoodsAdapter(Context context, List<InfoBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolderImg onCreateViewHolder(ViewGroup parent, int viewType) {
        View oneView= LayoutInflater.from(context).inflate(R.layout.item_goods,null);
        ViewHolderImg holder=new ViewHolderImg(oneView);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolderImg holder, final int position) {
        final InfoBean infoBean=list.get(position);
        Glide.with(context).load(infoBean.icon).into(holder.imageView);
        holder.tv_price.setText("￥"+infoBean.price);
        holder.tv_title.setText(infoBean.title);
        holder.tv_saleNum.setText("销量："+infoBean.saleNum);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickSearch.onItemClickSearch(position,infoBean.pid,infoBean.deUrl);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolderImg extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView tv_title;
        private TextView tv_price;
        private TextView tv_saleNum;

        public ViewHolderImg(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.iv_ItemOne);
            tv_price=itemView.findViewById(R.id.tv_priceOne);
            tv_title=itemView.findViewById(R.id.tv_titleOne);
            tv_saleNum=itemView.findViewById(R.id.tv_saleNum);
        }
    }


    private ItemClickSearch itemClickSearch;

    public void setItemClickSearch(ItemClickSearch itemClickSearch){
        this.itemClickSearch=itemClickSearch;
    }
    public interface ItemClickSearch{
        void onItemClickSearch(int id,int pid,String url);
    }

}
