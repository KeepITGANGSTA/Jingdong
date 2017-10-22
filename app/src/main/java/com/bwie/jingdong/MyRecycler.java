package com.bwie.jingdong;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by 李英杰 on 2017/9/29.
 */

public class MyRecycler extends RecyclerView {
    public MyRecycler(Context context) {
        super(context);
    }

    public MyRecycler(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRecycler(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        return super.onInterceptTouchEvent(e);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        int height=R.attr.actionBarSize;
        switch (e.getAction()){
            case MotionEvent.ACTION_DOWN:
                float y = e.getY();
                System.out.println("yyyyyyyy=="+(int)y);
                System.out.println("height"+height);
                if ((int)y<160){
                    return false;
                }
                break;
        }
        return super.onTouchEvent(e);
        //return super.onTouchEvent(e);
    }
}
