package dog.black.com.blackdog.videoPage.bean;

import cn.bmob.v3.BmobObject;

public class PhoneInfoEntity  {
      private  String DISPLAY; //设备显示的版本包 固件版本 Build.DISPLAY
      private  String CPU_ABI; //CPU指令集 Build.CPU_ABI
      private  String CPU_ABI2; //CPU指令集 Build.CPU_ABI2
      private  String MANUFACTURER; //手机制造商，例如：HUAWEI Build.MANUFACTURER
    private  String BRAND; //手机品牌，例如：HONOR Build.BRAND
    private  String HARDWARE; //CPU型号 Build.HARDWARE
    private  String PRODUCT; //手机型号，设置-关于手机-型号 Build.PRODUCT
    private  String FINGERPRINT; //build的指纹信息 Build.FINGERPRINT
    private  String getRadioVersion; //基带版本 Build.getRadioVersion()
    private  String RADIO; //基带版本 Build.RADIO
    private  String BOARD; //主版 Build.BOARD
    private  String  DEVICE; //设备驱动名称 Build.DEVICE
    private  String ID; //设备版本号 Build.ID)
    private  String MODEL; //手机型号 Build.MODEL
    private  String BOOTLOADER; //主板引导程序 Build.BOOTLOADER
    private  String HOST; //设备主机地址 Build.HOST
    private  String TAGS; //描述标签 Build.TAGS
    private  String TYPE; //设备版本类型 Build.TYPE
    private  String INCREMENTAL; //源码控制版本号 Build.VERSION.INCREMENTAL
    private  String RELEASE; // Andorid系统版本: Build.VERSION.RELEASE
    private  String SDK_INT;// Android系统api版本 Build.VERSION.SDK_INT
    private  String TIME; //固定build时间 Build.TIME
    private  String  ANDROID_ID; //设备版本类型  AndroidID
    private  String one;//gsm.version.baseband  基带radioversion3
    private  String two;//gsm.version.baseband  基带radioversion4
    private  String description;//gsm.version.baseband      描述信息
    private  String IMEI;//IMEI getDeviceId
    private  String BluetoothAddress;//蓝牙地址
    private  String getMacAddress;//WiFiMac地址
    private  String getSSID;//WiFi名称
    private  String getNetworkInfo;//WiFi-getExtraInfo
    private  String getBSSID;//WiFi-接入点的识别地址
    private  String getLine1Number;//电话号码
    private  String getSimSerialNumber;//手机卡序列号
    private  String getNetworkOperator;//网络运营商类型
    private  String networkOperatorName;//网络类型名称
    private  String getSimOperator;//sim卡运营商类型
    private  String getSimOperatorName;//sim卡运营商名称
    private  String networkCountryIso;//网络ISO代码
    private  String SimCountryIso;//网络ISO代码

    public String getSimCountryIso() {
        return SimCountryIso;
    }

    public void setSimCountryIso(String simCountryIso) {
        SimCountryIso = simCountryIso;
    }

    private  String softwareVersion;//系统版本
    private  String getNetworkType;//网络链接类型
    private  String getPhoneType;//手机类型
    private  String getSimState;//sim卡状态
    private  String getWidth;//手机宽高
    private  String getHeight;//手机宽高
    private  String getLocalIpAddress;//手机内网ip地址
    private  String densityDpi;//屏幕densityDpi
    private  String density;//屏幕density
    private  String xdpi;//屏幕xdpi
    private  String ydpi;//屏幕xdpi
    private  String scaledDensity;//屏幕scalDensity
    public String getDISPLAY() {
        return DISPLAY;
    }

    public void setDISPLAY(String DISPLAY) {
        this.DISPLAY = DISPLAY;
    }

    public String getCPU_ABI() {
        return CPU_ABI;
    }

    public void setCPU_ABI(String CPU_ABI) {
        this.CPU_ABI = CPU_ABI;
    }

    public String getCPU_ABI2() {
        return CPU_ABI2;
    }

    public void setCPU_ABI2(String CPU_ABI2) {
        this.CPU_ABI2 = CPU_ABI2;
    }

    public String getMANUFACTURER() {
        return MANUFACTURER;
    }

    public void setMANUFACTURER(String MANUFACTURER) {
        this.MANUFACTURER = MANUFACTURER;
    }

    public String getBRAND() {
        return BRAND;
    }

    public void setBRAND(String BRAND) {
        this.BRAND = BRAND;
    }

    public String getHARDWARE() {
        return HARDWARE;
    }

    public void setHARDWARE(String HARDWARE) {
        this.HARDWARE = HARDWARE;
    }

    public String getPRODUCT() {
        return PRODUCT;
    }

    public void setPRODUCT(String PRODUCT) {
        this.PRODUCT = PRODUCT;
    }

    public String getFINGERPRINT() {
        return FINGERPRINT;
    }

    public void setFINGERPRINT(String FINGERPRINT) {
        this.FINGERPRINT = FINGERPRINT;
    }

    public String getGetRadioVersion() {
        return getRadioVersion;
    }

    public void setGetRadioVersion(String getRadioVersion) {
        this.getRadioVersion = getRadioVersion;
    }

    public String getRADIO() {
        return RADIO;
    }

    public void setRADIO(String RADIO) {
        this.RADIO = RADIO;
    }

    public String getBOARD() {
        return BOARD;
    }

    public void setBOARD(String BOARD) {
        this.BOARD = BOARD;
    }

    public String getDEVICE() {
        return DEVICE;
    }

