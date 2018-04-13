package cn.xwj.easy.util;

import android.content.Context;

/**
 * 显示工具类
 * <p>
 * Created by xw on 2017/12/7.
 */

/**
 * 显示工具类
 * <p>
 * Creat by xw on 2017/12/7.
 */

public class DisplayUtils {


    public static int dp2px(Context context, float dpValue) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * density + 0.5f);
    }

    public static int px2dp(Context context, float pxValue) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / density + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        float scaledDensity = context.getResources()
                .getDisplayMetrics().scaledDensity;
        return (int) (spValue * scaledDensity + 0.5f);
    }

    public static int px2sp(Context context, float pxValue) {
        float scaledDensity = context.getResources()
                .getDisplayMetrics().scaledDensity;
        return (int) (pxValue / scaledDensity + 0.5f);
    }
}