package common.customview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.conductatour.pack.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 公共的确定弹框
 */
public class BaseDialog {

    /**
     * View
     */
    @BindView(R.id.title_prompt_infor)
    TextView mTitlePromptTinfo;

    /**
     * 常量
     */
    private Context context;
    private AlertDialog alertDialog;
    private String tagStr;

    public BaseDialog(@NonNull Context context) {
        this.context = context;
        initData(context);
    }

    private void initData(Context context) {
        View parentView = LayoutInflater.from(context).inflate(R.layout.dialog_base_dialog_view, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(parentView);

        // dilog的一些设置
        builder.setCancelable(true);
        alertDialog = builder.create();
//        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.setCanceledOnTouchOutside(false);   //失去焦点dismiss
        ButterKnife.bind(this, parentView);
        alertDialog.show();
    }

    /**
     * dialog的内容和点击事件
     * 传递一个tag
     */
    public void showDialog(String context, String tagStr) {
        this.tagStr = tagStr;
        mTitlePromptTinfo.setText(context);
        alertDialog.show();
    }

    @OnClick({R.id.cancel_button, R.id.ensure_button})
    public void OnClickListener(View view) {
        switch (view.getId()) {
            case R.id.cancel_button:
                // 取消弹框
                alertDialog.dismiss();
                break;
            case R.id.ensure_button:
                // 确定按钮
                alertDialog.dismiss();
                if (dialogClickListener != null) {
                    dialogClickListener.onDialogClickListener(view, tagStr);
                }
                break;
            default:
                break;
        }
    }

    private DialogClickListener dialogClickListener;

    public void setDialogClickListener(DialogClickListener dialogClickListener) {
        this.dialogClickListener = dialogClickListener;
    }

    public interface DialogClickListener {
        void onDialogClickListener(View view, String tagStr);
    }
}
