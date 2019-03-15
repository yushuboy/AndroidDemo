package com.axaet.module.view.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * date: 2019/1/19
 *
 * @author yuShu
 */
public class NestRecyclerView extends RecyclerView {

    private int lastVisibleItemPosition;
    private int firstVisibleItemPosition;
    /**
     * 记录上次Y位置
     */
    private float mLastY = 0;
    private boolean isTopToBottom = false;
    private boolean isBottomToTop = false;


    public NestRecyclerView(@NonNull Context context) {
        super(context);
    }

    public NestRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NestRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastY = event.getY();
                //不允许父View拦截事件
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                float nowY = event.getY();
                isIntercept(nowY);
                if (isBottomToTop || isTopToBottom) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                    return false;
                } else {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                mLastY = nowY;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                getParent().requestDisallowInterceptTouchEvent(false);
                break;
            default:
        }
        return super.onTouchEvent(event);
    }

    private void isIntercept(float nowY) {
        isTopToBottom = false;
        isBottomToTop = false;
        RecyclerView.LayoutManager layoutManager = getLayoutManager();
        if (layoutManager != null) {
            if (layoutManager instanceof GridLayoutManager) {
                //得到当前界面，最后一个子视图对应的position
                lastVisibleItemPosition = ((GridLayoutManager) layoutManager)
                        .findLastVisibleItemPosition();
                //得到当前界面，第一个子视图的position
                firstVisibleItemPosition = ((GridLayoutManager) layoutManager)
                        .findFirstVisibleItemPosition();
            }
            //得到当前界面可见数据的大小
            int visibleItemCount = layoutManager.getChildCount();
            //得到RecyclerView对应所有数据的大小
            int totalItemCount = layoutManager.getItemCount();
            Log.d("nestScrolling", "onScrollStateChanged");
            if (visibleItemCount > 0) {
                if (lastVisibleItemPosition == totalItemCount - 1) {
                    //最后视图对应的position等于总数-1时，说明上一次滑动结束时，触底了
                    Log.d("nestScrolling", "触底了");
                    if (canScrollHorizontally(-1) && nowY < mLastY) {
                        // 不能向上滑动
                        Log.d("nestScrolling", "不能向上滑动");
                        isBottomToTop = true;
                    } else {
                        Log.d("nestScrolling", "向下滑动");
                    }
                } else if (firstVisibleItemPosition == 0) {
                    //第一个视图的position等于0，说明上一次滑动结束时，触顶了
                    Log.d("nestScrolling", "触顶了");
                    if (canScrollHorizontally(-1) && nowY > mLastY) {
                        // 不能向下滑动
                        Log.d("nestScrolling", "不能向下滑动");
                        isTopToBottom = true;
                    } else {
                        Log.d("nestScrolling", "向上滑动");
                    }
                }
            }
        }
    }
}