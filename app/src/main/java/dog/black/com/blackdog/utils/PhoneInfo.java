package dog.black.com.blackdog.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import dog.black.com.blackdog.App;

import static android.content.pm.PackageManager.GET_UNINSTALLED_PACKAGES;
import static android.support.v4.content.PermissionChecker.checkSelfPermission;


public class PhoneInfo {
    private static ConnectivityManager connMgr = (ConnectivityManager) App.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
    private static NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
    private static TelephonyManager telephonyManager = (TelephonyManager) App.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
    private static PackageManager pManager;

    /**
     * 获取附加信息。
     * 当用wifi上的时候
     * getType 是WIFI
     * getExtraInfo是空的
     *
     * @return
     */
    public static String getNetworkInfo() {
        if (networkInfo != null) {
            return networkInfo.getExtraInfo();
        }
        return "";
    }

    /**
     * 获取连接失败的原因。
     *
     * @return
     */
    public static String getReason() {
        if (networkInfo != null) {
            return networkInfo.getReason();
        }
        return "";
    }

    /**
     * 获取网络类型(一般为移动或Wi-Fi)。
     *
     * @return
     */
    public static String getType() {
        if (networkInfo != null) {
            return String.valueOf(networkInfo.getType());
        }
        return "";
    }

    /**
     * 获取网络类型名称(一般取值“WIFI”或“MOBILE”)。
     *
     * @return
     */
    public static String getTypeName() {
        if (networkInfo != null) {
            return networkInfo.getTypeName();
        }
        return "";
    }

    /**
     * 获取网络类型名称(一般取值“WIFI”或“MOBILE”)。
     *
     * @return
     */
    public static int getSubtype() {
        if (networkInfo != null) {
            return networkInfo.getSubtype();
        }
        return 0;
    }

    public static String getNetworkOperator() {
        return telephonyManager.getNetworkOperator();
    }

    public static String getNetworkOperatorName() {
        return telephonyManager.getNetworkOperatorName();
    }

    public static String getNetworkType() {
        return String.valueOf(telephonyManager.getNetworkType());
    }

    public static String getSimOperator() {
        return String.valueOf(telephonyManager.getSimOperator());
    }

    public static String getSimOperatorName() {
        return String.valueOf(telephonyManager.getSimOperatorName());
    }

    @SuppressLint({"MissingPermission", "HardwareIds"})
    public static String getLine1Number() {
        return String.valueOf(telephonyManager.getLine1Number());
    }

    @SuppressLint({"MissingPermission", "HardwareIds"})
    public static String getSimSerialNumber() {
        return String.valueOf(telephonyManager.getSimSerialNumber());
    }

    public static String getRadioVersion() {
        return String.valueOf(Build.getRadioVersion());
    }

    /**
     * mac地址有多种获取方式 需要适配
     *
     * @return
     */
    public static String getWifiMac() {
        WifiManager wm = (WifiManager) App.getInstance().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wi = wm.getConnectionInfo();
        if (wi == null || wi.getMacAddress() == null) {
            return null;
        }
        if ("02:00:00:00:00:00".equals(wi.getMacAddress().trim())) {
            return null;
        } else {
            return wi.getMacAddress().trim();
        }
    }

    public static String getBSSID() {
        WifiManager wm = (WifiManager) App.getInstance().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wi = wm.getConnectionInfo();
        if (wi == null) {
            return null;
        }
        return null == wi.getBSSID() ? "" : wi.getBSSID().trim();
    }

    public static String getIpAddress() {
        WifiManager wm = (WifiManager) App.getInstance().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wi = wm.getConnectionInfo();
        if (wi == null) {
            return null;
        }
        return String.valueOf(wi.getIpAddress());
    }

    public static String getNetworkId() {
        WifiManager wm = (WifiManager) App.getInstance().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wi = wm.getConnectionInfo();
        if (wi == null) {
            return null;
        }
        return String.valueOf(wi.getNetworkId());
    }

    /**
     * WiFi名称
     *
     * @return 9.0的机型，必须请求GPS权限并打开GPS才可以正确获取到WIFI名称
     */
    public static String getSSID() {
        WifiManager wm = (WifiManager) App.getInstance().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wi = wm.getConnectionInfo();
        if (wi == null) {
            return null;
        }
        return String.valueOf(wi.getSSID());
    }

    public static String getRssi() {
        WifiManager wm = (WifiManager) App.getInstance().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wi = wm.getConnectionInfo();
        if (wi == null) {
            return null;
        }
        return String.valueOf(wi.getRssi());
    }

