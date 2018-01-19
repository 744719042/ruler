package com.example.ruler;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2018/1/19.
 */

public class RulerView extends View {
    /**
     * True是横向，false是竖向
     */
    private boolean orientation = false;
    private Paint paint;
    private Rect mTmpRect = new Rect();

    private int LARGE_GROUP_DIVIDER_HEIGHT = CommonUtils.dp2px(20);
    private int SMALL_GROUP_DIVIDER_HEIGHT = LARGE_GROUP_DIVIDER_HEIGHT / 2;
    private int UNIT_DIVIDER_HEIGHT = SMALL_GROUP_DIVIDER_HEIGHT / 2;

    public RulerView(Context context) {
        this(context, null);
    }

    public RulerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RulerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RulerView);
            int value = array.getInt(R.styleable.RulerView_orientation, 0);
            orientation = value == 0;
            array.recycle();
        }
        setBackgroundColor(getResources().getColor(R.color.transparent));

        // 初始化画笔工具
        paint = new Paint();
        paint.setColor(getResources().getColor(R.color.colorAccent));
        paint.setDither(true);
        paint.setAntiAlias(true);
        paint.setTextSize(30);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = 0, width = 0;
        if (orientation) { // 如果是横向尺子
            width = CommonUtils.getScreenWidth();
            height = CommonUtils.dp2px(80);
        } else {
            width = CommonUtils.dp2px(80);
            height = (int) (CommonUtils.getScreenHeight() * 0.8);
        }
        setMeasuredDimension(width, height);
        mTmpRect.set(0, 0, getMeasuredWidth(), getMeasuredHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (orientation) {
            drawHorizontalRuler(canvas);
        } else {
            drawVerticalRuler(canvas);
        }
    }

    private void drawVerticalRuler(Canvas canvas) {
        // 画尺子外框
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(mTmpRect, paint);
        // 每5个像素为一小格，每5个小格为一个小组，每两个小组也就是10个小格为一个大组
        for (int i = 0; i < getMeasuredHeight(); i++) {
            if (i % 50 == 0) { // 画大组分隔
                // 画左边的刻度
                canvas.drawLine(0, i, LARGE_GROUP_DIVIDER_HEIGHT, i, paint);
                // 画右边的刻度
                canvas.drawLine(getMeasuredWidth(), i, getMeasuredWidth() - LARGE_GROUP_DIVIDER_HEIGHT, i, paint);
                if (i > 0) {
                    canvas.drawText(String.valueOf(i), getMeasuredWidth() / 2 - 20, i + 5, paint);
                }
            } else if (i % 25 == 0) { // 画小组分隔
                // 画左边的刻度
                canvas.drawLine(0, i, SMALL_GROUP_DIVIDER_HEIGHT, i, paint);
                // 画右边的刻度
                canvas.drawLine(getMeasuredWidth(), i, getMeasuredWidth() - SMALL_GROUP_DIVIDER_HEIGHT, i, paint);
            } else if (i % 5 == 0) { // 画小格分隔
                // 画左边的刻度
                canvas.drawLine(0, i, UNIT_DIVIDER_HEIGHT, i, paint);
                // 画右边的刻度
                canvas.drawLine(getMeasuredWidth(), i, getMeasuredWidth() - UNIT_DIVIDER_HEIGHT, i, paint);
            }
        }
    }

    private void drawHorizontalRuler(Canvas canvas) {
        // 画尺子外框
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(mTmpRect, paint);
        // 每5个像素为一小格，每5个小格为一个小组，每两个小组也就是10个小格为一个大组
        for (int i = 0; i < getMeasuredWidth(); i++) {
            if (i % 100 == 0) {
                canvas.drawText(String.valueOf(i), i - 20, getMeasuredHeight() / 2, paint);
            }
            if (i % 50 == 0) { // 画大组分隔
                // 画左边的刻度
                canvas.drawLine(i, 0, i, LARGE_GROUP_DIVIDER_HEIGHT, paint);
                // 画右边的刻度
                canvas.drawLine(i, getMeasuredHeight(), i,getMeasuredHeight() - LARGE_GROUP_DIVIDER_HEIGHT, paint);
            } else if (i % 25 == 0) { // 画小组分隔
                // 画左边的刻度
                canvas.drawLine(i, 0, i, SMALL_GROUP_DIVIDER_HEIGHT, paint);
                // 画右边的刻度
                canvas.drawLine(i, getMeasuredHeight(), i,getMeasuredHeight() - SMALL_GROUP_DIVIDER_HEIGHT, paint);
            } else if (i % 5 == 0) { // 画小格分隔
                // 画左边的刻度
                canvas.drawLine(i, 0, i, UNIT_DIVIDER_HEIGHT, paint);
                // 画右边的刻度
                canvas.drawLine(i, getMeasuredHeight(), i,getMeasuredHeight() - UNIT_DIVIDER_HEIGHT, paint);
            }
        }
    }

    public void rotate() {
        this.orientation = !orientation;
        requestLayout();
    }
}
