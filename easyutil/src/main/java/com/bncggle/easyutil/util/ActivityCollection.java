package com.bncggle.easyutil.util;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xw on 2017/7/27.
 */

public class ActivityCollection {
    private static List<Activity> sActivityList = new ArrayList<>();

    public static void remove(Activity activity) {
        if (sActivityList.contains(activity)) {
            sActivityList.remove(activity);
        }
    }

    public static void add(Activity activity){
        sActivityList.add(activity);
    }
}
