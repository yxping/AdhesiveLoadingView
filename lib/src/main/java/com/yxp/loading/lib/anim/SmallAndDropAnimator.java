package com.yxp.loading.lib.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.yxp.loading.lib.component.BeadCircle;
import com.yxp.loading.lib.Config;
import com.yxp.loading.lib.component.WolfCircle;

/**
 * 缩小和下落压缩的动画
 * Created by yanxing on 16/1/12.
 */
public class SmallAndDropAnimator extends ValueAnimator {
    public final static int DURATION = Config.DURATION_DROP;
    private WolfCircle mCircle;
    private View mView;
    // 水滴
    private BeadCircle mBead;
    private Path mPath;
    // 完整下落距离
    private int mDis;
    // 胶着的下落距离
    private int mixDis;

    public SmallAndDropAnimator(View view, WolfCircle circle) {
        mCircle = circle;
        mView = view;
        mixDis = circle.getRadius();
//        mDis = Config.CENTER_Y + Config.BIG_CIRCLE_RADIUS - Config.START_Y + mixDis;
        mDis = view.getHeight() - mixDis;
        Config.BASELINE = mDis;
        mBead = new BeadCircle(Config.START_X, Config.START_Y, 0);
        mPath = new Path();
        initAnim();
    }

    /**
     * 初始化动画的配置
     */
    public void initAnim() {
        this.setDuration(DURATION);
        // flatten distance 水滴放大的距离
        final int flattenDis = mixDis / 4;
        final int preFlattenDis = mixDis - flattenDis;
        this.setInterpolator(new AccelerateInterpolator(1.5f));
        // 由于要形成一个下落压缩的效果,所以在VALUE的设置上通过参数高->低->高->恢复这样的效果来实现
        this.setIntValues(Config.START_Y, Config.START_Y + mixDis, mDis - preFlattenDis, mDis + flattenDis);
        this.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int curY = (int) animation.getAnimatedValue();
                if (curY < mDis  - preFlattenDis) {
                    if (curY <= Config.START_Y + mixDis) {
                        // 缩小
                        mCircle.bigger(mixDis - curY + Config.START_Y);
                        // 放大
                        mBead.bigger(curY - Config.START_Y);
                    }
                    // 下落
                    mBead.drop(curY);
                } else if (curY < mDis){
                    // 压缩
                    mBead.flatten(mDis + flattenDis - curY);
                    // 下落
                    mBead.drop(curY);
                } else {
                    // 压缩
                    mBead.flatten(mDis + flattenDis - curY);
                }
                mView.invalidate();
            }
        });
        this.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mBead.reset(Config.START_X, Config.START_Y);
            }
        });

    }

    public void draw(Canvas canvas, Paint paint) {
        mBead.draw(canvas, paint);
    }

    public BeadCircle getBead() {
        return mBead;
    }
}
