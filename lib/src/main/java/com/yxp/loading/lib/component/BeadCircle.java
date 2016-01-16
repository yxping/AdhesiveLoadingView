package com.yxp.loading.lib.component;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.yxp.loading.lib.Config;

/**
 * Created by yanxing on 16/1/12.
 */
public class BeadCircle extends Circle {
    private int mOriginR;
    private RectF rectF;
    private boolean isCircle = true;

    public BeadCircle(int x, int y, int r) {
        super(x, y, r);
        mOriginR = r;
        rectF = new RectF(x - radius, y - radius , x + radius , y + radius);
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        if (isCircle) {
            canvas.drawCircle(x, y, radius, paint);
        } else {
            canvas.drawOval(rectF, paint);
        }
    }

    @Override
    public void bigger(int level) {
        radius = mOriginR + level;
    }

    public void drop(int y) {
        this.y = y;
    }

    public void flatten(int r) {
        if (isCircle) {
            isCircle = false;
        }
        rectF.set(x - radius, y - r, x + radius, y + radius);
    }

    /**
     * 恢复原状
     */
    public void reset(int x, int y) {
        radius = mOriginR;
        this.x = x;
        this.y = y;
        isCircle = true;
    }
}
