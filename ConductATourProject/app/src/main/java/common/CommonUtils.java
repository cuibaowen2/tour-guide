package common;

import android.graphics.Bitmap;
import android.view.View;

/**
 * 公共工具类
 */
public class CommonUtils {

    /**
     * 百度地图将view转化成图片
     *
     * @param view
     * @return
     */
    public static Bitmap getViewBitmap(View view) {
        int me = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(me, me);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }
}
