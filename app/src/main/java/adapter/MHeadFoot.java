package adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

/**
 * Created by 李英杰 on 2017/10/7.
 */

public class MHeadFoot extends HeaderAndFooterWrapper {
    public MHeadFoot(RecyclerView.Adapter adapter) {
        super(adapter);
    }

}
