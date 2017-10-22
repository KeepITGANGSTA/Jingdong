package fragment;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import adapter.ViewPagerAdapter;

/**
 * Created by 李英杰 on 2017/10/8.
 */

public class TestAdapter extends PagerAdapter {
    private Context context;

    public TestAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        System.out.println(position);
        TextView textView = new TextView(context);
        textView.setText(Integer.toString(position));
        container.addView(textView);
        return textView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
