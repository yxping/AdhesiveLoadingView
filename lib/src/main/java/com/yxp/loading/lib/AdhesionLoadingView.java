package com.yxp.loading.lib;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by yanxing on 16/1/7.
 */
public class AdhesionLoadingView extends View {
    public final static int DEFAULT_WIDTH = 100;
    public final static int DEFAULT_HEIGHT = 100;

    private ArrayList<Circle> mRabbits = new ArrayList<>();
    private boolean isInit = false;
    private Paint mPaint;

    public AdhesionLoadingView(Context context) {
        super(context);
    }

    public AdhesionLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AdhesionLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AdhesionLoadingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
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
        if (isInit) {
            return;
        }
        isInit = true;
        int r = Math.min(getWidth(), getHeight()) / 10;
        mRabbits.add(new RabbitCircle(getWidth() / 2, getHeight() / 5, r));

        mRabbits.add(new RabbitCircle(getWidth() / 2, getHeight() * 2 / 5, r));

        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLACK);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(getWidth() / 2, getHeight() / 2, 15, mPaint);
        for (Circle circle : mRabbits) {
            circle.draw(canvas, mPaint);
        }
    }
}
