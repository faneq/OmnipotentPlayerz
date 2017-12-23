package dog.black.com.blackdog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.fen.asunder.R;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * Created by feq on 2017/2/19.
 */

public class Test extends AppCompatActivity {

    private WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        webview = (WebView) findViewById(R.id.web_view);
        webview.getSettings().setJavaScriptEnabled(true);
        // 设置web视图客户端
        MyWebViewClient myWebViewClient = new MyWebViewClient();
        webview.setWebViewClient(myWebViewClient);
        webview.loadUrl("http://www.iqiyi.com/v_19rr9tre6k.html?vfm=f_178_2345&fv=p_07_01");
    }

    // web视图客户端
    public class MyWebViewClient extends WebViewClient {
        public boolean shouldOverviewUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

    }
}
