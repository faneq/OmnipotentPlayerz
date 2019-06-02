package dog.black.com.blackdog.videoPage;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fen.asunder.R;

import dog.black.com.blackdog.utils.AndroidUtil;


/**
 * 2016 逛街购（北京）网络科技有限公司，版权所有
 * guangjiegou - Android客户端
 * Content:自定义Dialog
 * Created by wangwei on 2016/4/12.
 */
public class BaseDialog extends Dialog {
    /* 声明控件：标题、提示内容、取消按钮、确认按钮 */
    private TextView mTitle;
    private TextView mMessage;
    // private TextView mCancel;
    // private TextView mConfirm;
    private View mBelowTitledivider;
    private LinearLayout mChooseDialog;
    private LinearLayout mListDialog;
    private LinearLayout mChooseBtn;

    private Context context;

    public BaseDialog(Context context) {
        super(context, R.style.baseDialog);
        this.context = context;
    }

    public BaseDialog(Context context, boolean cancelable,
                      OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
    }

    public BaseDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_layout);
        initView();
        setWidth();
    }

    /**
     * 初始化dialog View;
     */
    private void initView() {
        mTitle = (TextView) findViewById(R.id.dialogTitle);
        mMessage = (TextView) findViewById(R.id.dialogMsg);
        // mCancel = (TextView) findViewById(R.id.dialogCancel);
        // mConfirm = (TextView) findViewById(R.id.dialogConfirm);
        mBelowTitledivider = findViewById(R.id.dialogBelowTitledivider);
        mChooseDialog = (LinearLayout) findViewById(R.id.chooseDialog);
        mListDialog = (LinearLayout) findViewById(R.id.listDialog);
        mChooseBtn = (LinearLayout) findViewById(R.id.chooseBtn);
    }

    /***
     * 为对话框设置标题<br>
     *
     * @param title
     */
    public void setTitleText(String title) {
        mTitle.setText(title);
    }

    /**
     * 为对话框设置标题<br>
     *
     * @param title
     */
    public void setTitleText(int title) {
        mTitle.setText(title);
    }

    public void setTitleText(String title, int gravity) {
        setTitleText(title);
        mTitle.setGravity(gravity);
    }

    /**
     * 为对话框设置对话内容<br>
     *
     * @param msg
     */
    public void setMsgText(String msg) {
        msg = msg.replace("<br>", "\n");
        mMessage.setText(msg);
    }

    /**
     * 为对话框设置对话内容<br>
     *
     * @param msg
     */
    public void setMsgText(int msg) {
        String message = context.getString(msg).replace("<br>", "\n");
        mMessage.setText(message);
    }

    /**
     * 是否显示标题
     *
     * @param has
     */
    public void hasTitle(boolean has) {
        if (!has) {
            mTitle.setVisibility(View.GONE);
            mBelowTitledivider.setVisibility(View.GONE);
        }
    }

    /**
     * 设置普通选择按钮对话框 通过传入参数判断显示按钮个数<br>
     *
     * @param textArray
     */
    public void setChooseDialog(TextView[] textArray) {
        mChooseBtn.removeAllViews();
        mListDialog.setVisibility(View.GONE);
        mChooseDialog.setVisibility(View.VISIBLE);
        int max = textArray.length - 1;
        if (max == 0) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0,
                    AndroidUtil.dp2px(getContext(), 53), 1);
            params.setMargins(AndroidUtil.dp2px(context, 15), 0, AndroidUtil.dp2px(context, 15), 0);
            textArray[0].setLayoutParams(params);
            textArray[0].setTextSize(19);
            textArray[0].setTextColor(context.getResources().getColor(
                    R.color.list_text_title));
            textArray[0].setGravity(Gravity.CENTER);
            textArray[0].setBackgroundResource(R.drawable.dialog_white_item_bg);
            mChooseBtn.addView(textArray[0]);

        } else {
            for (int i = 0; i < textArray.length; i++) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0,
                        AndroidUtil.dp2px(getContext(), 53), 1);

                textArray[i].setTextSize(19);
                textArray[i].setTextColor(context.getResources().getColor(
                        R.color.list_text_title));
                textArray[i].setGravity(Gravity.CENTER);
                // 创建按钮中间分割线
                View divider = new View(context);
                // 为分割线设置尺寸大小
                LinearLayout.LayoutParams dividerParams = new LinearLayout.LayoutParams(1,
                        LinearLayout.LayoutParams.MATCH_PARENT, 0);
                dividerParams.setMargins(0, 7, 0, 7);
                divider.setLayoutParams(dividerParams);
                // 设置背景色
                divider.setBackgroundResource(R.drawable.common_pop_title_option_dividing);
                // 设置背景
                textArray[i]
                        .setBackgroundResource(R.drawable.dialog_white_item_bg);
                if (i == 0) {
                    params.setMargins(AndroidUtil.dp2px(context, 15), 0, 0, 0);
                    textArray[i].setLayoutParams(params);
                } else if (i == max) {
                    params.setMargins(0, 0, AndroidUtil.dp2px(context, 15), 0);
                    textArray[i].setLayoutParams(params);
                    divider.setVisibility(View.GONE);
                }
                mChooseBtn.addView(textArray[i]);
                mChooseBtn.addView(divider);
            }
        }
    }

    /**
     * 设置为列表形式的Dialog<br>
     */
    public void setListDialog(TextView[] textArray) {
        setListDialog(textArray, false, 0);
    }

    /**
     * 设置为列表形式的Dialog<br>
     *
     * @param checkedIndex 默认高亮的选项
     */
    public void setListDialog(TextView[] textArray, int checkedIndex) {
        setListDialog(textArray, true, checkedIndex);
    }


    /**
     * 设置为列表形式的Dialog<br>
     *
     * @param
     * @param defaultChecked 是否选中某一项
     * @param checkedIndex   默认选中项的index
     */
    public void setListDialog(TextView[] textArray, boolean defaultChecked, int checkedIndex) {
        mListDialog.removeAllViews();
        mChooseDialog.setVisibility(View.GONE);
        mListDialog.setVisibility(View.VISIBLE);
        int max = textArray.length - 1;
        // 循环数组
        for (int i = 0; i < textArray.length; i++) {
            // 创建TextView控件
            // TextView childView = new TextView(context);

            // 设置位置
            textArray[i].setGravity(Gravity.CENTER_VERTICAL);
            // 构造一个LayoutParams
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    AndroidUtil.dp2px(context, 56));
            params.setMargins(AndroidUtil.dp2px(context, 15), 0, AndroidUtil.dp2px(context, 15), 0);
            // TextView配置为params
            textArray[i].setLayoutParams(params);
            // textView可点击
            textArray[i].setClickable(true);
            // 设置文字尺寸
            textArray[i].setTextSize(18);
            // 设置文字颜色
            if (defaultChecked && i == checkedIndex) {
                textArray[i].setTextColor(context.getResources().getColor(R.color.contacts_organge));
            } else {
                textArray[i].setTextColor(context.getResources().getColor(R.color.list_text_title));
            }
            // 创建下方分割线
            View divider = new View(context);
            // 为分割线设置尺寸大小
            LinearLayout.LayoutParams dividerParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, 1, 1);
            dividerParams.setMargins(AndroidUtil.dp2px(context, 15), 0, AndroidUtil.dp2px(context, 15), 0);
            divider.setLayoutParams(dividerParams);
            // 设置背景色
            divider.setBackgroundResource(R.drawable.common_pop_option_dividing2);
            // 设置背景
            textArray[i].setBackgroundResource(R.drawable.dialog_white_item_bg);
            if (i == max) {
                divider.setVisibility(View.GONE);
            }
            // if (i == 0) {
            // textArray[i]
            // .setBackgroundResource(R.drawable.dialog_list_header_bg);
            // } else if (i == max) {
            // textArray[i]
            // .setBackgroundResource(R.drawable.dialog_list_bottom_bg);
            // divider.setVisibility(View.GONE);
            // } else {
            // textArray[i]
            // .setBackgroundResource(R.drawable.dialog_list_item_bg);
            // }
            // // 设置内填充
            // textArray[i].setPadding(30, 0, 0, 0);

            // 设置文字
            // textArray[i].setText(listContent[i]);
            // 分别将TextView 和view分割线添加到列表对话框容器中
            mListDialog.addView(textArray[i]);
            mListDialog.addView(divider);
        }
    }

    /**
     * 是否显示对话内容
     *
     * @param has
     */
    public void hasMsg(boolean has) {
        if (!has) {
            mMessage.setVisibility(View.GONE);
        }
    }

    // /**
    // * 是否为单选
    // *
    // * @param single参数为boolean类型单选隐藏cancel按钮
    // */
    // public void singleBtn(boolean single) {
    // if (single) {
    // mCancel.setVisibility(View.GONE);
    // }
    // }

    /**
     * 获取标题文本内容
     *
     * @return返回标题内容 没有返回空
     */
    public String getTitleText() {
        String titleText = mTitle.getText().toString();
        return (titleText.equals("") || titleText == null) ? "" : titleText;
    }

    /**
     * 获取提示信息文本内容
     *
     * @return返回标题内容 没有返回空
     */
    public String getMsgText() {
        String msgText = mMessage.getText().toString();
        return (msgText.equals("") || msgText == null) ? "" : msgText;
    }

    private void setWidth() {
        WindowManager wm = ((Activity) context).getWindowManager();
        Display d = wm.getDefaultDisplay();
        android.view.WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = d.getWidth();
        getWindow().setAttributes(params);
    }
}
