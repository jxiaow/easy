package com.bncggle.easyutil;

/**
 * Created by xiaowu on 2017/7/25.
 */

import android.content.Context;

import com.bncggle.easyutil.util.CrashHandler;


public class E {

    private static Context sContext;

    public static void init(Context context) {
        sContext = context;
    }

    public static Context getContext() {
        return sContext;
    }

    /**
     * start CrashHandler
     *
     * @param start
     */
    public static void startCrashHandler(boolean start) {
        if (start) {
            CrashHandler.getInstance().init(sContext);
        }
    }

}