    public void setDEVICE(String DEVICE) {
        this.DEVICE = DEVICE;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getMODEL() {
        return MODEL;
    }

    public void setMODEL(String MODEL) {
        this.MODEL = MODEL;
    }

    public String getBOOTLOADER() {
        return BOOTLOADER;
    }

    public void setBOOTLOADER(String BOOTLOADER) {
        this.BOOTLOADER = BOOTLOADER;
    }

    public String getHOST() {
        return HOST;
    }

    public void setHOST(String HOST) {
        this.HOST = HOST;
    }

    public String getTAGS() {
        return TAGS;
    }

    public void setTAGS(String TAGS) {
        this.TAGS = TAGS;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }

    public String getINCREMENTAL() {
        return INCREMENTAL;
    }

    public void setINCREMENTAL(String INCREMENTAL) {
        this.INCREMENTAL = INCREMENTAL;
    }

    public String getRELEASE() {
        return RELEASE;
    }

    public void setRELEASE(String RELEASE) {
        this.RELEASE = RELEASE;
    }

    public String getSDK_INT() {
        return SDK_INT;
    }

    public void setSDK_INT(String SDK_INT) {
        this.SDK_INT = SDK_INT;
    }

    public String getTIME() {
        return TIME;
    }

    public void setTIME(String TIME) {
        this.TIME = TIME;
    }

    public String getANDROID_ID() {
        return ANDROID_ID;
    }

    public void setANDROID_ID(String ANDROID_ID) {
        this.ANDROID_ID = ANDROID_ID;
    }

    public String getOne() {
        return one;
    }

    public void setOne(String one) {
        this.one = one;
    }

    public String getTwo() {
        return two;
    }

    public void setTwo(String two) {
        this.two = two;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIMEI() {
        return IMEI;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    public String getBluetoothAddress() {
        return BluetoothAddress;
    }

    public void setBluetoothAddress(String bluetoothAddress) {
        BluetoothAddress = bluetoothAddress;
    }

    public String getGetMacAddress() {
        return getMacAddress;
    }

    public void setGetMacAddress(String getMacAddress) {
        this.getMacAddress = getMacAddress;
    }

    public String getGetSSID() {
        return getSSID;
    }

    public void setGetSSID(String getSSID) {
        this.getSSID = getSSID;
    }

    public String getGetNetworkInfo() {
        return getNetworkInfo;
    }

    public void setGetNetworkInfo(String getNetworkInfo) {
        this.getNetworkInfo = getNetworkInfo;
    }

    public String getGetBSSID() {
        return getBSSID;
    }

    public void setGetBSSID(String getBSSID) {
        this.getBSSID = getBSSID;
    }

    public String getGetLine1Number() {
        return getLine1Number;
    }

    public void setGetLine1Number(String getLine1Number) {
        this.getLine1Number = getLine1Number;
    }

    public String getGetSimSerialNumber() {
        return getSimSerialNumber;
    }

    public void setGetSimSerialNumber(String getSimSerialNumber) {
        this.getSimSerialNumber = getSimSerialNumber;
    }

    public String getGetNetworkOperator() {
        return getNetworkOperator;
    }

    public void setGetNetworkOperator(String getNetworkOperator) {
        this.getNetworkOperator = getNetworkOperator;
    }

    public String getNetworkOperatorName() {
        return networkOperatorName;
    }

    public void setNetworkOperatorName(String networkOperatorName) {
        this.networkOperatorName = networkOperatorName;
    }

    public String getGetSimOperator() {
        return getSimOperator;
    }

    public void setGetSimOperator(String getSimOperator) {
        this.getSimOperator = getSimOperator;
    }

    public String getGetSimOperatorName() {
        return getSimOperatorName;
    }

    public void setGetSimOperatorName(String getSimOperatorName) {
        this.getSimOperatorName = getSimOperatorName;
    }

    public String getNetworkCountryIso() {
        return networkCountryIso;
    }

    public void setNetworkCountryIso(String networkCountryIso) {
        this.networkCountryIso = networkCountryIso;
    }

    public String getSoftwareVersion() {
        return softwareVersion;
    }

    public void setSoftwareVersion(String softwareVersion) {
        this.softwareVersion = softwareVersion;
    }

    public String getGetNetworkType() {
        return getNetworkType;
    }

    public void setGetNetworkType(String getNetworkType) {
        this.getNetworkType = getNetworkType;
    }

    public String getGetPhoneType() {
        return getPhoneType;
    }

    public void setGetPhoneType(String getPhoneType) {
        this.getPhoneType = getPhoneType;
    }

    public String getGetSimState() {
        return getSimState;
    }

    public void setGetSimState(String getSimState) {
        this.getSimState = getSimState;
    }

    public String getGetWidth() {
        return getWidth;
    }

    public void setGetWidth(String getWidth) {
        this.getWidth = getWidth;
    }

    public String getGetHeight() {
        return getHeight;
    }

    public void setGetHeight(String getHeight) {
        this.getHeight = getHeight;
    }

    public String getGetLocalIpAddress() {
        return getLocalIpAddress;
    }

    public void setGetLocalIpAddress(String getLocalIpAddress) {
        this.getLocalIpAddress = getLocalIpAddress;
    }

    public String getDensityDpi() {
        return densityDpi;
    }

    public void setDensityDpi(String densityDpi) {
        this.densityDpi = densityDpi;
    }

    public String getDensity() {
        return density;
    }

    public void setDensity(String density) {
        this.density = density;
    }

    public String getXdpi() {
        return xdpi;
    }

    public void setXdpi(String xdpi) {
        this.xdpi = xdpi;
    }

    public String getYdpi() {
        return ydpi;
    }

    public void setYdpi(String ydpi) {
        this.ydpi = ydpi;
    }

    public String getScaledDensity() {
        return scaledDensity;
    }

    public void setScaledDensity(String scaledDensity) {
        this.scaledDensity = scaledDensity;
    }


}
