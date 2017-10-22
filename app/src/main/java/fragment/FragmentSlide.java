package fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bwie.jingdong.R;

import cn.bleu.widget.slidedetails.SlideDetailsLayout;

/**
 * Created by 李英杰 on 2017/10/17.
 */

public class FragmentSlide extends Fragment {

    private View mRoot;
    private SlideDetailsLayout slideDetailsLayout;
    private FragmentManager supportFragmentManager;
    private WebView webView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRoot==null){
            mRoot=inflater.inflate(R.layout.slide_view,null);
        }
        ViewGroup parent = (ViewGroup) mRoot.getParent();
        if (parent!=null){
            parent.removeView(mRoot);
        }
        return mRoot;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        int pid = getArguments().getInt("pid");
        final String Murl=getArguments().getString("url");
        System.out.println("slidePid---------"+pid);
        FragmentGoods goods=new FragmentGoods();
        Bundle bundle=new Bundle();
        bundle.putInt("pid",pid);
        goods.setArguments(bundle);
        slideDetailsLayout = (SlideDetailsLayout)mRoot.findViewById(R.id.slidedetails);
        supportFragmentManager = getChildFragmentManager();
        supportFragmentManager.beginTransaction().replace(R.id.slidedetails_front,goods).commit();

        webView = (WebView) mRoot.findViewById(R.id.slidedetails_behind);
        final WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setUseWideViewPort(true);
        settings.setDomStorageEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR_MR1) {
            new Object() {
                public void setLoadWithOverviewMode(boolean overview) {
                    settings.setLoadWithOverviewMode(overview);
                }
            }.setLoadWithOverviewMode(true);
        }

        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        getActivity().getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl(Murl);
            }
        });
    }
}
