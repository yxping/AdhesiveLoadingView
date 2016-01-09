package com.yxp.loading.lib;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by yanxing on 16/1/7.
 */
public class WolfCircle extends Circle {
    private int mOriginR;

    public WolfCircle() {
    }

    public WolfCircle(int x, int y, int r) {
        super(x, y, r);
    }

    public WolfCircle(int x, int y, int r, int degree) {
        super(x, y, r, degree);
        mOriginR = radius;
    }

    public WolfCircle(int x, int y, int r, int degree, int color) {
        super(x, y, r, degree, color);
    }

    @Override
    public void draw(Canvas canvas, Paint paint, int cx, int cy) {
        canvas.save();
        canvas.rotate(degree, cx, cy);
        paint.setColor(color);
        canvas.drawCircle(x, y, radius, paint);
        canvas.restore();
    }

    public void runTo(int degree) {
        if (degree < 360) {
            this.degree = degree;
        } else {
            this.degree = 0;
        }
    }

    public void changeSize(int level) {
        radius = mOriginR + level * 2;
    }

    public int getOriginR() {
        return mOriginR;
    }
}
