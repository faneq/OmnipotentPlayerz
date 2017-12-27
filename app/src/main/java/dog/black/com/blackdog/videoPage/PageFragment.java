package dog.black.com.blackdog.videoPage;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fen.asunder.R;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.util.ArrayList;

import dog.black.com.blackdog.utils.AppShare;
import dog.black.com.blackdog.videoPage.bean.AdsUrlsEntity;

import static android.R.attr.name;

/**
 * Created by feq on 2017/2/25.
 */

public class PageFragment extends Fragment {
    public static final String ARGS_PAGE = "args_page";
    public static final String ARGS_URL = "args_url";
    private int mPage;
    public com.tencent.smtt.sdk.WebView mWebView;
    private static final String APP_CACAHE_DIRNAME = "/webcache";
    private TextView textView;
    private TextView textView1;
    private ArrayList<String> loadHistoryUrls = new ArrayList<String>();
    private ProgressBar mProgressBar;
    private String url;

    public static PageFragment newInstance(int page, String webUrl) {
        Bundle args = new Bundle();
        args.putInt(ARGS_PAGE, page);
        args.putString(ARGS_URL, webUrl);
        PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARGS_PAGE);
        url = getArguments().getString(ARGS_URL);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.video_pager, container, false);
        mWebView = new com.tencent.smtt.sdk.WebView(getActivity());
        ((FrameLayout) view.findViewById(R.id.web_view)).addView(mWebView);
        textView1 = (TextView) view.findViewById(R.id.textView);
        mProgressBar = (ProgressBar) view.findViewById(R.id.myProgressBar);
        initView();
        textView1.setText("第" + mPage + "页");
        return view;
    }

    private void initView() {
        WebSettings webSettings = mWebView.getSettings();
        setInitSetting();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setDomStorageEnabled(true);//这句话必须保留。。否则无法播放优酷视频网页。。其他的可以
        webSettings.setDefaultTextEncodingName("utf-8");//这句话去掉也没事。。只是设置了编码格式
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setPluginsEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setSavePassword(true);
        webSettings.setSaveFormData(true);// 保存表单数据
        webSettings.setSupportMultipleWindows(true);// 新加
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView webView, String s) {
                if (!ADFilterTool.hasAd(getContext(), s)) {
                    return super.shouldInterceptRequest(webView, url);//正常加载
                } else {
                    return new WebResourceResponse(null, null, null);//含有广告资源屏蔽请求
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                loadHistoryUrls.add(s);
                if (url.startsWith("http:") || url.startsWith("https:")) {
                    return false;
                }
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } catch (Exception e) {
                }
                return true;
            }
        });
        //http/https混合
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webSettings.setMixedContentMode(WebSettings.LOAD_NORMAL);
        }
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView webView, int newProgress) {
                super.onProgressChanged(webView, newProgress);
                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.INVISIBLE);
                } else {
                    if (View.INVISIBLE == mProgressBar.getVisibility()) {
                        mProgressBar.setVisibility(View.VISIBLE);
                    }
                    mProgressBar.setProgress(newProgress);
                }
            }
        });
        mWebView.loadUrl(url);
        //        loadHistoryUrls.add(Constans.PAGEVIEW[mPage - 1]);
        String cacheDirPath = getActivity().getFilesDir().getAbsolutePath() + APP_CACAHE_DIRNAME; //缓存路径
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);  //缓存模式
        mWebView.getSettings().setAppCachePath(cacheDirPath); //设置缓存路径
        mWebView.getSettings().setAppCacheEnabled(true); //开启缓存功能
        mWebView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {

                if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {  //表示按返回键
                    mWebView.goBack();   //后退
                    return true;    //已处理
                }
                return false;
            }
        });
    }

    public void play() {
        Toast.makeText(getActivity(), "视频加载中，请稍等...", Toast.LENGTH_SHORT).show();
        String x = mWebView.getUrl();
        String lineName = AppShare.getInstence(getContext()).getString("lineName", "当前线路:" + name);
        String lineUrl = AppShare.getInstence(getContext()).getString("lineUrl", "当前线路:" + name);
        mWebView.loadUrl(lineUrl + x);
    }

    public boolean back() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return false;
    }

    public void goForward() {
        if (mWebView.canGoForward()) {
            mWebView.goForward();
        }
    }

    private void setInitSetting() {
        Bundle data = new Bundle();

        data.putBoolean("standardFullScreen", false);
        //true表示标准全屏，false表示X5全屏；不设置默认false，

        data.putBoolean("supportLiteWnd", false);
        //false：关闭小窗；true：开启小窗；不设置默认true，

        data.putInt("DefaultVideoScreen", 2);
        //1：以页面内开始播放，2：以全屏开始播放；不设置默认：1

        mWebView.getX5WebViewExtension().invokeMiscMethod("setVideoParams", data);
    }

}

class ADFilterTool {
    public static boolean hasAd(Context context, String url) {
        if (VideoViewActivity.adsUrls != null && VideoViewActivity.adsUrls.size() != 0) {
            for (AdsUrlsEntity adUrl : VideoViewActivity.adsUrls) {
                if (url.contains(adUrl.getUrls())) {
                    return true;
                }
            }
        } else {
            Resources res = context.getResources();
            String[] adUrls = res.getStringArray(R.array.adBlockUrl);
            for (String adUrl : adUrls) {
                if (url.contains(adUrl)) {
                    return true;
                }
            }
        }
        return false;
    }
}