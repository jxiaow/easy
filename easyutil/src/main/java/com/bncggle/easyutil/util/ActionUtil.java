package com.bncggle.easyutil.util;

import android.content.Context;
import android.content.Intent;

/**
 * Created by xw on 2017/7/27.
 */

public class ActionUtil {

    public static <T> void actionStart(Context context, Class<T> tClass) {
        Intent intent = new Intent(context, tClass);
        context.startActivity(intent);
    }
}
