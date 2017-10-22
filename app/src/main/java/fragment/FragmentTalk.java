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
 * Created by 李英杰 on 2017/10/16.
 */

public class FragmentTalk extends Fragment{

    private View mRoot;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRoot==null){
            mRoot=inflater.inflate(R.layout.talk_f_view,null);
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

    }
}
