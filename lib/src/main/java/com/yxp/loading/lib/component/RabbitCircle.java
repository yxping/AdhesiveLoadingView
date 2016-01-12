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
    private int state = DIED;
    private Random mRandom = new Random();
    private int mShiftX;
    private int mShiftY;

    public RabbitCircle() {
    }

    public RabbitCircle(int x, int y, int r) {
        super(x, y, r);
    }

    public RabbitCircle(int x, int y, int r, int degree) {
        super(x, y, r, degree);
    }

    public RabbitCircle(int x, int y, int r, int degree, int color) {
        super(x, y, r, degree, color);
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
            mShiftX = mRandom.nextInt(5) - 2;
            mShiftY = mRandom.nextInt(5) - 2;
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
