package com.axaet.module.view.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.widget.Scroller;

import androidx.appcompat.widget.AppCompatButton;

/**
 * date: 2019/3/20
 *
 * @author yuShu
 */
public class MyButton extends AppCompatButton {

    private static final String TAG = "MyButton";

    public MyButton(Context context) {
        super(context);
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 检测速度
     */
    private final VelocityTracker tracker = VelocityTracker.obtain();

    /**
     * 滑动效果
     */
    private final Scroller scroller = new Scroller(getContext());

    /**
     * 手势监听
     */
    private final GestureDetector detector = new GestureDetector(getContext(), new GestureDetector.OnGestureListener() {
        @Override
        public boolean onDown(MotionEvent e) {
            Log.i(TAG, "onDown: ");
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {
            Log.i(TAG, "onShowPress: ");
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Log.i(TAG, "onSingleTapUp: ");
            return false;
        }


        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Log.i(TAG, "onScroll: ");
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            Log.i(TAG, "onLongPress: ");
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.i(TAG, "onFling: ");
            smoothScrollTo((int) (velocityX+300));
            return false;
        }
    });


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        detector.onTouchEvent(event);
        detectionSpeed(event);
        return super.onTouchEvent(event);
    }


    /**
     * 检测滑动速度
     *
     * @param event 手势
     */
    private void detectionSpeed(MotionEvent event) {
        tracker.addMovement(event);
        tracker.computeCurrentVelocity(1000);
        float xVelocity = tracker.getXVelocity();
        float yVelocity = tracker.getYVelocity();
//        Log.i(TAG, "xVelocity: " + xVelocity + "  yVelocity: " + yVelocity);
    }


    private void smoothScrollTo(int destX) {
        int scrollX = getScrollX();
        int delta = destX - scrollX;
        scroller.startScroll(scrollX, 0, delta, 0, 8000);
        invalidate();
    }


    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.i(TAG, "onDetachedFromWindow: ");
        //重置，回收内存
        tracker.clear();
        tracker.recycle();
    }
}
