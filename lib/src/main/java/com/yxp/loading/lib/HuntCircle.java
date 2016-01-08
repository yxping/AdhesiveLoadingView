package com.yxp.loading.lib;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by yanxing on 16/1/7.
 */
public class HuntCircle extends Circle {

    public HuntCircle(int x, int y, int r) {
        super(x, y, r);
    }

    public HuntCircle(int x, int y, int r, int color) {
        super(x, y, r, color);
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(color);
        canvas.drawCircle(x, y, r, paint);

    }
}
