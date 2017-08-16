package com.bncggle.easyutil.util;

import android.content.Intent;

import com.bncggle.easyutil.E;

/**
 * Created by xw on 2017/7/27.
 */

public class ActionUtil {

    public static <T> void actionStart(Class<T> tClass) {
        Intent intent = new Intent(E.activity().getCurrentActivity(), tClass);
        E.activity().getCurrentActivity().startActivity(intent);
    }
}
