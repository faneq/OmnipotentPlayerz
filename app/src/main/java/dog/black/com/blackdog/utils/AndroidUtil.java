package dog.black.com.blackdog.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Field;
import java.util.regex.Pattern;

public class AndroidUtil {
    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @param fontScale （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(float pxValue, float fontScale) {
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @param fontScale （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(float spValue, float fontScale) {
        return (int) (spValue * fontScale + 0.5f);
    }

    public static void dismissLoading(Dialog loadingDialog) {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    /**
     * 解决Android2.2版本之前的httpconnection连接的bug
     */
    public static void disableConnectionReuseIfNecessary() {
        if (hasHttpConnectionBug()) {
            System.setProperty("http.keepAlive", "false");
        }
    }

    public static boolean hasHttpConnectionBug() {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.FROYO;
    }

    /**
     * 获取网络状态
     */
    public static boolean getNetState(Context context) {
        ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        // mobile 3G Data Network
        State mobile = conMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
        State wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();

        if (mobile == State.CONNECTED || mobile == State.CONNECTING)
            return true;
        if (wifi == State.CONNECTED || wifi == State.CONNECTING)
            return true;
        return false;
    }

    /**
     * sdcard是否可读写
     *
     * @return
     */
    public static boolean isSdcardReady() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * @return
     */
    public static boolean isSdcardAvailable() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File sdcardDir = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(sdcardDir.getPath());
            long availCount = sf.getAvailableBlocks();
            long blockSize = sf.getBlockSize();
            long availSize = availCount * blockSize / 1024;

            if (availSize >= 3072) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * 获取sd卡剩余大小
     */
    public static long getAvailaleSize() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File sdcardDir = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(sdcardDir.getPath());
            long availCount = sf.getAvailableBlocks();
            long blockSize = sf.getBlockSize();
            long availSize = availCount * blockSize;
            return availSize;
        }
        return 0;

    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 在某个Activity中隐藏输入法
     *
     * @param context
     */
    public static void hideIME(Activity context) {
        if (context == null) {
            return;
        }
        try {
            ((InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                    context.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }
    }

    /**
     * 获取版本号
     */
    public static int getVerCode(Context context) {
        int verCode = -1;
        try {
            verCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return verCode;
    }

    /**
     * 获取版本名称
     */
    public static String getVerName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

        return verName;
    }

    /**
     * 获取android系统版本号
     */
    public static String getAndroidVerCode(Context context) {
        String androidVerCode = "";
        try {
            androidVerCode = android.os.Build.VERSION.RELEASE;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return androidVerCode;
    }

    /**
     * 显示键盘
     *
     * @param view
     */
    public static void showKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        imm.showSoftInput(view, 0);
    }

    /**
     * 隐藏键盘
     */
    public static void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private static long lastClickTime;

    /**
     * 判断用户连续点击按钮间隔
     *
     * @return
     */
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 1000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /* Toast 所需常量 */
    private static String oldMSg;
    private static Toast mToast;
    private static long oldTime;
    private static long newTime;

    private static int i = 0;

    /**
     * 获取imsi
     */
    public static String getImsi(Context context) {
        TelephonyManager mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String imsi = mTelephonyMgr.getSubscriberId();
        return imsi;
    }

    /**
     * 获取imei
     */
    public static String getImei(Context context) {
        TelephonyManager mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = mTelephonyMgr.getDeviceId();
        return imei;
    }

    /**
     * 判断SIM卡是否存在
     *
     * @param context
     * @return
     */
    public static boolean isSimState(Context context) {
        TelephonyManager mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        int simState = mTelephonyMgr.getSimState();
        if (simState == TelephonyManager.SIM_STATE_ABSENT || simState == TelephonyManager.SIM_STATE_UNKNOWN) {
            return false;
        }
        return true;
    }

    /**
     * 判断是否为手机号，方法倒数第11位为1
     *
     * @param number 号码
     * @return
     */
    public static boolean isPhoneNumb(String number) {
        if (number.length() < 11) {
            return false;
        }
        int position = number.length() - 11;
        if (number.charAt(position) == '1') {
            return true;
        }
        return false;
    }

    /**
     * 获取电话号码
     */
    public static String getTel(Context context) {
        TelephonyManager mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String tel = mTelephonyMgr.getLine1Number();
        return tel;
    }

//    /**
//     * 发送短信
//     *
//     * @param number
//     * @param content
//     * @return返回是否发送成功
//     */
//    public static boolean sendSMS(String number, String content) {
//        if (!StringUtil.isEmpty(number) && !StringUtil.isEmpty(content)) {
//            SmsManager smsManager = SmsManager.getDefault();
//            smsManager.sendTextMessage(number, null, content, null, null);
//            return true;
//        }
//        return false;
//    }

    public static void ToSendSms(Context context, String number, String content) {
        Uri smsUri = Uri.parse("smsto:" + number);
        Intent smsIntent = new Intent(Intent.ACTION_SENDTO, smsUri);
        smsIntent.putExtra("sms_body", content);
        context.startActivity(smsIntent);
    }

    /**
     * 开启GPU加速
     *
     * @param window
     */
    public static void openGPU(Window window) {
        try {
            // 反射出来硬件加速参数，兼容2.3版本
            Field field = WindowManager.LayoutParams.class.getField("FLAG_HARDWARE_ACCELERATED");
            Field field2 = WindowManager.LayoutParams.class.getField("FLAG_HARDWARE_ACCELERATED");
            if (field != null && field2 != null) {
                window.setFlags(field.getInt(null), field2.getInt(null));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取内核数
     *
     * @return
     */
    public static int getNumCores() {
        // Private Class to display only CPU devices in the directory listing
        class CpuFilter implements FileFilter {
            @Override
            public boolean accept(File pathname) {
                // Check if filename is "cpu", followed by a single digit number
                if (Pattern.matches("cpu[0-9]", pathname.getName())) {
                    return true;
                }
                return false;
            }
        }

        try {
            // Get directory containing CPU info
            File dir = new File("/sys/devices/system/cpu/");
            // Filter to only list the devices we care about
            File[] files = dir.listFiles(new CpuFilter());
            // Return the number of cores (virtual CPU devices)
            return files.length;
        } catch (Exception e) {
            e.printStackTrace();
            // Default to return 1 core
            return 1;
        }
    }

    /**
     * 判断当前网络是否连接
     *
     * @return
     */
    public static boolean isNetworkConnection(Context context) {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null && info.isConnected()) {
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public static void goXActivity() {
        System.exit(0);
    }

//    /**
//     * 判断当前网络类型，可以判断是2G的网络，3G网络还是WIFI网络
//     * 返回int类型的数据  1-wifi， 2-2G， 3-3G
//     *
//     * @param context
//     * @return
//     */
//    public static int getNetworkType(Context context) {
//        ConnectivityManager connectMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo info = connectMgr.getActiveNetworkInfo();
//        if (info == null) {
////			throw new NullPointerException();
//            return 0;
//        }
//
//        if (ConnectivityManager.TYPE_WIFI == info.getType()) {
//            return AppConfig.NETWORK_TYPE_WIFI;
//        } else {
//            int subType = info.getSubtype();
//            switch (subType) {
//                case TelephonyManager.NETWORK_TYPE_CDMA:
//                case TelephonyManager.NETWORK_TYPE_GPRS:
//                case TelephonyManager.NETWORK_TYPE_EDGE:
//                    return AppConfig.NETWORK_TYPE_2G;
//                default:
//                    return AppConfig.NETWORK_TYPE_3G;
//            }
//        }
//    }

    public static int getWidth(Context mContext) {
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        if (wm != null)
            return wm.getDefaultDisplay().getWidth();
        return 0;
    }

    public static long getNifo() {
        return System.currentTimeMillis();
    }

    public static int getHeight(Context mContext) {
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        if (wm != null)
            return wm.getDefaultDisplay().getHeight();
        return 0;
    }

}
