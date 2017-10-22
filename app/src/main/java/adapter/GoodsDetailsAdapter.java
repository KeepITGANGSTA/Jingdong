package adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by 李英杰 on 2017/10/16.
 */

public class GoodsDetailsAdapter extends FragmentPagerAdapter {

    private Context context;
    private List<Fragment> fragmentList;
    private List<String> title;

    public GoodsDetailsAdapter(FragmentManager fm, Context context, List<Fragment> fragmentList,List<String> title) {
        super(fm);
        this.title=title;
        this.context = context;
        this.fragmentList = fragmentList;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title.get(position);
    }

    public GoodsDetailsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
