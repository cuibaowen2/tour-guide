package common.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Scroller;

import androidx.annotation.Nullable;

import com.conductatour.pack.R;

public class MyScrollerView extends LinearLayout {
    /**
     * downY:手指按下时距离View顶部的距离
     * moveY:手指在屏幕上滑动的距离
     * movedY:手指在屏幕上总共滑动的距离
     */
    private int downY, moveY, movedY;

    private View mChild;
    private Scroller mScroller;
    private int mScrollHeight;
    private boolean isTop = false;
    private float visibilityHeight;

    public MyScrollerView(Context context) {
        super(context, null);
    }

    public MyScrollerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyScrollerView);
        visibilityHeight = ta.getDimension(R.styleable.MyScrollerView_visibility_height, 200);
        ta.recycle();

        init(context);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mScrollHeight = (int) (mChild.getMeasuredHeight() - visibilityHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mChild.layout(0, mScrollHeight, mChild.getMeasuredWidth(), mChild.getMeasuredHeight() + mScrollHeight);
    }

    private void init(Context context) {
        mScroller = new Scroller(context);
        this.setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() == 0 || getChildAt(0) == null) {
            throw new RuntimeException("没有子空间！");
        }
        if (getChildCount() > 1) {
            throw new RuntimeException("只有 一个子空间");
        }
        mChild = getChildAt(0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = (int) event.getY();

                if (!isTop && downY < mScrollHeight) {
                    return super.onTouchEvent(event);
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                moveY = (int) event.getY();
                int deY = downY - moveY;
                if (deY > 0) {
                    movedY += deY;
                    if (movedY > mScrollHeight) movedY = mScrollHeight;
                    if (movedY < mScrollHeight) {
                        scrollBy(0, deY);
                        downY = moveY;
                        return true;
                    }
                }
                if (deY < 0 && isTop) {
                    movedY += deY;
                    if (movedY < 0) movedY = 0;
                    if (movedY > 0) {
                        scrollBy(0, deY);
                    }
                    downY = moveY;
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (movedY > mScrollHeight / 4 && !isTop) {
                    mScroller.startScroll(0, getScrollY(), 0, (mScrollHeight - getScrollY()));
                    invalidate();
                    movedY = mScrollHeight;
                    isTop = true;
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(0, mScroller.getCurrY());
            postInvalidate();
        }
    }
}
