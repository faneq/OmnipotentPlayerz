package dog.black.com.blackdog;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fen.asunder.R;
import com.orhanobut.logger.Logger;
import com.qq.e.ads.splash.SplashAD;
import com.qq.e.ads.splash.SplashADListener;
import com.qq.e.comm.util.AdError;
import com.tencent.smtt.sdk.QbSdk;

import java.util.ArrayList;
import java.util.List;

import dog.black.com.blackdog.utils.AppShare;
import dog.black.com.blackdog.videoPage.VideoViewActivity;

/**
 * Created by feq on 2017/3/12.
 */

public class GuideActivity extends Activity implements SplashADListener {
    private SplashAD splashAD;
    private ViewGroup container;
    private TextView skipView;
    //    private ImageView splashHolder;
    public final static String APPID = "1105400838", SplashPosID = "9050317172966673", DialogAdsId = "5050927857597777", Banner = "7010410183895827";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 23) {
            checkAndRequestPermission();
            return;
        }
        initView();
    }

    private void initView() {
        initX5();
        if (AppShare.getInstence(App.getInstance()).getBoolean("isFirst", true)) {
            setContentView(R.layout.activity_guide);
            findViewById(R.id.bt_next).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppShare.getInstence(App.getInstance()).putBooleanValue("isFirst", false);
                                        goMain();
                }
            });
        } else {
            setContentView(R.layout.activity_splash);
            container = (ViewGroup) this.findViewById(R.id.splash_container);
            skipView = (TextView) findViewById(R.id.skip_view);
            fetchSplashAD(this, container, skipView, APPID, SplashPosID, this, 0);
//            goMain();
        }
    }

    private void goMain() {
        Intent intent = new Intent();
        intent.setClass(GuideActivity.this, VideoViewActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 初始化h5内核
     */
    private void initX5() {
        QbSdk.initX5Environment(getApplication(), new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {
                Logger.i("x5内核onCoreInitFinished");
            }

            @Override
            public void onViewInitFinished(boolean b) {
                Logger.i("x5内核初onCoreInitFinished");
            }
        });
    }

    @Override
    public void onADDismissed() {
        Log.i("AD_DEMO", "SplashADDismissed");
        next();
    }

    @Override
    public void onNoAD(AdError adError) {
        Log.i("AD_DEMO", String.format("LoadSplashADFail, eCode=%d, errorMsg=%s", adError.getErrorCode(), adError.getErrorMsg()));
        /** 如果加载广告失败，则直接跳转 */
        this.startActivity(new Intent(this, VideoViewActivity.class));
        this.finish();
    }

    @Override
    public void onADPresent() {
        Log.i("AD_DEMO", "SplashADPresent");
        //        splashHolder.setVisibility(View.INVISIBLE); // 广告展示后一定要把预设的开屏图片隐藏起来
    }

    @Override
    public void onADClicked() {

    }

    /**
     * 倒计时回调，返回广告还将被展示的剩余时间。
     * 通过这个接口，开发者可以自行决定是否显示倒计时提示，或者还剩几秒的时候显示倒计时
     *
     * @param millisUntilFinished 剩余毫秒数
     */
    @Override
    public void onADTick(long millisUntilFinished) {
        Log.i("AD_DEMO", "SplashADTick " + millisUntilFinished + "ms");
        skipView.setText(String.format("剩余%d秒", Math.round(millisUntilFinished / 1000f)));
    }

    private void next() {
        this.startActivity(new Intent(this, VideoViewActivity.class));
        //防止用户回退看到此页面
        this.finish();
    }

    private void fetchSplashAD(Activity activity, ViewGroup adContainer, View skipContainer,
                               String appId, String posId, SplashADListener adListener, int fetchDelay) {
        splashAD = new SplashAD(activity, adContainer, skipContainer, appId, posId, adListener, fetchDelay);
    }


    //防止用户返回键退出APP
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @TargetApi(Build.VERSION_CODES.M)
    private void checkAndRequestPermission() {
        List<String> lackedPermission = new ArrayList<String>();
        if (!(checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED)) {
            lackedPermission.add(Manifest.permission.READ_PHONE_STATE);
        }

        if (!(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
            lackedPermission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (!(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
            lackedPermission.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        // 权限都已经有了，那么直接调用SDK
        if (lackedPermission.size() == 0) {
            initView();
            //            fetchSplashAD(this, container, skipView, APPID, SplashPosID, this, 0);
        } else {
            // 请求所缺少的权限，在onRequestPermissionsResult中再看是否获得权限，如果获得权限就可以调用SDK，否则不要调用SDK。
            String[] requestPermissions = new String[lackedPermission.size()];
            lackedPermission.toArray(requestPermissions);
            requestPermissions(requestPermissions, 1024);
        }
    }

    private boolean hasAllPermissionsGranted(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1024 && hasAllPermissionsGranted(grantResults)) {
            //            fetchSplashAD(this, container, skipView, APPID, SplashPosID, this, 0);
            initView();
        } else {
            // 如果用户没有授权，那么应该说明意图，引导用户去设置里面授权。
            Toast.makeText(this, "应用缺少必要的权限！请点击\"权限\"，打开所需要的权限。", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + getPackageName()));
            startActivity(intent);
            finish();
        }
    }

}
