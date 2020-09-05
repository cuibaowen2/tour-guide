package common;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Toast的工具类
 */
public class ToastUtils {
    /**
     * 显示默认的toast
     *
     * @param context
     * @param content
     */
    public static void defaultToast(Context context, String content) {
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
    }

    public static void centerToas(Context context, String content) {
        Toast centerToas = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        centerToas.setGravity(Gravity.CENTER, 0, 0);
        centerToas.show();
    }
}
