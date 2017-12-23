package dog.black.com.blackdog.mainView.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fen.asunder.R;

import java.util.List;

import dog.black.com.blackdog.videoPage.bean.PlayUrlEntity;

/**
 * Created by feq on 2017/2/25.
 */

public class ChangeAdapter extends BaseQuickAdapter<PlayUrlEntity, BaseViewHolder> {
    public ChangeAdapter(int layoutResId, List<PlayUrlEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PlayUrlEntity item) {
        helper.setText(R.id.tv_name, item.getName());
    }
}
