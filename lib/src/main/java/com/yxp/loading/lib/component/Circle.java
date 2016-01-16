package com.yxp.loading.lib.component;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by yanxing on 16/1/7.
 */
public abstract class Circle {
    public final static int defaultColor = Color.parseColor("#000000");

    protected int x;
    protected int y;
    protected int radius;
    protected int color;
    // for the canvas's rotation
    protected int degree;

    public Circle () {

    }

    public Circle(int x, int y, int r) {
        this(x, y, r, 0);
    }

    public Circle(int x, int y, int r, int degree) {
        this(x, y, r, degree, defaultColor);
    }

    public Circle(int x, int y, int r, int degree, int color) {
        this.x = x;
        this.y = y;
        this.radius = r;
        this.color = color;
        this.degree = degree;
    }

    /**
     * 提供绕圆心旋转画图
     * @param canvas
     * @param paint
     * @param cx
     * @param cy
     */
    public void draw(Canvas canvas, Paint paint, int cx, int cy) {
    }

    public void draw(Canvas canvas, Paint paint) {

    }

    public void bigger(int size) {

    }

    public void smaller(int size) {

    }
    public int getDegree() {
        return degree;
    }

    public int getRadius() {
        return radius;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
