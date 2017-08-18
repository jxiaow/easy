package cn.xwj.easyutil.util;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xw on 2017/7/27.
 */

public class ActivityManger {
    private ActivityManger() {
    }

    private static ActivityManger mActivityManger;

    public static ActivityManger getInstance() {
        if (mActivityManger == null) {
            synchronized (ActivityManger.class) {
                if (mActivityManger == null) {
                    mActivityManger = new ActivityManger();
                }
            }
        }
        return mActivityManger;
    }

    private List<Activity> sActivityList = new ArrayList<>();

    public synchronized void remove(Activity activity) {
        if (sActivityList.contains(activity)) {
            sActivityList.remove(activity);
        }
    }

    public synchronized void add(Activity activity) {
        sActivityList.add(activity);
    }

    public void finishAll() {
        for (Activity activity : sActivityList) {
            activity.finish();
        }
        sActivityList.clear();
    }

    public Activity getCurrentActivity() {
        if (sActivityList.isEmpty()) {
            throw new RuntimeException("you has not add Activity");
        }
        return sActivityList.get(sActivityList.size() - 1);
    }


}
