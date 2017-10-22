package com.bwie.jingdong;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.DetailsOff;
import adapter.MiaoOffset;
import adapter.SearchAdapter;
import adapter.SearchGoodsAdapter;
import bean.DetailsBean;
import bean.InfoBean;
import bean.SearchChildBean;
import bean.SearchGson;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import common.Api;
import okhttp3.Call;
import okhttp3.Response;
import presenter.SearchPresent;
import presenter.SearchView;
import utils.NetCallBack;
import utils.NetUtils;
import utils.OkHttpMethed;

public class SearchActivity extends AppCompatActivity implements SearchView{

    @BindView(R.id.iv_searchBack)
    ImageView iv_searchBack;
    @BindView(R.id.ed_search)
    EditText ed_search;
    @BindView(R.id.tv_search)
    ImageView tv_search;
    @BindView(R.id.iv_cutType)
    ImageView iv_cutType;
    @BindView(R.id.tv_saleOrder)
    TextView tv_saleOrder;
    @BindView(R.id.tv_priceOrder)
    TextView tv_priceOrder;
    @BindView(R.id.Recy_Child)
    RecyclerView Recy_Child;

    private LinearLayoutManager LinManager=new LinearLayoutManager(this);
    private GridLayoutManager gridManager=new GridLayoutManager(this,2);

