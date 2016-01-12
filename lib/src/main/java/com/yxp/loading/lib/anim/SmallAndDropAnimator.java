package com.yxp.loading.lib.anim;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

import com.yxp.loading.lib.component.BeadCircle;
import com.yxp.loading.lib.component.Circle;
import com.yxp.loading.lib.Config;

/**
 * Created by yanxing on 16/1/12.
 */
public class SmallAndDropAnimator extends ValueAnimator {
    private Circle mCircle;
    private View mView;
    // 水滴
    private BeadCircle mBead;
    private Path mPath;

    public SmallAndDropAnimator(View view, Circle circle) {
        mCircle = circle;
        mView = view;
        initAnim();
        mBead = new BeadCircle(Config.START_X, Config.START_Y, 0);


    }

    public void initAnim() {
        this.setDuration(500);
        this.setIntValues(360 / Config.DEGREE_GAP, 0);
        this.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int level = (int) animation.getAnimatedValue();
                mCircle.changeSize(level);
                mView.invalidate();
            }
        });
    }

    public void draw(Canvas canvas, Paint paint) {
        mBead.draw(canvas, paint);
    }
}
