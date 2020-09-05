package common.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

public class VerticalSrcollView extends ViewGroup {
    public VerticalSrcollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private Context cx;

    public VerticalSrcollView(Context context) {
        super(context);
        this.cx = context;
        init();
    }

    public VerticalSrcollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.cx = context;
        init();
    }

    private void init() {
        scroller = new Scroller(cx);
        detector = new GestureDetector(cx, new GestureDetector.OnGestureListener() {
            public boolean onSingleTapUp(MotionEvent e) {
                return false;
            }

            public void onShowPress(MotionEvent e) {
            }

            /**
             * 当手指正常滑动时回调
             */
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                if ((distanceY < 0 && curId == 0) || (distanceY > 0 && curId == getChildCount() - 1)) {// 首页跟末尾页超出边界
                    return false;
                }
                scrollBy(0, (int) distanceY);
                return false;
            }

            public void onLongPress(MotionEvent e) {
            }

            /**
             * 当快速滑动式回调
             */
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                System.out.println("vY：" + velocityY);
                System.out.println("cr：" + curId);
                if (velocityY > 0 && curId > 0) {
                    moveToDest(curId - 1);
                } else if (velocityY < 0 && curId < getChildCount() - 1) {
                    moveToDest(curId + 1);
                } else {
                    moveToDest(curId);
                }
                isFling = true;
                return false;
            }

            public boolean onDown(MotionEvent e) {
                return false;
            }
        });
    }

    /**
     * 对子view进行排列布局
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        for (int i = 0; i < getChildCount(); i++) {
            View view = this.getChildAt(i);
            view.layout(0, i * getHeight(), getWidth(), (i + 1) * getHeight());
        }
    }

    /**
     * 必须对子View进行measure
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            view.measure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    private GestureDetector detector;
    private Scroller scroller;
    private int curId;// 当前子View的Id
    private boolean isFling = false; // 是否发生快速滑动

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        detector.onTouchEvent(event);// 交给手势识别器

        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:// 手指抬起
                if (!isFling) moveToDest();
                isFling = false;
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * ViewGroup的内容移动到合适位置
     */
    private void moveToDest() {
        // 计算当前子view的id
        int destId = (getHeight() / 2 + getScrollY()) / getHeight();
        moveToDest(destId);
    }

    /**
     * 将指定下标destId的控件移到屏幕
     *
     * @param destId
     */
    private void moveToDest(int destId) {
        curId = destId;
        if (destId > getChildCount() - 1) destId = getChildCount() - 1;
        int distance = destId * getHeight() - getScrollY();// 需要移动的距离
        // scrollBy(0, distance);
        scroller.startScroll(0, getScrollY(), 0, distance);
        invalidate();// 刷新视图,执行computeScroll()
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollTo(0, scroller.getCurrY());
            invalidate();
        }
    }

    public void setOnPageScrollListener(IPageChangeListener changeListener) {
        this.pageChangeListener = changeListener;
    }

    private IPageChangeListener pageChangeListener;

    /**
     * 页面滑动监听器
     *
     * @author zyh
     */
    public interface IPageChangeListener {
        public void changeTo(int pageId);
    }
}
