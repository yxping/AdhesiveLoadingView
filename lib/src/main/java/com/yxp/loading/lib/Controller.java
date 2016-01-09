package com.yxp.loading.lib;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import java.util.ArrayList;

/**
 * Created by yanxing on 16/1/9.
 */
public class Controller {
    public final static int RABBIT_NUM = 6;
    public final static int DEGREE_GAP = 360 / RABBIT_NUM;
    private View mView;
    private ArrayList<RabbitCircle> mRabbits = new ArrayList<>();
    private WolfCircle mWolf;

    private int startX;
    private int startY;
    private int centerX;
    private int centerY;
    private int bigR;
    private float rate = 1.2f;

    private int mAliveRabbits = 0;
    private int mRabbitIndex = 0;
    // wolf and rabbit mix when their degree are closing, this is the threshold that they mix
    private int mMixDegree;

    private Path mPath;
    private int mPathDegree = -1;


    public Controller(View view) {
        mView = view;
        centerX = view.getWidth() / 2;
        centerY = view.getHeight() / 2;
        startX = view.getWidth() / 2;
        startY = view.getHeight() / 5;
        initComponent();
        initAnimator();

        bigR = centerY - startY;
        mPath = new Path();
    }

    private void initComponent() {
        // create 6 rabbits
        int r = Math.min(mView.getWidth(), mView.getHeight()) / 20;
        int degree = 0;
        for (int i = 0; i < RABBIT_NUM; i++) {
            mRabbits.add(new RabbitCircle(startX, startY, r, degree));
            degree += DEGREE_GAP;
        }

        // create wolf
        if (mWolf == null) {
            mWolf = new WolfCircle(startX, startY, (int)(rate * r), 0);
        }
    }

    private void initAnimator() {
        final AnimatorSet set = new AnimatorSet();

        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setIntValues(0, 360);
        valueAnimator.setDuration(3000);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int degree = (int) animation.getAnimatedValue();
                startActivities(degree);

                mView.invalidate();
            }
        });
        // for wolf to become small
        ValueAnimator smallAnimator = ValueAnimator.ofInt(360 / DEGREE_GAP, 0);
        smallAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int level = (int) animation.getAnimatedValue();
                mWolf.changeSize(level);
                mView.invalidate();
            }
        });
        smallAnimator.setDuration(500);
        set.play(valueAnimator).before(smallAnimator);
        set.start();
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                set.start();
            }
        });
    }

    private void startActivities(int degree) {
        mWolf.runTo(degree);

        mWolf.changeSize(degree / DEGREE_GAP);

        for (RabbitCircle rabbit : mRabbits) {
            if (mAliveRabbits < 6 && rabbit.getState() == RabbitCircle.DIED
                    && rabbit.getDegree() < degree) {
                rabbit.setState(RabbitCircle.ALIVE);
                mAliveRabbits++;
            }
            if (mWolf.getDegree() - rabbit.getDegree() > 0 && mWolf.getDegree() - rabbit.getDegree() <= 40) {
                float deg = (mWolf.getDegree() - rabbit.getDegree()) / 2f;
                mPathDegree = (int) (deg + rabbit.getDegree());
                int distance = (int) (Math.sin(Math.PI * deg / 180) * bigR);
                updatePath(distance);
            }

            if (rabbit.getDegree() - mWolf.getDegree() > 0 && rabbit.getDegree() - mWolf.getDegree() < 60) {
                rabbit.setState(RabbitCircle.DANGER);
            } else if (rabbit.getState() == RabbitCircle.DANGER) {
                rabbit.setState(RabbitCircle.ALIVE);
            }
        }
    }

    private void updatePath(int distance){
        mPath.reset();
        int x1 = startX - distance;
        int y1 = startY - mRabbits.get(0).getRadius() + 2;

        int x2 = startX - distance;
        int y2 = startY + mRabbits.get(0).getRadius() - 1;

        int x3 = startX + distance;
        int y3 = startY + mWolf.getRadius() - 1;

        int x4 = startX + distance;
        int y4 = startY - mWolf.getRadius() + 1;

        int controlX1T4 = (x1 + x4) / 2;
        int controlY1T4 = (int) (y1 + (x4 - x1) * 0.4f);
        int controlX2T3 = (x2 + x3) / 2;
        int controlY2T3 = (int) (y2 - (x3 - x2) * 0.4f);

        mPath.moveTo(x1, y1);
        mPath.lineTo(x2, y2);
        mPath.quadTo(controlX2T3, controlY2T3, x3, y3);
        mPath.lineTo(x4, y4);
        mPath.quadTo(controlX1T4, controlY1T4, x1, y1);
        mPath.close();
    }

    public void draw(Canvas canvas, Paint paint) {

        for (Circle rabbit : mRabbits) {
            rabbit.draw(canvas, paint, centerX, centerY);
        }

        mWolf.draw(canvas, paint, centerX, centerY);

        if (mPathDegree > 0) {
            drawPath(canvas, paint);
        }

    }

    public void drawPath(Canvas canvas, Paint paint) {
        paint.setColor(Circle.defaultColor);
        canvas.save();
        canvas.rotate(mPathDegree, centerX, centerY);
        canvas.drawPath(mPath, paint);
        canvas.restore();
        mPathDegree = -1;
    }
}
