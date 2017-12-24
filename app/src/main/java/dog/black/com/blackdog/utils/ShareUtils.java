package dog.black.com.blackdog.utils;

import android.content.Context;
import android.content.Intent;


/**
 * 2016 逛街购（北京）网络科技有限公司，版权所有
 * guangjiegou - Android客户端
 * Context:分享工具类
 */
public class ShareUtils {
    public static void shareGirl(String title, String url, final Context context) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, title + "\n" + url);
        shareIntent.setType("text/plain");
        context.startActivity(Intent.createChooser(shareIntent, "分享到"));
    }

    public static void shareApp(Context context, String url) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, url);
        shareIntent.setType("text/plain");

        //设置分享列表的标题，并且每次都显示分享列表
        context.startActivity(Intent.createChooser(shareIntent, "分享到"));
    }
}
