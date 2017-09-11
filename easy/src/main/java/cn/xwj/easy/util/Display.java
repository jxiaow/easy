package cn.xwj.easy.util;

import cn.xwj.easy.E;

/**
 * Created by xiaowu on 2017/3/17.
 * 屏幕参数相关工具类
 */

public class Display{

    public static int dp2px(float dpValue) {
        float density = E.context().getResources().getDisplayMetrics().density;
        return (int) (dpValue * density + 0.5f);
    }

    public static int px2dp(float pxValue) {
        float density = E.context().getResources().getDisplayMetrics().density;
        return (int) (pxValue / density + 0.5f);
    }

    public static int sp2px(float spValue) {
        float scaledDensity = E.context().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * scaledDensity + 0.5f);
    }

    public static int px2sp(float pxValue) {
        float scaledDensity = E.context().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / scaledDensity + 0.5f);
    }
}
