package adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.bwie.jingdong.R;

import java.util.ArrayList;
import java.util.List;

import bean.GridMsgBean;

/**
 * Created by 李英杰 on 2017/10/8.
 */

public class ViewPagerAdapter extends PagerAdapter {
    private Context context;
    private List<GridMsgBean> list;
    private List<GridMsgBean> info;
    private List<List<GridMsgBean>> max;

    public ViewPagerAdapter(Context context, List<List<GridMsgBean>> max) {
        this.context = context;
        this.max=max;
    }

    @Override
    public int getCount() {
        return max.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view= LayoutInflater.from(context).inflate(R.layout.view_grid,null);
        RecyclerView gridView=view.findViewById(R.id.mGridView);
        GridViewAdapter adapter=new GridViewAdapter(context,max.get(position));
        gridView.setLayoutManager(new GridLayoutManager(context,5));
        gridView.addItemDecoration(new GridRecycler(10,5));
        gridView.setAdapter(adapter);
/*        if (position==0){
            RecyclerView gridView=view.findViewById(R.id.mGridView);
            GridViewAdapter adapter=new GridViewAdapter(context,list);
            gridView.setLayoutManager(new GridLayoutManager(context,5));
            gridView.addItemDecoration(new GridRecycler(10,5));
            gridView.setAdapter(adapter);
        }else if (position==1){
            RecyclerView gridView=view.findViewById(R.id.mGridView);
            GridViewAdapter adapter=new GridViewAdapter(context,info);
            gridView.setLayoutManager(new GridLayoutManager(context,5));
            gridView.addItemDecoration(new GridRecycler(10,5));
            gridView.setAdapter(adapter);
        }*/
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
