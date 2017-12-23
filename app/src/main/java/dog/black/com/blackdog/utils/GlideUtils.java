package dog.black.com.blackdog.utils;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import dog.black.com.blackdog.App;

/**
 * Created by feq on 2017/2/19.
 */

public class GlideUtils {
    public static void laodImag(ImageView imageView, String url) {
        Glide.with(App.getInstance()).load(url).into(imageView);
    }
}
