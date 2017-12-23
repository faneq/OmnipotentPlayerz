package dog.black.com.blackdog.videoPage.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by feq on 2017/3/12.
 */

public class ConstantsBean extends BmobObject {
    private boolean isUpload;

    public boolean isUpload() {
        return isUpload;
    }

    public void setUpload(boolean upload) {
        isUpload = upload;
    }

    public String getUpLoadUrl() {
        return upLoadUrl;
    }

    public void setUpLoadUrl(String upLoadUrl) {
        this.upLoadUrl = upLoadUrl;
    }

    public int getVision() {
        return vision;
    }

    public void setVision(int vision) {
        this.vision = vision;
    }

    private String upLoadUrl;
    private int vision;
}
