package fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bwie.jingdong.CusActivity;
import com.bwie.jingdong.DetailsActivity;
import com.bwie.jingdong.GoodsActivity;
import com.bwie.jingdong.GoodsDetails;
import com.bwie.jingdong.R;
import com.bwie.jingdong.SearchActivity;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import adapter.MHeadFoot;
import adapter.MiaoOffset;
import adapter.MiaoRecycler;
import adapter.RecyclerViewAdapter;
import adapter.RecyclerViewDivide;
import adapter.ViewPagerAdapter;
import adapter.XBannerGlide;
import bean.GridBean;
import bean.GridMsgBean;
import bean.GsonBean;
import bean.MiaoBean;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import common.Api;
import head.MHead;
import presenter.Present;
import presenter.PresentView;

/**
 * Created by 李英杰 on 2017/9/30.
 */

public class FragmentOne extends Fragment implements PresentView{

    private List<MiaoBean> miaoBeanList=new ArrayList<>();
    private List<MiaoBean> TuiJ=new ArrayList<>();

    private List<ImageView> dotList=new ArrayList<>();
    private List<String> tvList=new ArrayList<>();
    private List<GridMsgBean> gridList=new ArrayList<GridMsgBean>();
    private List<GridMsgBean> info=new ArrayList<>();
    private List<List<GridMsgBean>> max=new ArrayList<>();
/*    private ArrayList<String> list=new ArrayList<String>(){{
        add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1649060787,253637340&fm=27&gp=0.jpg");
        add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1507193296&di=8b6d3b1618e6b8f0e825d6e025f7e3e1&imgtype=jpg&er=1&src=http%3A%2F%2Fwww.zhaoxi.net%2Fimages%2Fmymake%2F2014%2F4%2F201404242353235302.jpg");
        add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506598616492&di=ffc9053d88fa5378b3a81d3380f57309&imgtype=0&src=http%3A%2F%2Fwww.th7.cn%2Fd%2Ffile%2Fp%2F2014%2F04%2F26%2F42bb698e1310d66e112b60c39b69093c.jpg");
    }};*/
    private List<String> list=new ArrayList<>();
    private List<String> url=new ArrayList<>();
    private HashMap<String,String> map=new HashMap<String,String>();
    private View mContentView;
    private RecyclerViewAdapter adapter;
    private int mDistanceY;
    private Banner mba;

