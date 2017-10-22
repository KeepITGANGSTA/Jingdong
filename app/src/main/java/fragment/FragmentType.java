package fragment;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.jingdong.App;
import com.bwie.jingdong.R;
import com.bwie.jingdong.SearchActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import bean.GridBean;
import bean.TypeBean;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import common.Api;
import presenter.Present;
import presenter.PresentView;


/**
 * Created by 李英杰 on 2017/9/30.
 */

public class FragmentType extends Fragment implements PresentView{

    private View mContentView;
    private Present present;

    private int scrollViewMiddle=0;
    private int scrllViewWidth=0;

    @BindView(R.id.Type_toolbar)
    Toolbar Type_toolbar;
    @BindView(R.id.rel_TypeEdit)
    RelativeLayout rel_TypeEdit;
    @BindView(R.id.tools_scrlllview)
    ScrollView tools_scrlllview;
    @BindView(R.id.lin_tools)
    LinearLayout lin_tools;
    private List<TypeBean> list=new ArrayList<>();



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mContentView==null){
            mContentView=inflater.inflate(R.layout.fragment_type,container,false);
        }
        ViewGroup viewGroup= (ViewGroup) mContentView.getParent();
        if (viewGroup!=null){
            viewGroup.removeView(mContentView);
        }
        ButterKnife.bind(this,mContentView);
        return mContentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        present=new Present(this);
        Rect frame = new Rect();
        getActivity().getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        mContentView.setPadding(0,statusBarHeight,0,0);
        if (list.size()==0){
            present.getGrid(Api.GridView);
        }
        tools_scrlllview.setScrollY(App.screen_height/2);
        initType();

    }

    private void initType() {

    }


    private View.OnClickListener typeItem=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int id = view.getId();
            itemBgColor(id);
            changeTextLocation(id);
            GoodsFragment intencec = GoodsFragment.getIntencec(id);
            getChildFragmentManager().beginTransaction().replace(R.id.framelayout,intencec).commit();
        }
    };

    private void changeTextLocation(int clickPosition) {
        int x = (lin_tools.getChildAt(clickPosition).getTop() - getScrollViewMiddle() + (getViewheight(lin_tools.getChildAt(clickPosition)) / 2));
        tools_scrlllview.smoothScrollTo(0, x);
    }



    /**
     * 返回scrollview的中间位置
     *
     * @return
     */
    private int getScrollViewMiddle() {
        if (scrollViewMiddle == 0)
            scrollViewMiddle = getScrollViewheight() / 2;
        return scrollViewMiddle;
    }

    /**
     * 返回ScrollView的宽度
     *
     * @return
     */
    private int getScrollViewheight() {
        if (scrllViewWidth == 0)
            scrllViewWidth = tools_scrlllview.getBottom() - tools_scrlllview.getTop();
        return scrllViewWidth;
    }

    /**
     * 返回view的宽度
     *
     * @param view
     * @return
     */
    private int getViewheight(View view) {
        return view.getBottom() - view.getTop();
    }



    private void itemBgColor(int id) {
        for (int i = 0; i < lin_tools.getChildCount(); i++) {
            TextView tv= (TextView) lin_tools.getChildAt(i);
            if (i==id){
                tv.setTextColor(Color.parseColor("#ff0000"));
                tv.setBackgroundColor(Color.parseColor("#F3F5F7"));
            }else {
                tv.setTextColor(Color.parseColor("#000000"));
                tv.setBackgroundColor(Color.parseColor("#ffffff"));
            }
        }
    }

    @OnClick({R.id.rel_TypeEdit})
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.rel_TypeEdit:
                Intent intent=new Intent(getContext(), SearchActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void success(String result) {


    }

    @Override
    public void requestFail() {

    }

    @Override
    public void fail() {

    }

    @Override
    public void GridSuccess(String result) {
        System.out.println("分类--------------"+result);
        if (list!=null) {
            Gson gson = new Gson();
            GridBean gridBean = gson.fromJson(result, GridBean.class);
            List<GridBean.DataBean> data = gridBean.data;
            list.clear();
            for (int i = 0; i < data.size(); i++) {
                    GridBean.DataBean dataBean = data.get(i);
                    TypeBean typeBean = new TypeBean();
                    typeBean.cid = dataBean.cid;
                    typeBean.name = dataBean.name;
                    list.add(typeBean);
                }
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                System.out.println("list集合----"+list.size());
                lin_tools.removeAllViews();
                    for (int i = 0; i < list.size(); i++) {
                        TextView textView=new TextView(getContext());
                        LinearLayout.LayoutParams ll=new LinearLayout.LayoutParams(App.screen_width/5,App.screen_height/14);
                        textView.setLayoutParams(ll);
                        textView.setGravity(Gravity.CENTER);
                        textView.setId(i);
                        textView.setOnClickListener(typeItem);
                        textView.setBackgroundColor(Color.parseColor("#ffffff"));
                        textView.setTextColor(Color.parseColor("#000000"));
                        textView.setText(list.get(i).name);
                        lin_tools.addView(textView);
                    }
            }
        });

    }

    @Override
    public void Gridfail() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        present.Destory();
    }
}
