package dog.black.com.blackdog;

import android.app.Application;

import com.orhanobut.logger.Logger;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;

/**
 * Created by feq on 2017/2/18.
 */

public class App extends Application {
    private static App instance;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initLog();
        initUmPush();
        //必须调用初始化
        //        OkGo.init(this);
    }

    private void initUmPush() {
        PushAgent mPushAgent = PushAgent.getInstance(this);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
            }

            @Override
            public void onFailure(String s, String s1) {

            }
        });


    }

    private void initLog() {
        Logger.init("BlackDog");
    }


}
