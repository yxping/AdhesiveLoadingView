package com.yxp.loading.lib.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.yxp.loading.lib.Config;

/**
 * 字体弹出,向外移动的效果
 * Created by yanxing on 16/1/13.
 */
public class TextAnimator extends ValueAnimator {
    public final static int DURATION = Config.DURATION_TEXT;
    public final static String STR = "Loading";
    public String[] word;
    /**
     * 当前绘制到的位置
     */
    private int curIndex;
    private View mView;
    private int mTextSize;
    private int curTextSize;
    private int mBaseLine;
    /**
     * 字体缩放的额外距离
     */
    private int mScaleSize;
    private Text[] texts;
    private Paint mPaint;

    public TextAnimator(View view) {
        mView = view;
        initConfig();
        initAnim();
    }

    /**
     * 初始化参数
     */
    private void initConfig() {
        initWord();
        curIndex = 0;
        mTextSize = mView.getWidth() / (STR.length() - 2);
        mBaseLine = Config.BASELINE;
        mScaleSize = 30;
        mPaint = new Paint();
        mPaint.setTextSize(mTextSize);
        texts = new Text[word.length];
        // 初始化各个字母运动的方向
        boolean toRight = false;
        for (int i = 0; i < word.length; i++) {
            // 向左运动
            if (!toRight) {
                texts[i] = new Text(word[i], 0, Config.CENTER_X, Text.DIRECTION_LEFT);
                toRight = true;
            } else {
                if (i + 1 == word.length) {
                    // 居中不动
                    texts[i] = new Text(word[i], 0, Config.CENTER_X, Text.DIRECTION_CENTER);
                } else {
                    // 向右运动
                    texts[i] = new Text(word[i], 0, Config.CENTER_X, Text.DIRECTION_RIGHT);
                }
                toRight = false;
            }

        }
    }

    /**
     * 初始化word的顺序,例如loading->lgonaid,方便进行动画
     */
    protected void initWord() {
        word = new String[STR.length()];
        int a = 0;
        int b = word.length - 1;
        int i = 0;
        while (a <= b) {
            if (a == b) {
                word[i] = String.valueOf(STR.charAt(a));
            } else {
                word[i] = String.valueOf(STR.charAt(a));
                word[i + 1] = String.valueOf(STR.charAt(b));
            }
            a++;
            b--;
            i += 2;
        }
    }

    protected void initAnim() {
        // 通过设置参数来实现字体的大小变化,从而实现弹出放大的效果.
        this.setIntValues(0, mTextSize, mTextSize + mScaleSize, 0, mTextSize + mScaleSize / 2, mTextSize);
        this.setDuration(DURATION);
        this.setInterpolator(new AccelerateInterpolator());
        this.addUpdateListener(new AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                curTextSize = (int) animation.getAnimatedValue();
                if (curIndex > 0) {
                    texts[curIndex - 1].setSize(curTextSize);
                }
                mView.invalidate();
            }
        });
        this.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                // 定格最后字母的大小
                if (curIndex > 0 && curIndex <= word.length) {
                    texts[curIndex - 1].setSize(mTextSize);
                }
                int tmpIndex = curIndex;
                while (tmpIndex - 3 >= 0) {
                    texts[tmpIndex - 3].setExtraX(mPaint.measureText(texts[curIndex - 1].content));
                    tmpIndex -= 2;
                }
                curIndex++;
                if (curIndex > word.length) {
                    curIndex = 1;
                    resetText();
                }
            }
        });
    }

    public void draw(Canvas canvas, Paint paint) {
        if (curIndex < 1 || curIndex > word.length) {
            return;
        }
        for (int i = 0; i < curIndex; i++) {
            paint.setTextSize(texts[i].size);
            if (i == curIndex - 1) {
                paint.setTextAlign(Paint.Align.CENTER);
                // 绘制中间的字母
                canvas.drawText(texts[i].content, texts[i].x, mBaseLine, paint);
            } else {
                // 由于文字绘制的原点影响了文字的间距,因为文字的宽度都是通过align.right进行一个间距的计算的,所以
                // 当以中心为绘制原点的时候,相同的间距会变成原来的一半,这样就会导致间距缩小,尤其是小字体像i等,所以通过
                // 设置不同的绘制原点,加上不同的位移来解决这个问题.
                paint.setTextAlign(Paint.Align.LEFT);
                    if (texts[i].direction == Text.DIRECTION_RIGHT) {
                        canvas.drawText(texts[i].content,
                                texts[i].x + paint.measureText(word[curIndex - 1]) / 2 + texts[i].extraX, mBaseLine, paint);
                    } else if (texts[i].direction == Text.DIRECTION_LEFT) {
                        canvas.drawText(texts[i].content,
                                texts[i].x - paint.measureText(word[i]) - paint.measureText(word[curIndex - 1]) / 2 + texts[i].extraX, mBaseLine, paint);
                    }
            }
        }
    }

    /**
     * 重置文字的距离
     */
    private void resetText() {
        if (texts != null) {
            for (Text text : texts) {
                text.extraX = 0;
            }
        }
    }

    /**
     * 字母状态
     */
    private class Text{
        public final static int DIRECTION_RIGHT = 1;
        public final static int DIRECTION_LEFT = 2;
        public final static int DIRECTION_CENTER = 3;

        public String content;
        public int size;
        public float x;
        public int direction;
        public float extraX;

        public Text(String content, int size , float x, int direction) {
            this.content = content;
            this.size = size;
            this.x = x;
            this.direction = direction;
            this.extraX = 0;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public void setExtraX(float extra) {
            if (direction == DIRECTION_LEFT) {
                this.extraX -= extra;
            } else if (direction == DIRECTION_RIGHT) {
                this.extraX += extra;
            }
        }
    }
}
