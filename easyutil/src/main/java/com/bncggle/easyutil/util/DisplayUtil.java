package com.bncggle.easyutil.util;

import com.bncggle.easyutil.E;

/**
 * Created by xiaowu on 2017/3/17.
 * 屏幕参数相关工具类
 */

public class DisplayUtil {

    public static int dp2px(float dpValue) {

        float density = E.getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * density + 0.5f);
    }

    public static int px2dp(float pxValue) {
        float density = E.getContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / density + 0.5f);
    }

    public static int sp2px(float spValue) {
        float scaledDensity = E.getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * scaledDensity + 0.5f);
    }

    public static int px2sp(float pxValue) {
        float scaledDensity = E.getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / scaledDensity + 0.5f);
    }
}
