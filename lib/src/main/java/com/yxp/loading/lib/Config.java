package com.yxp.loading.lib;

/**
 * Created by yanxing on 16/1/12.
 */
public class Config {
    // 圆点的数量
    public final static int RABBIT_NUM = 6;
    // 每个原点之间的间隔度数
    public final static int DEGREE_GAP = 360 / RABBIT_NUM;
    public static int START_X;
    public static int START_Y;
    public static int CENTER_X;
    public static int CENTER_Y;
    public static int BIG_CIRCLE_RADIUS;
    public static int BASELINE;

    // 旋转时间
    public static int DURATION_LOOP = 1300;
    // 缩小下落时间
    public static int DURATION_DROP = 1000;
    // 字体弹出时间
    public static int DURATION_TEXT = 200;

}
