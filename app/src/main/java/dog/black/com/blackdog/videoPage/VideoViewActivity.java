package dog.black.com.blackdog.videoPage;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.fen.asunder.R;
import com.orhanobut.logger.Logger;
import com.qq.e.ads.banner.ADSize;
import com.qq.e.ads.banner.AbstractBannerADListener;
import com.qq.e.ads.banner.BannerView;
import com.qq.e.ads.interstitial.AbstractInterstitialADListener;
import com.qq.e.ads.interstitial.InterstitialAD;
import com.qq.e.comm.util.AdError;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.helper.GsonUtil;
import cn.bmob.v3.listener.BmobUpdateListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.update.BmobUpdateAgent;
import cn.bmob.v3.update.UpdateResponse;
import dog.black.com.blackdog.App;
import dog.black.com.blackdog.GuideActivity;
import dog.black.com.blackdog.mainView.adapter.ChangeAdapter;
import dog.black.com.blackdog.mainView.bean.ShareInfo;
import dog.black.com.blackdog.utils.AndroidUtil;
import dog.black.com.blackdog.utils.AppShare;
import dog.black.com.blackdog.utils.PhoneInfo;
import dog.black.com.blackdog.utils.ShareUtils;
import dog.black.com.blackdog.videoPage.bean.AdsUrlsEntity;
import dog.black.com.blackdog.videoPage.bean.ConstantsBean;
import dog.black.com.blackdog.videoPage.bean.PhoneInfoEntity;
import dog.black.com.blackdog.videoPage.bean.PlayUrlEntity;
import dog.black.com.blackdog.videoPage.bean.UpdataPhoneInfo;
import dog.black.com.blackdog.videoPage.bean.ViedeoTitle;
import dog.black.com.blackdog.widget.CustomScrollViewPager;

/**
 * Created by feq on 2017/2/25.
 */

