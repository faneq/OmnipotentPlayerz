package dog.black.com.blackdog.videoPage;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import dog.black.com.blackdog.videoPage.bean.ViedeoTitle;

/**
 * Created by feq on 2017/2/25.
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    public final int COUNT = 6;
    private String[] titlesx = new String[]{"搜索", "爱奇艺", "优酷", "乐视", "腾讯视频", "芒果tv"};
    private Context context;
    PageFragment pageFragment;
    List<ViedeoTitle> titles=new ArrayList<>();

    public MyFragmentPagerAdapter(FragmentManager fm, Context context, List<ViedeoTitle> titles) {
        super(fm);
        this.context = context;
        this.titles.addAll(titles);
    }

    @Override
    public Fragment getItem(int position) {
        return PageFragment.newInstance(position + 1,titles.get(position).getUrl());
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position).getName();
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        pageFragment = (PageFragment) object;
        super.setPrimaryItem(container, position, object);
    }


    public PageFragment getCurrentFragment() {
        return pageFragment;
    }
}