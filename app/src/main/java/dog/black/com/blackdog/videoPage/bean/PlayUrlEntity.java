package dog.black.com.blackdog.videoPage.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by feq on 2017/2/26.
 * 线路实体类
 */

public class PlayUrlEntity extends BmobObject {
    private String name;
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
