package com.yxp.loading.lib.anim;

import android.animation.ValueAnimator;
import android.view.View;

import com.yxp.loading.lib.component.BeadCircle;

/**
 * Created by yanxing on 16/1/13.
 */
public class TextAnimator extends ValueAnimator {
    private View mView;

    public TextAnimator(View view) {
        mView = view;
    }
}
