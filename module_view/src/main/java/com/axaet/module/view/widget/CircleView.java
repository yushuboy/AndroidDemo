package com.axaet.module.view.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;


/**
 * Created by Mr_immortalZ on 2016/5/3.
 * email : mr_immortalz@qq.com
 */
public class CircleView extends View {
    private Paint mPaint;
    private Paint mTextPaint;
    private Bitmap mBitmap;
    private float radius;//半径
    private float disX;//位置X
    private float disY;//位置Y
    private float angle;//旋转的角度
    private float proportion;//根据远近距离的不同计算得到的应该占的半径比例

    private RectF rectF = new RectF();

    public float getProportion() {
        return proportion;
    }

    public void setProportion(float proportion) {
        this.proportion = proportion;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float getDisX() {
        return disX;
    }

    public void setDisX(float disX) {
        this.disX = disX;
    }

    public float getDisY() {
        return disY;
    }

    public void setDisY(float disY) {
        this.disY = disY;
    }

    public CircleView(Context context) {
        this(context, null);
    }

    public CircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mTextPaint = new Paint(mPaint);
        mTextPaint.setTextSize(radius / 5);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setColor(Color.parseColor("#FFFFFF"));
//        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.headportrait);
    }

    int mWidth, mHeight;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        // 如果布局里面设置的是固定值,这里取布局里面的固定值;如果设置的是match_parent,则取父布局的大小
        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = (int) radius;
            mHeight = (int) (mWidth * 4 / 3.0);
        } else {
            // 如果布局里面没有设置固定值,这里取布局的宽度的1/2
            mWidth = (int) radius;
            mHeight = (int) (mWidth * 4 / 3.0);
        }
        setMeasuredDimension(mWidth, mHeight);
        rectF.set(0, 0, mWidth, mWidth);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        if (mBitmap != null) {
            canvas.drawBitmap(mBitmap, null, rectF, mPaint);
        }
        canvas.drawText("MyTag", (float) (mWidth / 2.0), (float) ((mHeight - mWidth) / 2.0 + mWidth), mTextPaint);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mBitmap != null) {
            mBitmap.recycle();
        }
        mBitmap = null;
        rectF = null;
        mTextPaint = null;
        mPaint = null;
    }


}
