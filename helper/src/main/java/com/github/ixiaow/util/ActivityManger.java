package com.github.ixiaow.util;

import android.app.Activity;

import java.util.LinkedList;
import java.util.List;

/**
 * Activity 管理集合
 * <p>
 * Created by xw on 2017/7/27.
 */

public class ActivityManger {

    private List<Activity> sActivityList = new LinkedList<>();
    private static ActivityManger mActivityManger;

    private ActivityManger() {
    }

    /**
     * 获取ActivityManager实例，采用单例模式
     *
     * @return 返回ActivityManger实例
     */
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


    /**
     * 移除Activity
     *
     * @param activity 需要移除的Activity
     */
    public  void remove(Activity activity) {
        if (sActivityList.contains(activity)) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
            sActivityList.remove(activity);
        }
    }

    /**
     * 添加Activity
     *
     * @param activity 需要添加的Activity
     */
    public void add(Activity activity) {
        sActivityList.add(activity);
    }

    /**
     * 结束所有的Activity
     */
    public void finishAll() {
        for (Activity activity : sActivityList) {
            if(!activity.isFinishing()){
                activity.finish();
            }
        }
        sActivityList.clear();
    }

    /**
     * 获取当前Activity
     *
     * @return 获取到的Activity
     */
    public Activity getCurrentActivity() {
        if (sActivityList.isEmpty()) {
            throw new RuntimeException("you has not add Activity");
        }
        return sActivityList.get(sActivityList.size() - 1);
    }
}
