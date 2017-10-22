package fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bwie.jingdong.R;

import butterknife.ButterKnife;

/**
 * Created by 李英杰 on 2017/9/30.
 */

public class FragmentFind extends Fragment {

    private View mContentView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mContentView==null){
            mContentView=inflater.inflate(R.layout.fragment_find,container,false);
        }
        ViewGroup viewGroup= (ViewGroup) mContentView.getParent();
        if (viewGroup!=null){
            viewGroup.removeView(mContentView);
        }
        ButterKnife.bind(this,mContentView);
        return mContentView;
    }
}
