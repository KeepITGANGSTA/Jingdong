package fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.bwie.jingdong.R;

import butterknife.ButterKnife;

/**
 * Created by 李英杰 on 2017/10/16.
 */

public class FragmentDetails extends Fragment{

    private View mRoot;
    private WebView webView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRoot==null){
            mRoot=inflater.inflate(R.layout.details_f_view,null);
        }
        ViewGroup parent = (ViewGroup) mRoot.getParent();
        if (parent!=null){
            parent.removeView(mRoot);
        }
        ButterKnife.bind(this.mRoot);
        return mRoot;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        webView = mRoot.findViewById(R.id.details_wb);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebChromeClient(new WebChromeClient());
        String url = getArguments().getString("url");
        webView.loadUrl(url);
    }
}
