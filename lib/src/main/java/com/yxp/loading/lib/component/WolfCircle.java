package com.yxp.loading.lib.component;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by yanxing on 16/1/7.
 */
public class WolfCircle extends Circle {
    private int mOriginR;
    private int mouthDegree = 0;

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
//        RectF oval = new RectF(x - radius, y - radius, x + radius, y + radius);
//        canvas.drawArc(oval, mouthDegree / 2, 360 - mouthDegree, true, paint);
//
//        paint.setColor(Color.WHITE);
//        canvas.drawCircle(x + radius / 3, y - radius / 2, 2, paint);
        canvas.restore();
    }

    public void runTo(int degree) {
        if (degree < 360) {
            this.degree = degree;
//            eat(degree);
        } else {
            this.degree = 0;
        }
    }

    public void eat(int degree) {
        mouthDegree = degree % 59;
    }

    /**
     * change the radius to change size
     * @param size
     */
    @Override
    public void smaller(int size) {
        radius = mOriginR - size;
    }

    @Override
    public void bigger(int size) {
        radius = mOriginR + size;
    }

    public int getOriginR() {
        return mOriginR;
    }
}
