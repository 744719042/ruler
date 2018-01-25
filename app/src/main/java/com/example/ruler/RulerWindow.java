package com.example.ruler;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by Administrator on 2018/1/19.
 */

public class RulerWindow {
    private WindowManager windowManager;
    private WindowManager.LayoutParams layoutParams;
    private RulerView ruler;

    public RulerWindow() {
        this.windowManager = (WindowManager) RulerApplication.getContext().getSystemService(Context.WINDOW_SERVICE);
        this.ruler = new RulerView(RulerApplication.getContext());
        layoutParams = new WindowManager.LayoutParams();
        ruler.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        layoutParams.x = CommonUtils.dp2px(50);
        layoutParams.y = CommonUtils.dp2px(50);
        layoutParams.width = ruler.getMeasuredWidth();
        layoutParams.height = ruler.getMeasuredHeight();
        layoutParams.gravity = Gravity.TOP | Gravity.LEFT;
        layoutParams.format = PixelFormat.RGBA_8888;
        layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;

        initRuler();
    }

    private void initRuler() {
        ruler.setOnTouchListener(new View.OnTouchListener() {
            private int lastX;
            private int lastY;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x = (int) event.getRawX(), y = (int) event.getRawY();
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        lastX = x;
                        lastY = y;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int deltaX = x - lastX;
                        int deltaY = y - lastY;
                        layoutParams.x += deltaX;
                        layoutParams.y += deltaY;
                        windowManager.updateViewLayout(ruler, layoutParams);
                        lastX = x;
                        lastY = y;
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return false;
            }
        });
    }

    public void show() {
        try {
            windowManager.addView(ruler, layoutParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hide() {
        try {
            windowManager.removeView(ruler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void rotate() {
        ruler.rotate();
        ruler.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        layoutParams.x = CommonUtils.dp2px(50);
        layoutParams.y = CommonUtils.dp2px(50);
        layoutParams.width = ruler.getMeasuredWidth();
        layoutParams.height = ruler.getMeasuredHeight();
        windowManager.updateViewLayout(ruler, layoutParams);
    }

    public void destroy() {
        hide();
        ruler = null;
        layoutParams = null;
        windowManager = null;
    }
}
