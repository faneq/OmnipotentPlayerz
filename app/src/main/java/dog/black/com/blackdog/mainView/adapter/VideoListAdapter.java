package dog.black.com.blackdog.mainView.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fen.asunder.R;
import com.orhanobut.logger.Logger;

import java.util.List;

import dog.black.com.blackdog.mainView.bean.VideoInfo;
import dog.black.com.blackdog.utils.GlideUtils;

/**
 * Created by feq on 2017/2/18.
 */

public class VideoListAdapter extends BaseQuickAdapter<VideoInfo, BaseViewHolder> {

    public VideoListAdapter(int layoutResId, List<VideoInfo> data) {
        super(layoutResId, data);
        Logger.i("查询成功：共");
    }

    @Override
    protected void convert(BaseViewHolder helper, VideoInfo item) {
        ImageView imageView = helper.getView(R.id.iv_videoo);
        GlideUtils.laodImag(imageView, item.getVideoImg());
        helper.setText(R.id.tv_name, item.getVideoName());
        helper.setText(R.id.tv_introduce, item.getIntroduce().replace("简介：", "").replace("\t", ""));
        helper.setText(R.id.tv_video_score, item.getScore());
        Logger.i("查询成功：共");
    }
}
