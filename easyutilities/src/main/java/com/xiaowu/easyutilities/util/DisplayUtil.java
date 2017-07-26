package com.xiaowu.easyutilities.util;

import com.xiaowu.easyutilities.Easy;

/**
 * Created by xiaowu on 2017/3/17.
 * 屏幕参数相关工具类
 */

public class DisplayUtil {

    public static int dp2px(float dpValue) {

        float density = Easy.getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * density + 0.5f);
    }

    public static int px2dp(float pxValue) {
        float density = Easy.getContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / density + 0.5f);
    }

}
