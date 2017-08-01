package com.bncggle.easyutil.util;

import android.content.Intent;

/**
 * Created by xw on 2017/7/27.
 */

public class ActionUtil {

    public static <T> void actionStart(Class<T> tClass) {
        Intent intent = new Intent(ActivityCollection.getCurrentActivity(), tClass);
        ActivityCollection.getCurrentActivity().startActivity(intent);
    }
}
