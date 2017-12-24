package dog.black.com.blackdog.mainView.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by feq on 2017/12/23.
 */

public class ShareInfo extends BmobObject {
    String url;
    String vision;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVision() {
        return vision;
    }

    public void setVision(String vision) {
        this.vision = vision;
    }
}
