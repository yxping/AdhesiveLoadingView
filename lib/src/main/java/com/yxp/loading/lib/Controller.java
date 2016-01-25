package com.yxp.loading.lib;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import com.yxp.loading.lib.anim.TextAnimator;
import com.yxp.loading.lib.anim.LoopCircleAnimator;
import com.yxp.loading.lib.anim.SmallAndDropAnimator;

/**
 * Created by yanxing on 16/1/9.
 */
public class Controller {
    private LoopCircleAnimator mLoopCircleAnim;
    private SmallAndDropAnimator mSapAnim;
    private TextAnimator mTextAnim;
    private AnimatorSet mAnimSet;

    public Controller(View view) {

        initConfig(view);

        mAnimSet = new AnimatorSet();
        mLoopCircleAnim = new LoopCircleAnimator(view);
        mSapAnim = new SmallAndDropAnimator(view, mLoopCircleAnim.getWolf());
        mTextAnim = new TextAnimator(view);
        // 顺序播放
        mAnimSet.playSequentially(mLoopCircleAnim, mSapAnim, mTextAnim);
        mAnimSet.start();
        mAnimSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mAnimSet.start();
            }
        });
    }

    public void initConfig(View view) {
        Config.CENTER_X = view.getWidth() / 2;
        Config.CENTER_Y = view.getWidth() / 2;
        Config.START_X = view.getWidth() / 2;
        Config.START_Y = view.getWidth() / 5;
        Config.BIG_CIRCLE_RADIUS = Config.CENTER_X - Config.START_Y;
    }

    public void draw(Canvas canvas, Paint paint) {
        mLoopCircleAnim.draw(canvas, paint);
        mSapAnim.draw(canvas, paint);
        mTextAnim.draw(canvas, paint);
    }

}
