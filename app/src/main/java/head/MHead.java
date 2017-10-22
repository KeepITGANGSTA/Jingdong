package head;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bwie.jingdong.R;
import com.liaoinstan.springview.container.BaseHeader;

/**
 * Created by 李英杰 on 2017/10/22.
 */

public class MHead extends BaseHeader{

    private Context context;
    private Toolbar toolbar;
    private ImageView iv_fragme;
    private TextView tv_fresh;
    private AnimationDrawable drawable;

    public MHead(Toolbar toolbar, Context context) {
        if (toolbar!=null){
            this.toolbar = toolbar;
        }
        this.context=context;
    }



    @Override
    public View getView(LayoutInflater inflater, ViewGroup viewGroup) {
        View view = inflater.inflate(R.layout.head_view, viewGroup, true);
        iv_fragme = view.findViewById(R.id.iv_fame);
        tv_fresh = view.findViewById(R.id.tv_fresh);
        return view;
    }

    @Override
    public void onPreDrag(View rootView) {
    }



    @Override
    public void onDropAnim(View rootView, int dy) {
        float pivotY = rootView.getPivotY();
        System.out.println("下拉高度----"+dy+"----"+pivotY);
        if (dy<200){
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) iv_fragme.getLayoutParams();
            layoutParams.width=dy;
            layoutParams.height=dy;
            iv_fragme.setLayoutParams(layoutParams);
        }
        if (toolbar!=null){
            if (dy>30){
                toolbar.setVisibility(View.GONE);
            }else {
                toolbar.setVisibility(View.VISIBLE);
            }
        }


    }


    @Override
    public int getDragLimitHeight(View rootView) {
        return 250;
    }

    @Override
    public void onLimitDes(View rootView, boolean upORdown) {
        if (upORdown){
            tv_fresh.setText("下拉刷新...");
        }else {
            tv_fresh.setText("松开刷新...");
        }
    }

    @Override
    public void onStartAnim() {
        tv_fresh.setText("正在刷新...");
        iv_fragme.setImageResource(R.drawable.frame_jing);
        drawable = (AnimationDrawable) iv_fragme.getDrawable();
        drawable.start();
    }

    @Override
    public void onFinishAnim() {
        if (toolbar!=null){
            toolbar.setVisibility(View.VISIBLE);
        }
        drawable.stop();
        tv_fresh.setText("下拉刷新...");
        ((Activity)context).getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }
}