    @BindView(R.id.iv_clean)
    ImageView iv_clean;
    @BindView(R.id.tv_clean)
    TextView tv_clean;
    @BindView(R.id.iv_info)
    ImageView iv_info;
    @BindView(R.id.tv_info)
    TextView tv_info;
    @BindView(R.id.rel_Edit)
    RelativeLayout rel_edit;
    @BindView(R.id.mToolbar)
    Toolbar toolbar;
    @BindView(R.id.mRec)
    RecyclerView mRec;
//    private LinearLayout lin_clean;
    @BindView(R.id.lin_clean)
LinearLayout lin_clean;
    @BindView(R.id.lin_info)
    LinearLayout lin_info;
    @BindView(R.id.mNv)
    NestedScrollView mNv;
    @BindView(R.id.mSv)
    SpringView mSv;
    private Present present;
    private ViewPager mVP;
    private LinearLayout dotLin;
    private RecyclerView miaoRecycler;
    private HeaderAndFooterWrapper headfoot;
    private View view;
    private View gridView;
    private View miaos;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      // getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (mContentView==null){
            mContentView=inflater.inflate(R.layout.fragment_one,container,false);
        }
        ViewGroup viewGroup= (ViewGroup) mContentView.getParent();
        if (viewGroup!=null){
            viewGroup.removeView(mContentView);
        }
        ButterKnife.bind(this,mContentView);
        return mContentView;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRecyclerView();
        present = new Present(this);
        present.Prequest(Api.VP);
        present.getGrid(Api.GridView);
        //mSv.setHeader(new DefaultHeader(getContext()));
        mSv.setHeader(new MHead(toolbar,getContext()));
        mSv.setFooter(new DefaultFooter(getContext()));
        mSv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSv.onFinishFreshAndLoad();
                    }
                },2000);
            }

            @Override
            public void onLoadmore() {
            }
        });

        mNv.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                System.out.println("scrollX=="+scrollX);
                System.out.println("scrollY=="+scrollY);
                System.out.println("oldScrollX=="+oldScrollX);
                System.out.println("oldScrollY=="+oldScrollY);
                //滑动的距离
                mDistanceY = scrollY;
                //toolbar的高度
                final int toolbarHeight = toolbar.getBottom();
                System.out.println("toolbarHeight=="+toolbarHeight);
                System.out.println("mDistanceY=="+mDistanceY);
                //当滑动的距离 <= toolbar高度的时候，改变Toolbar背景色的透明度，达到渐变的效果
                if (mDistanceY <= toolbarHeight) {
                    Window window = getActivity().getWindow();
                    //window.setStatusBarColor(Color.parseColor("#ff0000"));
                    toolbar.setClickable(true);
                    getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    System.out.println("mDistanceY=="+mDistanceY);
                    System.out.println("toolbarHeight=="+toolbarHeight);
                    float scale = (float) mDistanceY / toolbarHeight;
                    float alpha = scale*255;
                    toolbar.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
                    tv_clean.setTextColor(Color.WHITE);
                    tv_info.setTextColor(Color.WHITE);
                    iv_clean.setImageResource(R.mipmap.clean);
                    iv_info.setImageResource(R.mipmap.info);
                    rel_edit.setBackgroundResource(R.drawable.edit_bg);
                } else {
                    getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    //将标题栏的颜色设置为完全不透明状态
                    //toolbar.setBackgroundResource(R.color.colorToolBar);
                    tv_clean.setTextColor(Color.BLACK);
                    tv_info.setTextColor(Color.BLACK);
                    iv_clean.setImageResource(R.mipmap.cleanb);
                    iv_info.setImageResource(R.mipmap.infob);
                    rel_edit.setBackgroundResource(R.drawable.edit_bgb);
                }
            }
        });
    }

    private void initRecyclerView() {
        initData();

        view = LayoutInflater.from(getContext()).inflate(R.layout.xbanner,null);
        gridView = LayoutInflater.from(getContext()).inflate(R.layout.grid_view,null);
        miaos = LayoutInflater.from(getContext()).inflate(R.layout.miaosha,null);
        miaoRecycler = miaos.findViewById(R.id.mMiaoRecy);
        mVP = gridView.findViewById(R.id.mViewPager);
        dotLin = gridView.findViewById(R.id.dotLin);

        mba = view.findViewById(R.id.mBanner);
        mba.setImageLoader(new XBannerGlide());
        mba.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                System.out.println("positon-------------------"+position);
                Intent intent=new Intent(getContext(),DetailsActivity.class);
                intent.putExtra("url",url.get(position));
                startActivity(intent);
            }
        });





        //mRec.addItemDecoration(new RecyclerViewDivide(getContext(), RecyclerViewDivide.VETTICAL_LIST));

        toolbar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Toast.makeText(getContext(), "toolbar", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
/*        mRec.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                //滑动的距离
                mDistanceY += dy;
                //toolbar的高度
                final int toolbarHeight = toolbar.getBottom();
                //当滑动的距离 <= toolbar高度的时候，改变Toolbar背景色的透明度，达到渐变的效果
                if (mDistanceY <= toolbarHeight) {
                    toolbar.setClickable(true);
                    getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    System.out.println("mDistanceY=="+mDistanceY);
                    System.out.println("toolbarHeight=="+toolbarHeight);
                    float scale = (float) mDistanceY / toolbarHeight;
                    float alpha = scale*255;
                    toolbar.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
                    tv_clean.setTextColor(Color.WHITE);
                    tv_info.setTextColor(Color.WHITE);
                    iv_clean.setImageResource(R.mipmap.clean);
                    iv_info.setImageResource(R.mipmap.info);
                    rel_edit.setBackgroundResource(R.drawable.edit_bg);
                } else {
                    toolbar.setClickable(true);
                    getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    //将标题栏的颜色设置为完全不透明状态
                    //toolbar.setBackgroundResource(R.color.colorToolBar);
                    tv_clean.setTextColor(Color.BLACK);
                    tv_info.setTextColor(Color.BLACK);
                    iv_clean.setImageResource(R.mipmap.cleanb);
                    iv_info.setImageResource(R.mipmap.infob);
                    rel_edit.setBackgroundResource(R.drawable.edit_bgb);
                }

            }
        });*/
    }
    private void initData() {
        tvList.clear();
        for (int i = 0; i < 20; i++) {
            tvList.add("条目"+i);
        }
    }

    @OnClick({R.id.lin_info,R.id.lin_clean,R.id.rel_Edit})
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.lin_info:
                Toast.makeText(getContext(), "消息", Toast.LENGTH_SHORT).show();
                break;
            case R.id.lin_clean:
                Toast.makeText(getContext(), "扫描", Toast.LENGTH_SHORT).show();
                IntentIntegrator integrator=new IntentIntegrator(getActivity());
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("扫描二维码");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(true);
                integrator.setBarcodeImageEnabled(true);
                integrator.setCaptureActivity(CusActivity.class);
                integrator.initiateScan();
                break;
            case R.id.rel_Edit:
                Intent intent=new Intent(getContext(), SearchActivity.class);
                startActivity(intent);
                break;
        }
    }


    @Override
    public void success(String result) {
        Gson gson=new Gson();
        GsonBean gsonBean = gson.fromJson(result, GsonBean.class);
        List<GsonBean.DataBean> data = gsonBean.data;
        list.clear();
        url.clear();
        for (GsonBean.DataBean dataBean : data) {
            if (dataBean.type==0){
                //map.put(dataBean.icon,dataBean.url);
                list.add(dataBean.icon);
                url.add(dataBean.url);
            }else if (dataBean.type==1){
//                map.put(dataBean.icon,"活动页");
                list.add(dataBean.icon);
                url.add("活动页");
            }
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mba.setImages(list);
                mba.start();
            }
        });

        GsonBean.MiaoshaBean miaosha = gsonBean.miaosha;
        int time = miaosha.time;
        String name = miaosha.name;
        List<GsonBean.MiaoshaBean.ListBeanX> list = miaosha.list;
        miaoBeanList.clear();
        for (GsonBean.MiaoshaBean.ListBeanX listBeanX : list) {
            MiaoBean mb=new MiaoBean();
            mb.bargainPrice=listBeanX.bargainPrice;
            mb.detailUrl=listBeanX.detailUrl;
            String[] img=listBeanX.images.split("\\|");
            mb.images=img[0];
            mb.pid=listBeanX.pid;
            mb.price=listBeanX.price;
            mb.pscid=listBeanX.pscid;
            mb.salenum=listBeanX.salenum;
            mb.sellerid=listBeanX.sellerid;
            mb.subhead=listBeanX.subhead;
            mb.title=listBeanX.title;
            miaoBeanList.add(mb);
            System.out.println("images-----"+img[0]);
        }


        GsonBean.TuijianBean tuijian = gsonBean.tuijian;
        String name1 = tuijian.name;
        List<GsonBean.TuijianBean.ListBean> list1 = tuijian.list;
        for (int i = 0; i < list1.size(); i++) {
            GsonBean.TuijianBean.ListBean listBean = list1.get(i);
            MiaoBean mbb=new MiaoBean();
            mbb.bargainPrice=listBean.bargainPrice;
            mbb.detailUrl=listBean.detailUrl;
            String[] img=listBean.images.split("\\|");
            mbb.images=img[0];
            mbb.pid=listBean.pid;
            mbb.price=listBean.price;
            mbb.pscid=listBean.pscid;
            mbb.salenum=listBean.salenum;
            mbb.sellerid=listBean.sellerid;
            mbb.subhead=listBean.subhead;
            mbb.title=listBean.title;
            TuiJ.add(mbb);
        }

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MiaoRecycler mr=new MiaoRecycler(getContext(),miaoBeanList);
                miaoRecycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
                miaoRecycler.addItemDecoration(new MiaoOffset(70,5));
                miaoRecycler.setAdapter(mr);
                mr.setOnMiaoItemClick(new MiaoRecycler.MiaoItemClick() {
                    @Override
                    public void onMiaoItemClick(int pid, String url) {
                        Intent intent=new Intent(getContext(), GoodsDetails.class);
                        intent.putExtra("pid",pid);
                        intent.putExtra("url",url);
                        startActivity(intent);
                    }
                });

                adapter = new RecyclerViewAdapter(getContext(),TuiJ);
                headfoot = new HeaderAndFooterWrapper(adapter);
                mRec.setLayoutManager(new GridLayoutManager(getContext(),2));
                mRec.setAdapter(headfoot);
                headfoot.addHeaderView(view);
                headfoot.addHeaderView(gridView);
                headfoot.addHeaderView(miaos);
                adapter.setOnTuiItemClick(new RecyclerViewAdapter.TuiItemClick() {
                    @Override
                    public void onTuiItemClick(int pid, String url) {
                        Intent intent=new Intent(getContext(), GoodsDetails.class);
                        intent.putExtra("pid",pid);
                        intent.putExtra("url",url);
                        startActivity(intent);
                    }
                });

            }
        });


    }

    @Override
    public void requestFail() {

    }

    @Override
    public void fail() {

    }

    @Override
    public void GridSuccess(String result) {
        System.out.println("GridView-------"+result);
        Gson gson=new Gson();
        GridBean gridBean = gson.fromJson(result, GridBean.class);
        List<GridBean.DataBean> data = gridBean.data;
        gridList.clear();
        info.clear();
        max.clear();
        for (int i = 0; i < data.size(); i++) {
            GridBean.DataBean dataBean = data.get(i);
            if (i<10){
                GridMsgBean gb=new GridMsgBean();
                gb.title=dataBean.name;
                gb.icon=dataBean.icon;
                gridList.add(gb);
            }else if (i>=10){
                GridMsgBean g=new GridMsgBean();
                g.title=dataBean.name;
                g.icon=dataBean.icon;
                info.add(g);
            }
        }
        max.add(gridList);
        max.add(info);

        System.out.println("gridList---------"+gridList.size());
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dotLin.removeAllViews();
                for (int i = 0; i < max.size(); i++) {
                    ImageView dot=new ImageView(getContext());
                    if (i==0){
                        dot.setImageResource(R.drawable.dot_selected);
                    }else {
                        dot.setImageResource(R.drawable.dot_empty);
                    }
                    LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(10,10);
                    lp.setMargins(5,0,5,0);
                    dotLin.addView(dot,lp);
                    dotList.add(dot);
                }
                ViewPagerAdapter adapt=new ViewPagerAdapter(getContext(),max);
                mVP.setAdapter(adapt);
                mVP.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        for (int i = 0; i < dotLin.getChildCount(); i++) {
                            ImageView ddot= (ImageView) dotLin.getChildAt(i);
                            if (i==position){
                                ddot.setImageResource(R.drawable.dot_selected);
                            }else {
                                ddot.setImageResource(R.drawable.dot_empty);
                            }
                        }
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });

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