    public static String getAndroidId() {
        return Settings.System.getString(App.getInstance().getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static String getTAGS() {
        return Build.TAGS;
    }

    public static String getHOST() {
        return Build.HOST;
    }

    public static String getUSer() {
        return Build.USER;
    }

    public static String getTIME() {
        return String.valueOf(Build.TIME);
    }

    public static String getDISPLAY() {
        return Build.DISPLAY;
    }

    public static String getBOOTLOADER() {
        return Build.BOOTLOADER;
    }

    public static String getSERIAL() {
        return Build.SERIAL;
    }

    public static String getBOARD() {
        return Build.BOARD;
    }

    public static String getBRAND() {
        return Build.BRAND;
    }

    public static String getDEVICE() {
        return Build.DEVICE;
    }

    public static String getFINGERPRINT() {
        return Build.FINGERPRINT;
    }

    public static String getHARDWARE() {
        return Build.HARDWARE;
    }

    public static String getMANUFACTURER() {
        return Build.MANUFACTURER;
    }

    public static String getTYPE() {
        return Build.TYPE;
    }

    public static String getMODEL() {
        return Build.MODEL;
    }

    public static String getPRODUCT() {
        return Build.PRODUCT;
    }

    public static String getID() {
        return Build.ID;
    }

    /**
     * Build.VERSION
     *
     * @return
     */
    public static String getRELEASE() {
        return Build.VERSION.RELEASE;
    }

    public static String getINCREMENTAL() {
        return Build.VERSION.INCREMENTAL;
    }

    public static String getCODENAME() {
        return Build.VERSION.CODENAME;
    }

    public static String getSDK() {
        return Build.VERSION.SDK;
    }

    public static String getSDK_INT() {
        return String.valueOf(Build.VERSION.SDK_INT);
    }

    /**
     * 取得IMEI SV
     * 设备的软件版本号： 返回移动终端的软件版本，例如：GSM手机的IMEI/SV码。 例如：the IMEI/SV(software version)
     * for GSM phones. Return null if the software version is not available.
     */
    public static String getDeviceSoftwareVersion() {
        if (checkSelfPermission(App.getInstance(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return null;
        }
        return telephonyManager.getDeviceSoftwareVersion();// String
    }

    public static String getTelephonyManagerLocation() {
        if (checkSelfPermission(App.getInstance(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(App.getInstance(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (telephonyManager.getPhoneType() == TelephonyManager.PHONE_TYPE_CDMA) {
                CdmaCellLocation cdmaCellLocation = (CdmaCellLocation)
                        telephonyManager.getCellLocation();
                int cid = cdmaCellLocation.getBaseStationId(); //获取cdma基站识别标号 BID
                int lac = cdmaCellLocation.getNetworkId(); //获取cdma网络编号NID
                int sid = cdmaCellLocation.getSystemId(); //用谷歌API的话cdma网络的mnc要用这个getSystemId()取得→SID
                int latitude = cdmaCellLocation.getBaseStationLatitude(); //用谷歌API的话cdma网络的mnc要用这个getSystemId()取得→SID
                int longitude = cdmaCellLocation.getBaseStationLongitude(); //用谷歌API的话cdma网络的mnc要用这个getSystemId()取得→SID
                return "cid" + cid + "----lac" + lac + "----sid" + sid + "----latitude" + latitude + "----longtitude" + longitude;
            } else {
                GsmCellLocation gsmCellLocation = (GsmCellLocation) telephonyManager.getCellLocation();
                int cid = gsmCellLocation.getCid(); //获取gsm基站识别标号
                int lac = gsmCellLocation.getLac(); //获取gsm网络编号
                int psc = gsmCellLocation.getPsc(); //获取gsm网络编号
                return "cid" + cid + "----lac" + lac + "----psc" + psc;
            }
        }
        return "";
    }

    /**
     * 获取imsi
     *
     * @return
     */
    public static String getSubscriberId() {
        if (checkSelfPermission(App.getInstance(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return null;
        }
        return String.valueOf(telephonyManager.getSubscriberId());
    }

    @SuppressLint("HardwareIds")
    public static String getBluetoothAdapter() {
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        return defaultAdapter.getAddress();
    }

//    public static String getTelephonyLocai() {
//        return telephonyManager;
//    }

    @SuppressLint("HardwareIds")
    public static String getDeviceId() {
        try {
            if (checkSelfPermission(App.getInstance(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    Activity#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return null;
            }
            if (telephonyManager.getDeviceId() == null || telephonyManager.getDeviceId().equals("")) {
                if (Build.VERSION.SDK_INT >= 23) {
                    return telephonyManager.getDeviceId(0);
                }
            } else {
                return telephonyManager.getDeviceId();
            }
        } catch (Exception e) {
        }
        return telephonyManager.getDeviceId();
    }

    //    public  static  String getLocalHost(){
//        try {
//            InetAddress localHost = InetAddress.getLocalHost();
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        }
//    }
    public static String getScanResults() {
        WifiManager wm = (WifiManager) App.getInstance().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        return wm.getScanResults().toString();
    }

    /**
     * 获取手机安装的非系统应用
     *
     * @param context
     * @return
     */
    public static List<PackageInfo> getAllApps(Context context) {
        List<PackageInfo> apps = new ArrayList<PackageInfo>();
        pManager = context.getPackageManager();
        // 获取手机内所有应用
        List<PackageInfo> paklist = pManager.getInstalledPackages(0);
        for (int i = 0; i < paklist.size(); i++) {
            PackageInfo pak = (PackageInfo) paklist.get(i);
            // 该值大于0时，表示获取的应用为系统预装的应用，反之则为手动安装的应用
            if ((pak.applicationInfo.flags & pak.applicationInfo.FLAG_SYSTEM) <= 0) {
                apps.add(pak);
            }
        }
        return apps;
    }
    /**
     * 获取手机ResolveInfo列表
     *
     * @param context
     * @return
     */

    public static List<ResolveInfo> getShareApps(Context context) {
        List<ResolveInfo> mApps = new ArrayList<ResolveInfo>();

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        PackageManager pManager = context.getPackageManager();
        mApps = pManager.queryIntentActivities(intent,
                PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);

        return mApps;
    }
    public  static List<ApplicationInfo>  getApplicationInfo(Context context){
      return   pManager.getInstalledApplications(GET_UNINSTALLED_PACKAGES);
    }
    /**
     * 清除其他应用的所有数据
     */
    public static boolean cleanOtherAppData(String packageName) {
        File file = new File("/data/data/" + packageName);
        System.out.println("包名为：" + packageName);

        if (file.exists()) {
            DataOutputStream os = null;
            Process p = null;

            try {
                p = Runtime.getRuntime().exec("su");// 获得root权限
                os = new DataOutputStream(p.getOutputStream());

                os.writeBytes("chmod 777 /data/data/" + packageName + "/ \n"); //拿到对应app包的读写执行权限
                os.flush();

                // data/data/packageName目录下要删除的文件夹
                List<String> list = new ArrayList<>();
                list.add("cache");
                list.add("databases");
                list.add("files");
                list.add("shared_prefs");
                for (int i = 0; i < list.size(); i++) {
                    os.writeBytes("rm -rif /data/data/" + packageName + "/" + list.get(i) + " \n");// 强制删除文件和文件夹
                    os.flush();
                }

                os.writeBytes("exit\n"); //退出命令
                os.flush();

                os.close();
                p.destroy();

                return true;

            } catch (IOException e) {
                try {
                    if (os != null) {
                        os.close();
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                if (p != null) {
                    p.destroy();
                }
            }
        }

        return false;
    }

    /**
     * 检测vpn
     * @return
     */
    public static boolean isVpnConnected() {
        try {
            Enumeration<NetworkInterface> niList = NetworkInterface.getNetworkInterfaces();
            if(niList != null) {
                for (NetworkInterface intf : Collections.list(niList)) {
                    if(!intf.isUp() || intf.getInterfaceAddresses().size() == 0) {
                        continue;
                    }
                    if ("tun0".equals(intf.getName()) || "ppp0".equals(intf.getName())){
                        return true;
                    }
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return false;
    }
    /**
     * 获取正在运行的应用列表
     */
//    public  static  getRunningApp(Context context){
//        List<AndroidAppProcess> processes = AndroidProcesses.getRunningAppProcesses();
//        for (AndroidAppProcess process:processes) {
//            String name = process.name;
//            String packageName = process.getPackageName();
//            Boolean bForeground = process.foreground;
//            String appName = Utils.getName(activity,process);
//            Log.d("===AppName : ",""+appName);
//            Log.d("Name : ",""+name);
//            Log.d("PackageName : ",""+packageName);
//            Log.d("Foreground : ",""+bForeground);
//        }
//    }
    //其他方式有可能或者不到mac地址
    public static String getMacAddr() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;
                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }
                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:",b));
                }
                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
        }
        return "02:00:00:00:00:00";
    }
    /**
     获取蓝牙地址
     */
    public static String getBluetoothMacAddress() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        String bluetoothMacAddress = "";
        try {
            Field mServiceField = bluetoothAdapter.getClass().getDeclaredField("mService");
            mServiceField.setAccessible(true);

            Object btManagerService = mServiceField.get(bluetoothAdapter);

            if (btManagerService != null) {
                bluetoothMacAddress = (String) btManagerService.getClass().getMethod("getAddress").invoke(btManagerService);
            }
        } catch (Exception ignored) {

        }
        return bluetoothMacAddress;
    }
}