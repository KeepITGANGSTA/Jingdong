package fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.bwie.jingdong.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 李英杰 on 2017/10/13.
 */

public class TypeFragment extends Fragment {

    private View mRoot;
    @BindView(R.id.typeFragment)
    RecyclerView typeFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRoot==null){
            mRoot=inflater.inflate(R.layout.type_fragment,null);
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
