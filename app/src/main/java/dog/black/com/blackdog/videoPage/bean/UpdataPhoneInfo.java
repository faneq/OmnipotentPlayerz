package dog.black.com.blackdog.videoPage.bean;

import cn.bmob.v3.BmobObject;

public class UpdataPhoneInfo extends BmobObject {
    public String getPhoneInfos() {
        return phoneInfos;
    }

    public void setPhoneInfos(String phoneInfos) {
        this.phoneInfos = phoneInfos;
    }

    private String phoneInfos;
}
