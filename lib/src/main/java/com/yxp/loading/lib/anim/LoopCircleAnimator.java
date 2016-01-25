package com.yxp.loading.lib.anim;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.yxp.loading.lib.component.Circle;
import com.yxp.loading.lib.Config;
import com.yxp.loading.lib.component.RabbitCircle;
import com.yxp.loading.lib.component.WolfCircle;

import java.util.ArrayList;

/**
 * 负责圆圈的旋转,利用度数来绘制六个圆点,同时通过度数来绘制运动的圆点.
 * Created by yanxing on 16/1/12.
 */
public class LoopCircleAnimator extends ValueAnimator {
    public final static int DURATION = Config.DURATION_LOOP;
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

    private Path mPath;
    private int mPathDegree = -1;

    public LoopCircleAnimator(View view) {
        mView = view;

        initComponent();
        initAnimator();

        mPath = new Path();
    }

    private void initComponent() {

        startX = Config.START_X;
        startY = Config.START_Y;
        centerX = Config.CENTER_X;
        centerY = Config.CENTER_Y;
        bigR = Config.BIG_CIRCLE_RADIUS;

        // create 6 rabbits
        int r = Math.min(mView.getWidth(), mView.getHeight()) / 20;
        int degree = 0;
        for (int i = 0; i < Config.RABBIT_NUM; i++) {
            mRabbits.add(new RabbitCircle(startX, startY, r, degree));
            degree += Config.DEGREE_GAP;
        }

        // create wolf
        if (mWolf == null) {
            mWolf = new WolfCircle(startX, startY, (int)(rate * r), 0);
        }

    }

    private void initAnimator() {

        this.setIntValues(0, 360);
        this.setDuration(DURATION);
        this.setInterpolator(new AccelerateDecelerateInterpolator());
        this.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int degree = (int) animation.getAnimatedValue();
                startActivities(degree);

                mView.invalidate();
            }
        });
    }

    private void startActivities(int degree) {
        mWolf.runTo(degree);

        mWolf.bigger(degree / Config.DEGREE_GAP * 2);

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

    /**
     * 黏着效果实现
     *
     * @param distance
     */
    private void updatePath(int distance){
        // TODO 塞贝尔曲线还有一些问题,由于是通过旋转角度实现两个圆点之间的链接,所以会有偏差,现在暂且通过微调解决
        mPath.reset();
        int x1 = startX - distance;
        int y1 = startY - mRabbits.get(0).getRadius() + 2;

        int x2 = startX - distance;
        int y2 = startY + mRabbits.get(0).getRadius() + 1;

        int x3 = startX + distance;
        int y3 = startY + mWolf.getRadius() + 1;

        int x4 = startX + distance;
        int y4 = startY - mWolf.getRadius() + 2;

        int controlX1T4 = startX;
        int controlY1T4 = y1 + distance;
        int controlX2T3 = startX;
        int controlY2T3 = y2 - distance;

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
        paint.setColor(Color.BLACK);
        canvas.save();
        canvas.rotate(mPathDegree, centerX, centerY);
        canvas.drawPath(mPath, paint);
        canvas.restore();
        mPathDegree = -1;
    }

    public WolfCircle getWolf() {
        return mWolf;
    }

    public ArrayList<RabbitCircle> getRabbits() {
        return mRabbits;
    }
}
