package com.bncggle.easyutil.util;

import android.content.Context;
import android.content.Intent;

import com.bncggle.easyutil.E;

/**
 * Created by xw on 2017/7/27.
 */

public class ActionUtil {

    public static <T> void actionStart(Class<T> tClass) {
        Intent intent = new Intent(ActivityCollection.getCurrentActvity(), tClass);
        ActivityCollection.getCurrentActvity().startActivity(intent);
    }
}
