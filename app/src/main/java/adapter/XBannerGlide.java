package adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by 李英杰 on 2017/9/29.
 */

public class XBannerGlide extends ImageLoader {
    @Override
    public void displayImage(final Context context, final Object path, final ImageView imageView) {

        Glide.with(context).load(path).into(imageView);

    }
}