    private List<SearchChildBean> list=new ArrayList<>();
    private List<InfoBean> goods=new ArrayList<>();
    private SearchPresent searchPresent;
    private SearchGoodsAdapter adapter;
    private SearchGoodsAdapter gadapter;
    private SearchGoodsAdapter ladapter;
    private SearchGoodsAdapter searchGoodsAdapter;
    private SearchAdapter searchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchPresent = new SearchPresent(this,this);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        int pscid = getIntent().getIntExtra("pscid",0);
        if (pscid!=0){
            Map<String,String> parms=new HashMap<>();
            parms.put("pscid",pscid+"");
            parms.put("page",1+"");
            NetUtils.okhttp(Api.Child_Type, parms, new NetCallBack() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Toast.makeText(SearchActivity.this, "子分类请求失败+"+e.toString(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(Call call, Response response) {
                    String result= null;
                    try {
                        result = response.body().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("子分类请求成功"+result);
                    Gson gson=new Gson();
                    SearchGson searchGson = gson.fromJson(result, SearchGson.class);
                    List<SearchGson.DataBean> data = searchGson.data;
                    list.clear();
                    for (int i = 0; i < data.size(); i++) {
                        SearchGson.DataBean dataBean = data.get(i);
                        SearchChildBean childBean=new SearchChildBean();
                        childBean.deUrl=dataBean.detailUrl;
                        childBean.pid=dataBean.pid;
                        childBean.price=dataBean.price;
                        childBean.pscid=dataBean.pscid;
                        childBean.tle=dataBean.title;
                        childBean.num=dataBean.salenum;
                        String images = dataBean.images;
                        String[] split = images.split("\\|");
                        childBean.img=split[0];
                        list.add(childBean);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("list集合："+list.size());
                            searchAdapter = new SearchAdapter(SearchActivity.this,list);
                            Recy_Child.setLayoutManager(LinManager);
                            Recy_Child.setAdapter(searchAdapter);
                            searchAdapter.setOnSearchItemClick(new SearchAdapter.SearchItemClick() {
                                @Override
                                public void onSearchItemClick(int pid,String url) {
                                    Intent intent=new Intent(SearchActivity.this,GoodsDetails.class);
                                    intent.putExtra("pid",pid);
                                    intent.putExtra("url",url);
                                    startActivity(intent);
                                }
                            });
                        }
                    });
                    System.out.println("Recy_Child---"+Recy_Child);
                }
            });
/*            OKHttp.getIntence(this).Call(OkHttpMethed.POST, Api.Child_Type, parms, new OkCallBack() {
                @Override
                public void onFailure(String e, String msg) {
                    Toast.makeText(SearchActivity.this, "子分类请求失败+"+msg, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(String result) {
                    System.out.println("子分类请求成功"+result);
                    Gson gson=new Gson();
                    SearchGson searchGson = gson.fromJson(result, SearchGson.class);
                    List<SearchGson.DataBean> data = searchGson.data;
                    list.clear();
                    for (int i = 0; i < data.size(); i++) {
                        SearchGson.DataBean dataBean = data.get(i);
                        SearchChildBean childBean=new SearchChildBean();
                        childBean.deUrl=dataBean.detailUrl;
                        childBean.pid=dataBean.pid;
                        childBean.price=dataBean.price;
                        childBean.pscid=dataBean.pscid;
                        childBean.tle=dataBean.title;
                        String images = dataBean.images;
                        String[] split = images.split("\\|");
                        childBean.img=split[0];
                        list.add(childBean);
                    }
                    System.out.println("list集合："+list.size());
                    searchAdapter = new SearchAdapter(SearchActivity.this,list);
                    Recy_Child.setLayoutManager(LinManager);
                    Recy_Child.setAdapter(adapter);
                    System.out.println("Recy_Child---"+Recy_Child);
                }
            });*/
        }
    }


    @OnClick({R.id.iv_searchBack,R.id.tv_search,R.id.iv_cutType,R.id.tv_priceOrder,R.id.tv_saleOrder})
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.iv_searchBack:
                finish();
                break;
            case R.id.tv_search:
                String s = ed_search.getText().toString();
                if (TextUtils.isEmpty(s)){
                    Toast.makeText(this, "请输入搜索内容", Toast.LENGTH_SHORT).show();
                    return;
                }
                Map<String,Object> parms=new HashMap<>();
                parms.put("keywords",s);
                searchPresent.SearchGoods(OkHttpMethed.POST,Api.SearchGoods,parms);
                break;
            case R.id.iv_cutType:
                RecyclerView.Adapter adapter1 = Recy_Child.getAdapter();
                    if (adapter1 instanceof SearchGoodsAdapter){

                        RecyclerView.LayoutManager layoutManager = Recy_Child.getLayoutManager();
                        if (layoutManager==LinManager){
                            iv_cutType.setSelected(true);
                        Recy_Child.setLayoutManager(gridManager);
                            //gadapter = new SearchGoodsAdapter(SearchActivity.this,goods);
                            Recy_Child.addItemDecoration(new DetailsOff(5,10));
                        Recy_Child.setAdapter(searchGoodsAdapter);
                    }else if (layoutManager== gridManager){
                            iv_cutType.setSelected(false);
                            //ladapter = new SearchGoodsAdapter(SearchActivity.this,goods);
                        Recy_Child.setLayoutManager(LinManager);
                        Recy_Child.setAdapter(searchGoodsAdapter);
                    }
                }else   if (adapter1 instanceof SearchAdapter){
                        RecyclerView.LayoutManager layoutManager = Recy_Child.getLayoutManager();
                    iv_cutType.setSelected(false);
                        if (layoutManager==LinManager){
                           // SearchAdapter adapter=new SearchAdapter(SearchActivity.this,list);
                            Recy_Child.setLayoutManager(gridManager);
                            Recy_Child.addItemDecoration(new DetailsOff(5,10));
                            Recy_Child.setAdapter(searchAdapter);
                    }else if (layoutManager == gridManager){
                            //SearchAdapter adapter=new SearchAdapter(SearchActivity.this,list);
                            Recy_Child.setLayoutManager(LinManager);
                            Recy_Child.setAdapter(searchAdapter);
                    }



                }
                break;
            case R.id.tv_priceOrder:
                RecyclerView.Adapter adapter = Recy_Child.getAdapter();
                if (adapter instanceof SearchGoodsAdapter){
                    Collections.sort(goods, new Comparator<InfoBean>() {
                        @Override
                        public int compare(InfoBean infoBean, InfoBean t1) {
                            double v = infoBean.price - t1.price;
                            if (v<0){
                                return -1;
                            }else {
                                return 0;
                            }
                        }
                    });
                    searchGoodsAdapter.notifyDataSetChanged();

                }else if (adapter instanceof SearchAdapter){
                    Collections.sort(list, new Comparator<SearchChildBean>() {
                        @Override
                        public int compare(SearchChildBean infoBean, SearchChildBean t1) {
                            double v = infoBean.price - t1.price;
                            if (v<0){
                                return -1;
                            }else {
                                return 0;
                            }
                        }
                    });
                    searchAdapter.notifyDataSetChanged();
                }
                RecyclerView.LayoutManager layoutManager = Recy_Child.getLayoutManager();
                break;
            case R.id.tv_saleOrder:
                RecyclerView.Adapter adapter2 = Recy_Child.getAdapter();
                if (adapter2 instanceof SearchGoodsAdapter){
                    Collections.sort(goods, new Comparator<InfoBean>() {
                        @Override
                        public int compare(InfoBean infoBean, InfoBean t1) {
                            double v = infoBean.saleNum - t1.saleNum;
                            if (v>0){
                                return -1;
                            }else {
                                return 0;
                            }
                        }
                    });
                    searchGoodsAdapter.notifyDataSetChanged();

                }else if (adapter2 instanceof SearchAdapter){
                    Collections.sort(list, new Comparator<SearchChildBean>() {
                        @Override
                        public int compare(SearchChildBean infoBean, SearchChildBean t1) {
                            double v = infoBean.num - t1.num;
                            if (v>0){
                                return -1;
                            }else {
                                return 0;
                            }
                        }
                    });
                    searchAdapter.notifyDataSetChanged();
                }
                break;
        }
    }

    @Override
    public void Fail(String msg) {

    }

    @Override
    public void SearchSuccess(String result) {
        System.out.println("请求结果：==="+result);
        Gson gson=new Gson();
        DetailsBean detailsBean = gson.fromJson(result, DetailsBean.class);
        List<DetailsBean.DataBean> data = detailsBean.data;
        goods.clear();
        for (int i = 0; i < data.size(); i++) {
            DetailsBean.DataBean dataBean = data.get(i);
            InfoBean infoBean=new InfoBean();
            infoBean.icon=dataBean.images;
            infoBean.price=dataBean.price;
            infoBean.title=dataBean.title;
            infoBean.saleNum=dataBean.salenum;
            infoBean.pid=dataBean.pid;
            infoBean.deUrl=dataBean.detailUrl;
            goods.add(infoBean);
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                searchGoodsAdapter = new SearchGoodsAdapter(SearchActivity.this,goods);
                Recy_Child.setLayoutManager(LinManager);
                Recy_Child.setAdapter(searchGoodsAdapter);
                searchGoodsAdapter.setItemClickSearch(new SearchGoodsAdapter.ItemClickSearch() {
                    @Override
                    public void onItemClickSearch(int id, int pid,String url) {
                        Intent intent=new Intent(SearchActivity.this,GoodsDetails.class);
                        intent.putExtra("pid",pid);
                        intent.putExtra("url",url);
                        startActivity(intent);
                    }
                });
            }
        });


    }

    @Override
    public void SearchFail(String resulet) {
    }
}
