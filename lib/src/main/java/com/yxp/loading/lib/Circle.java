package com.yxp.loading.lib;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by yanxing on 16/1/7.
 */
public abstract class Circle {
    public final static int defaultColor = Color.parseColor("#00000000");

    protected int x;
    protected int y;
    protected int r;
    protected int color;

    public Circle(int x, int y, int r) {
        this(x, y, r, defaultColor);
    }

    public Circle(int x, int y, int r, int color) {

    }

    public void draw(Canvas canvas, Paint patint) {
    }


}
