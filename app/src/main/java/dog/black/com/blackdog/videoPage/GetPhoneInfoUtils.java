package dog.black.com.blackdog.videoPage;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Display;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import dog.black.com.blackdog.utils.NetWorkUtil;
import dog.black.com.blackdog.utils.PhoneInfo;
import dog.black.com.blackdog.videoPage.bean.PhoneInfoEntity;

import static cn.bmob.v3.Bmob.getApplicationContext;

public class GetPhoneInfoUtils {
    private PhoneInfoEntity phoneInfoEntity;
    @RequiresApi(api = Build.VERSION_CODES.ECLAIR)
    public PhoneInfoEntity getPhoneInfo(Activity context) {
        phoneInfoEntity =new PhoneInfoEntity();
        phoneInfoEntity.setDISPLAY(Build.DISPLAY);
        phoneInfoEntity.setCPU_ABI(Build.CPU_ABI);
        phoneInfoEntity.setCPU_ABI2(Build.CPU_ABI2);
        phoneInfoEntity.setMANUFACTURER(Build.MANUFACTURER);
        phoneInfoEntity.setBRAND(Build.BRAND);
        phoneInfoEntity.setHARDWARE(Build.HARDWARE);
        phoneInfoEntity.setPRODUCT(Build.PRODUCT);
        phoneInfoEntity.setFINGERPRINT(Build.FINGERPRINT);
        phoneInfoEntity.setGetRadioVersion(Build.getRadioVersion());
        phoneInfoEntity.setRADIO(Build.RADIO);
        phoneInfoEntity.setBOARD(Build.BOARD);
        phoneInfoEntity.setDEVICE(Build.DEVICE);
        phoneInfoEntity.setID(Build.ID);
        phoneInfoEntity.setMODEL(Build.MODEL);
        phoneInfoEntity.setBOOTLOADER(Build.BOOTLOADER);
        phoneInfoEntity.setHOST(Build.HOST);
        phoneInfoEntity.setTAGS(Build.TAGS);
        phoneInfoEntity.setTYPE(Build.TYPE);
        phoneInfoEntity.setINCREMENTAL(Build.VERSION.INCREMENTAL);
        phoneInfoEntity.setRELEASE(Build.VERSION.RELEASE);
        phoneInfoEntity.setSDK_INT(String.valueOf(Build.VERSION.SDK_INT));
        phoneInfoEntity.setTIME(String.valueOf(Build.TIME));
        phoneInfoEntity.setANDROID_ID(Settings.Secure.getString(context.getContentResolver(),Settings.Secure.ANDROID_ID));
        try {
            Class<?> classSysProp = Class
                    .forName("android.os.SystemProperties");
            Method method2 = classSysProp.getDeclaredMethod("get",String.class,String.class);
            Method method1 = classSysProp.getDeclaredMethod("get",String.class);
            Object obj = classSysProp.getConstructor().newInstance();
            Object one = method1.invoke(obj,"gsm.version.baseband"); //基带版本
            Object two = method1.invoke(obj,"gsm.version.baseband"); //基带版本
            Object description = method1.invoke(obj,"ro.build.description"); //基带版本
            phoneInfoEntity.setOne(String.valueOf(one));
            phoneInfoEntity.setTwo(String.valueOf(two));
            phoneInfoEntity.setDescription(String.valueOf(description));
        } catch (Exception e) {
            e.printStackTrace();
        }

        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, "android.permission.READ_PHONE_NUMBERS") != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context,new String[]{Manifest.permission.READ_SMS,"android.permission.READ_PHONE_NUMBERS",Manifest.permission.READ_PHONE_STATE},1);
        }else {
            phoneInfoEntity.setIMEI(telephonyManager.getDeviceId());
//            phoneInfoEntity.setBluetoothAddress(BluetoothAdapter.getDefaultAdapter().getAddress());
            phoneInfoEntity.setBluetoothAddress(PhoneInfo.getBluetoothMacAddress());
            try {
                Class<?> bluetooth = Class.forName("android.bluetooth.BluetoothDevice");
                Field field = bluetooth.getDeclaredField("mAddress");
                Object obj = bluetooth.getConstructor().newInstance();
                field.setAccessible(true);
                phoneInfoEntity.setBluetoothAddress(String.valueOf(field.get(obj)));
            } catch (Exception e) {
                e.printStackTrace();
            }
            WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();

            phoneInfoEntity.setGetMacAddress(PhoneInfo.getMacAddr());
            phoneInfoEntity.setGetSSID(info.getSSID());
            phoneInfoEntity.setGetNetworkInfo(PhoneInfo.getNetworkInfo());
            phoneInfoEntity.setGetNetworkInfo(PhoneInfo.getNetworkInfo());
            phoneInfoEntity.setGetBSSID(info.getBSSID());
            phoneInfoEntity.setGetLine1Number(telephonyManager.getLine1Number());
            phoneInfoEntity.setGetSimSerialNumber(telephonyManager.getSimSerialNumber());
            phoneInfoEntity.setGetNetworkOperator(telephonyManager.getNetworkOperator());
            phoneInfoEntity.setNetworkOperatorName(telephonyManager.getNetworkOperatorName());
            phoneInfoEntity.setGetSimOperator(telephonyManager.getSimOperator());
            phoneInfoEntity.setGetSimOperatorName(telephonyManager.getSimOperatorName());
            phoneInfoEntity.setNetworkCountryIso(telephonyManager.getNetworkCountryIso());
            phoneInfoEntity.setSimCountryIso(telephonyManager.getSimCountryIso());
            phoneInfoEntity.setSoftwareVersion(telephonyManager.getDeviceSoftwareVersion());
            phoneInfoEntity.setGetNetworkType(String.valueOf(telephonyManager.getNetworkType()));
            phoneInfoEntity.setGetPhoneType(String.valueOf(telephonyManager.getPhoneType()));
            phoneInfoEntity.setGetSimState(String.valueOf(telephonyManager.getSimState()));
            Display display = ((Activity)context).getWindowManager().getDefaultDisplay();
            phoneInfoEntity.setGetWidth(String.valueOf(display.getWidth()));
            phoneInfoEntity.setGetHeight(String.valueOf(display.getHeight()));
            phoneInfoEntity.setGetLocalIpAddress(String.valueOf(NetWorkUtil.getLocalIpAddress(context)));
//            deviceInfo.add("手机内网ip地址:"+NetWorkUtil.getLocalIpAddress(this));
            Resources resources=context.getResources();
            DisplayMetrics displayMetrics = resources.getDisplayMetrics();
            phoneInfoEntity.setDensityDpi(String.valueOf(displayMetrics.densityDpi));
            phoneInfoEntity.setDensity(String.valueOf(displayMetrics.density));
            phoneInfoEntity.setXdpi(String.valueOf(displayMetrics.xdpi));
            phoneInfoEntity.setYdpi(String.valueOf(displayMetrics.ydpi));
            phoneInfoEntity.setScaledDensity(String.valueOf(displayMetrics.scaledDensity));
        }
        return  phoneInfoEntity;
    }
}