public class VideoViewActivity extends AppCompatActivity implements View.OnClickListener {
    private MyFragmentPagerAdapter adapter;
    private PageFragment mCurrentFragment;
    private ChangeAdapter cgAdapter;
    private Button bt_back;
    private EditText mEet_name;
    private EditText mEt_url;
    private List<PlayUrlEntity> mDatas;
    private List<ViedeoTitle> mDatasTitle = new ArrayList<>();
    private TextView mTv_curr_line;
    private BaseDialog mUpdateDialog;
    private BaseDialog questionDialog;
    private TextView mConfirmBtn, mCancelBtn;
    private long firstTime = 0;
    private MyHandler handler = new MyHandler(this);
    private final static int START_CODE = 2000;
    private RelativeLayout bannerAds;
    private CustomScrollViewPager mViewPager;
    private String shareUrl;
    public static List<AdsUrlsEntity> adsUrls;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.bg));//设置状态栏背景色
        setContentView(R.layout.video_activity);
        bannerAds = (RelativeLayout) findViewById(R.id.banner_ads);
        findViewById(R.id.bt_question).setOnClickListener(this);
        findViewById(R.id.share).setOnClickListener(this);
        findViewById(R.id.fab).setOnClickListener(this);
        findViewById(R.id.fab1).setOnClickListener(this);
        PushAgent.getInstance(this).onAppStart();
        //第一：默认初始化
        Bmob.initialize(this, "a209a5bf05f24481f85b1778be4b6a4d");
        //        BmobUpdateAgent.initAppVersion();
        //Fragment+ViewPager+FragmentViewPager组合的使用
        //        getVisionInfo();
        getvideoTitle();
        getListVideoInf();
        BmobUpdateAgent.update(this);
        BmobUpdateAgent.setUpdateListener(new BmobUpdateListener() {
            @Override
            public void onUpdateReturned(int i, UpdateResponse updateResponse) {
                Logger.i(i + " 更新监测成功");
            }
        });
        BmobUpdateAgent.setUpdateOnlyWifi(false);
        initAds();
        initAdsUrls();
        getShareInfo();
        addPhoneInfo();
        initQustionDialog();
    }

    private void initQustionDialog() {
        if (AppShare.getInstence(App.getInstance()).getBoolean("isFirst", true)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("使用教程");
            builder.setMessage("请查看教程后使用");
            builder.setPositiveButton("我知道了", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    MobclickAgent.onEvent(App.getInstance(), "00002");
                    mViewPager.setCurrentItem(mDatasTitle.size() - 1);
                }
            });
            builder.setCancelable(false);
            builder.show();
        }
        AppShare.getInstence(App.getInstance()).putBooleanValue("isFirst", false);
    }

    private void initAds() {
        handler.sendEmptyMessage(START_CODE);
        BannerView banner = new BannerView(this, ADSize.BANNER, GuideActivity.APPID, GuideActivity.Banner);
        //设置广告轮播时间，为0或30~120之间的数字，单位为s,0标识不自动轮播
        banner.setRefresh(30);
        banner.setADListener(new AbstractBannerADListener() {

            @Override
            public void onNoAD(AdError error) {
                Log.i("AD_DEMO", "BannerNoAD，eCode=" + error.getErrorCode());
            }

            @Override
            public void onADReceiv() {
                Log.i("AD_DEMO", "ONBannerReceive");
            }
        });
        /* 发起广告请求，收到广告数据后会展示数据   */
        banner.loadAD();
        bannerAds.addView(banner);
    }

    private void initLineData(String name, String url) {
        if (AppShare.getInstence(getApplication()).getString("lineName", "").equals("")) {
            AppShare.getInstence(getApplication()).putStringValue("lineName", name);
            AppShare.getInstence(getApplication()).putStringValue("lineUrl", url);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                mCurrentFragment = adapter.getCurrentFragment();
                mCurrentFragment.play();
                break;
            case R.id.fab1:
                MobclickAgent.onEvent(App.getInstance(), "00004");
                showPop();
                break;
            case R.id.bt_play:
                mCurrentFragment = adapter.getCurrentFragment();
                mCurrentFragment.play();
                MobclickAgent.onEvent(App.getInstance(), "00003");
                break;
            case R.id.bt_back:
                mCurrentFragment = adapter.getCurrentFragment();
                mCurrentFragment.back();
                break;
            case R.id.bt_go_forward:
                mCurrentFragment = adapter.getCurrentFragment();
                mCurrentFragment.goForward();
                break;
            case R.id.bt_change:
                MobclickAgent.onEvent(App.getInstance(), "00004");
                showPop();
                break;
            case R.id.bt_add:
                addInterface();
                break;
            case R.id.bt_question:
                MobclickAgent.onEvent(App.getInstance(), "00002");
                mViewPager.setCurrentItem(mDatasTitle.size() - 1);
                break;
            case R.id.share:
                ShareUtils.shareGirl("全网vip视频播放器:复制浏览器打开\n"
                        , shareUrl, this);
                MobclickAgent.onEvent(App.getInstance(), "00001");
                break;
        }
        if (mUpdateDialog != null) {
            mUpdateDialog.dismiss();
            finish();
            return;
        }
    }

    private void showPop() {
        //返回上一个页面，防止在播放页面无法播放，切换线路，播放地址变化
        mCurrentFragment = adapter.getCurrentFragment();
        mCurrentFragment.back();


        View view = View.inflate(getApplication(), R.layout.change_layout, null);
        view.findViewById(R.id.bt_add).setOnClickListener(this);
        mEet_name = (EditText) view.findViewById(R.id.et_name);
        mEt_url = (EditText) view.findViewById(R.id.et_url);
        final PopupWindow popupWindow = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT, 700, true);
        RecyclerView rc = (RecyclerView) view.findViewById(R.id.rc_change);
        rc.setLayoutManager(new LinearLayoutManager(getApplication()));
        rc.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        mDatas = new ArrayList<>();
        cgAdapter = new ChangeAdapter(R.layout.item, mDatas);
        rc.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                String url = mDatas.get(position).getUrl();
                String name = mDatas.get(position).getName();
                AppShare.getInstence(getApplication()).putStringValue("lineName", "当前线路:" + name);
                AppShare.getInstence(getApplication()).putStringValue("lineUrl", url);
                mTv_curr_line.setText("当前线路:" + name);
                Toast.makeText(getApplicationContext(), "切换成功", Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
            }
        });
        mTv_curr_line = (TextView) view.findViewById(R.id.tv_curr_line);
        rc.setAdapter(cgAdapter);
        popupWindow.setContentView(view);
        // 设置动画效果
        popupWindow.setAnimationStyle(R.style.AnimationFade);
        popupWindow.showAtLocation(bt_back, Gravity.BOTTOM, 0, -120);
        initData();
    }

    private void initData() {

        mTv_curr_line.setText(
                AppShare.getInstence(getApplication()).getString("lineName", "当前线路:"));
        getListVideoInf();
    }

    public void addInterface() {
        PlayUrlEntity p2 = new PlayUrlEntity();
        p2.setName(mEet_name.getText().toString());
        p2.setUrl(mEt_url.getText().toString());
        //        p2.setUrl("http://youdushipin.com/vip.php?url=");
        p2.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
                    Toast.makeText(getApplication(), "添加成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplication(), "添加失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public  void addPhoneInfo(){
        if (AppShare.getInstence(App.getInstance()).getBoolean("isFirst", true)) {
            GetPhoneInfoUtils getPhoneInfoUtils  = new GetPhoneInfoUtils();
            String infos = GsonUtil.toJson(getPhoneInfoUtils.getPhoneInfo(this));
            UpdataPhoneInfo phoneInfo = new UpdataPhoneInfo();
            phoneInfo.setPhoneInfos(infos);
            phoneInfo.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (e == null) {
//                    Toast.makeText(getApplication(), "添加成功", Toast.LENGTH_SHORT).show();
                    } else {
//                    Toast.makeText(getApplication(), "添加失败"+e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    private void getListVideoInf() {
        BmobQuery<PlayUrlEntity> query = new BmobQuery<>();
        //查询playerName叫“比目”的数据
        //        query.addWhereEqualTo("playerName", "比目");
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(20);
        //        query.setSkip(counts); // 忽略前10条数据（即第一页数据结果）
        //执行查询方法
        query.findObjects(new FindListener<PlayUrlEntity>() {
            @Override
            public void done(List<PlayUrlEntity> object, BmobException e) {
                if (e == null) {
                    if (object.size() != 0)
                        initLineData(object.get(0).getName(), object.get(0).getUrl());
                    mDatas.addAll(object);
                    cgAdapter.notifyDataSetChanged();
                } else {
                    Logger.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    public void addxxInterface() {
        ViedeoTitle p2 = new ViedeoTitle();
        p2.setName("1");
        p2.setUrl("1");
        //        p2.setUrl("http://youdushipin.com/vip.php?url=");
        p2.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
                    Toast.makeText(getApplication(), "添加成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplication(), "添加失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void getvideoTitle() {
        BmobQuery<ViedeoTitle> query = new BmobQuery<>();
        //查询playerName叫“比目”的数据
        //        query.addWhereEqualTo("playerName", "比目");
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(20);
        //        query.setSkip(counts); // 忽略前10条数据（即第一页数据结果）
        //执行查询方法
        query.findObjects(new FindListener<ViedeoTitle>() {
            @Override
            public void done(List<ViedeoTitle> object, BmobException e) {
                if (e == null) {
                    mDatasTitle.addAll(object);
                    setOtherView();
                } else {
                    Logger.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    private void setOtherView() {
        mViewPager = (CustomScrollViewPager) findViewById(R.id.viewPager);
        mViewPager.setOffscreenPageLimit(4);
        adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), this, mDatasTitle);
        mViewPager.setAdapter(adapter);

        //TabLayout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(mViewPager);

        bt_back = (Button) findViewById(R.id.bt_back);
        bt_back.setOnClickListener(this);
        findViewById(R.id.bt_play).setOnClickListener(this);
        findViewById(R.id.bt_go_forward).setOnClickListener(this);
        findViewById(R.id.bt_change).setOnClickListener(this);
    }

    /**
     * 获取版本信息
     */
    private void getVisionInfo() {
        BmobQuery<ConstantsBean> query = new BmobQuery<>();
        query.setLimit(1);
        query.findObjects(new FindListener<ConstantsBean>() {
            @Override
            public void done(List<ConstantsBean> object, BmobException e) {
                if (e == null && object.size() != 0) {
                    if (object.get(0).getVision() > AndroidUtil.getVerCode(App.getInstance())) {
                        Toast.makeText(App.getInstance(), "发现新版本", Toast.LENGTH_SHORT).show();
                        initDialogView("", object.get(0).getVision() + "", "新版本");
                    }
                } else {
                    Log.i("", "查询失败-----");
                    Logger.i("查询失败------");
                }
            }
        });
    }

    /**
     * 初始化对话框及其内部点击按钮
     */
    private void initDialogView(String appName, String versionName, String updateInfo) {
        if (null == mUpdateDialog) {
            mUpdateDialog = new BaseDialog(this);
        }
        if (null == mConfirmBtn) {
            mConfirmBtn = new TextView(this);
        }
        if (null == mCancelBtn) {
            mCancelBtn = new TextView(this);
        }
        mUpdateDialog.show();
        mUpdateDialog.hasTitle(true);
        mUpdateDialog.setTitleText("版本升级");

        mConfirmBtn.setText("确认");
        mConfirmBtn.setOnClickListener(this);
        mCancelBtn.setText("取消");
        mCancelBtn.setOnClickListener(this);
        mUpdateDialog.setMsgText(appName + versionName + "\n" + updateInfo);
        mUpdateDialog.setChooseDialog(new TextView[]{mCancelBtn, mConfirmBtn});
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            mCurrentFragment = adapter.getCurrentFragment();
            if (!mCurrentFragment.back()) {
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) {
                    Toast.makeText(this, "再点一次退出", Toast.LENGTH_SHORT).show();
                    firstTime = secondTime;
                    return true;
                } else {
                    Intent intent = new Intent();
                    intent.setAction("finishApp");
                    sendBroadcast(intent);
                    finish();
                }
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void dialogAds() {
        final InterstitialAD iad = new InterstitialAD(this, GuideActivity.APPID, GuideActivity.DialogAdsId);
        iad.setADListener(new AbstractInterstitialADListener() {
            @Override
            public void onADReceive() {
                iad.show();
            }

            @Override
            public void onNoAD(AdError error) {
                Log.i("AD_DEMO", String.format("LoadInterstitialAd Fail, error code: %d, error msg: %s", error.getErrorCode(), error.getErrorMsg()));
            }
        });

        //请求插屏广告，每次重新请求都可以调用此方法。
        iad.loadAD();
    }

    public static class MyHandler extends Handler {
        private final WeakReference<VideoViewActivity> mActivity;

        public MyHandler(Activity activity) {
            super();
            mActivity = new WeakReference<VideoViewActivity>((VideoViewActivity) activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mActivity.get().dialogAds();
            sendEmptyMessageDelayed(START_CODE, 1000 * 60 * 15);
        }
    }

    public void addApk() {
        ShareInfo p2 = new ShareInfo();
        p2.setUrl("");
        p2.setVision("1");
        //        p2.setUrl("http://youdushipin.com/vip.php?url=");
        p2.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
                    Toast.makeText(getApplication(), "添加成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplication(), "添加失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getShareInfo() {
        BmobQuery<ShareInfo> query = new BmobQuery<>();
        //查询playerName叫“比目”的数据
        //        query.addWhereEqualTo("playerName", "比目");
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(1);
        //        query.setSkip(counts); // 忽略前10条数据（即第一页数据结果）
        //执行查询方法
        query.findObjects(new FindListener<ShareInfo>() {
            @Override
            public void done(List<ShareInfo> object, BmobException e) {
                if (e == null) {
                    shareUrl = object.get(0).getUrl();
                    setOtherView();
                } else {
                    Logger.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    private void initAdsUrls() {
        BmobQuery<AdsUrlsEntity> query = new BmobQuery<>();
        //执行查询方法
        query.findObjects(new FindListener<AdsUrlsEntity>() {
            @Override
            public void done(List<AdsUrlsEntity> object, BmobException e) {
                if (e == null) {
                    adsUrls = object;
                } else {
                    Logger.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

}

