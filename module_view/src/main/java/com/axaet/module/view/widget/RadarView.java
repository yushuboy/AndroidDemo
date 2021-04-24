package com.axaet.module.view.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;


/**
 * Created by Mr_immortalZ on 2016/5/2.
 * email : mr_immortalz@qq.com
 */
public class RadarView extends View {
    private Paint mPaintLine;//画圆线需要用到的paint
    private Paint mPaintScan;//画扫描需要用到的paint

    private int mWidth, mHeight;//整个图形的长度和宽度

    private RectF rect = new RectF();
    private Matrix matrix = new Matrix();//旋转需要的矩阵
    private int scanAngle;//扫描旋转的角度
    private Shader scanShader;//扫描渲染shader

    //每个圆圈所占的比例
    private static float[] circleProportion = {1 / 12f, 2 / 12f, (float) (3.5 / 12f), (float) (5.4 / 12f)};

    private int currentScanningCount;//当前扫描的次数
    private int currentScanningItem;//当前扫描显示的item
    private int maxScanItemCount;//最大扫描次数
    private boolean startScan = false;//只有设置了数据后才会开始扫描
    private IScanningListener iScanningListener;//扫描时监听回调接口

    public void setScanningListener(IScanningListener iScanningListener) {
        this.iScanningListener = iScanningListener;
    }


    private final Runnable run = new Runnable() {
        @Override
        public void run() {
            int scanSpeed = 6;
            scanAngle = (scanAngle + scanSpeed) % 360 + 1;
            matrix.postRotate(scanSpeed, mWidth / 2.0f, mHeight / 2.0f);
            invalidate();
            postDelayed(run, 130);
            //开始扫描显示标志为true 且 只扫描一圈
            if (startScan && currentScanningCount <= (360 / scanSpeed)) {
                if (iScanningListener != null && currentScanningCount % scanSpeed == 0
                        && currentScanningItem < maxScanItemCount) {
                    iScanningListener.onScanning(currentScanningItem, scanAngle);
                    currentScanningItem++;
                } else if (iScanningListener != null && currentScanningItem == maxScanItemCount) {
                    iScanningListener.onScanSuccess();
                }
                currentScanningCount++;
            }
        }
    };


    public void setStartScan() {
        this.startScan = false;
        this.currentScanningCount = 0;
        this.currentScanningItem = 0;
        this.maxScanItemCount = -1;
    }


    public RadarView(Context context) {
        this(context, null);
    }

    public RadarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RadarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        mPaintLine = new Paint();
        mPaintLine.setColor(Color.parseColor("#FFFFFF"));
        mPaintLine.setAntiAlias(true);
        mPaintLine.setStrokeWidth(2);
        mPaintLine.setStyle(Paint.Style.STROKE);

        mPaintScan = new Paint();
        mPaintScan.setStyle(Paint.Style.FILL_AND_STROKE);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        // 如果布局里面设置的是固定值,这里取布局里面的固定值;如果设置的是match_parent,则取父布局的大小
        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
            mHeight = mWidth;
        } else {
            // 如果布局里面没有设置固定值,这里取布局的宽度的1/2
            mWidth = widthSize;
            mHeight = mWidth;
        }
        setMeasuredDimension(mWidth, mHeight);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //设置扫描渲染的shader
        scanShader = new SweepGradient(mWidth / 2.0f, mHeight / 2.0f,
                new int[]{Color.TRANSPARENT, Color.parseColor("#84B5CA")}, null);
        rect.set((int) (mWidth / 2 - mWidth * circleProportion[0]), (int) (mHeight / 2 - mWidth * circleProportion[0]),
                (int) (mWidth / 2 + mWidth * circleProportion[0]), (int) (mHeight / 2 + mWidth * circleProportion[0]));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawCircle(canvas);
        drawScan(canvas);
    }

    /**
     * 绘制圆线圈
     *
     * @param canvas
     */

    private void drawCircle(Canvas canvas) {
        canvas.drawCircle(mWidth / 2.0f, mHeight / 2.0f, mWidth * circleProportion[1], mPaintLine);     // 绘制小圆
        canvas.drawCircle(mWidth / 2.0f, mHeight / 2.0f, mWidth * circleProportion[2], mPaintLine);   // 绘制中圆
        canvas.drawCircle(mWidth / 2.0f, mHeight / 2.0f, mWidth * circleProportion[3], mPaintLine);  // 绘制大圆
    }

    /**
     * 绘制扫描
     *
     * @param canvas
     */
    private void drawScan(Canvas canvas) {
        canvas.save();
        mPaintScan.setShader(scanShader);
        canvas.concat(matrix);
        canvas.drawCircle(mWidth / 2.0f, mHeight / 2.0f, mWidth * circleProportion[3], mPaintScan);
        canvas.restore();
    }


    public interface IScanningListener {
        //正在扫描（此时还没有扫描完毕）时回调
        void onScanning(int position, float scanAngle);

        //扫描成功时回调
        void onScanSuccess();
    }

    public void setMaxScanItemCount(int maxScanItemCount) {
        this.maxScanItemCount = maxScanItemCount;
    }

    /**
     * 开始扫描
     */
    public void startScan() {
        this.startScan = true;
    }

    /**
     * 出现时
     */
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        post(run);
    }

    /**
     * 销毁时
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(run);
    }
}

