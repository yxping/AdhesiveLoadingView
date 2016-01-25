package com.yxp.loading.lib;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.yxp.loading.lib.anim.TextAnimator;

import java.util.ArrayList;

/**
 * Created by yanxing on 16/1/7.
 */
public class AdhesionLoadingView extends View {
    public static int DEFAULT_WIDTH = 200;
    public static int EXTRA = DEFAULT_WIDTH / (TextAnimator.STR.length() - 2);
    public static int DEFAULT_HEIGHT = DEFAULT_WIDTH + EXTRA;

    private Controller mController;
    private boolean isInit = false;
    private Paint mPaint;

    public AdhesionLoadingView(Context context) {
        super(context);
        initSize(context);
    }

    public AdhesionLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initSize(context);
    }

    public AdhesionLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initSize(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AdhesionLoadingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initSize(context);
    }

    public void initSize(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        DEFAULT_WIDTH = (int) (DEFAULT_WIDTH * 3 / dm.density);
        EXTRA = DEFAULT_WIDTH / (TextAnimator.STR.length() - 2);
        DEFAULT_HEIGHT = DEFAULT_WIDTH + EXTRA;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = measureDimension(DEFAULT_WIDTH, widthMeasureSpec);
        int height = measureDimension(DEFAULT_HEIGHT, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    public int measureDimension(int defaultSize, int measureSpec) {
        int result;

        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = defaultSize;   //UNSPECIFIED
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    private void init() {
        // TODO 只支持高度大于宽度,否则会出现奇怪的塞贝尔曲线以及动画效果.

        if (isInit) {
            return;
        }
        isInit = true;

        // create Controller
        mController = new Controller(this);

        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLACK);
        mPaint.setAntiAlias(true);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        init();

        mController.draw(canvas, mPaint);
    }
}
