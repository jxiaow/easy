package com.bncggle.easyutil.util;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xw on 2017/7/27.
 */

public class ActivityCollection {
    private static List<Activity> sActivityList = new ArrayList<>();

    public synchronized static void remove(Activity activity) {
        if (sActivityList.contains(activity)) {
            sActivityList.remove(activity);
        }
    }

    public synchronized static void add(Activity activity) {
        sActivityList.add(activity);
    }

    public static void finishAll() {
        for (Activity activity : sActivityList) {
            activity.finish();
        }
        sActivityList.clear();
    }

    public static Activity getCurrentActivity() {
        if (sActivityList.isEmpty()) {
            throw new RuntimeException("you has not add Activity");
        }
        return sActivityList.get(sActivityList.size() - 1);
    }
}
