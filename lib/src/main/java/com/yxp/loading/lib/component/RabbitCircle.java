package com.yxp.loading.lib.component;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.Random;

/**
 * Created by yanxing on 16/1/7.
 */
public class RabbitCircle extends Circle {
    public final static int DIED = 1;
    public final static int ALIVE = 2;
    public final static int DANGER = 3;
    /**
     * 记录当前状态,不同状态执行不同的动作
     */
    private int state = DIED;

    /**
     * 震动的偏移距离
     */
    private int mVibration = 4;
    private int mVibrationMax = 2 * mVibration + 1;
    private Random mRandom = new Random();

    private int mShiftX;
    private int mShiftY;

    public RabbitCircle(int x, int y, int r, int degree) {
        super(x, y, r, degree);
    }

    @Override
    public void draw(Canvas canvas, Paint paint, int cx, int cy) {
        if (state == ALIVE) {
            canvas.save();
            canvas.rotate(degree, cx, cy);
            paint.setColor(color);
            canvas.drawCircle(x, y, radius, paint);
            canvas.restore();
        } else if (state == DANGER) {
            // 当处于危险状态的时候进行震动的绘制
            mShiftX = mRandom.nextInt(mVibrationMax) - mVibration;
            mShiftY = mRandom.nextInt(mVibrationMax) - mVibration;
            canvas.save();
            canvas.rotate(degree, cx, cy);
            paint.setColor(color);
            canvas.drawCircle(x + mShiftX, y + mShiftY, radius, paint);
            canvas.restore();
        }
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }
}
