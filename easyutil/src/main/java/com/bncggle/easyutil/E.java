package com.bncggle.easyutil;

/**
 * Created by xiaowu on 2017/7/25.
 */

import android.content.Context;

import com.bncggle.easyutil.util.CrashHandler;
import com.bncggle.easyutil.util.LogUtil;

/**
 * easyutil-core
 */
public class E {

    private static Context sContext;
    private static E sE = new E();

    /**
     * init easyUtil
     *
     * @param context context
     * @return E new instance
     */
    public static E init(Context context) {
        sContext = context;
        return sE;
    }

    /**
     * get Context
     *
     * @return
     */
    public static Context getContext() {
        return sContext;
    }

    /**
     * start CrashHandler
     *
     * @param packName the packageName folder contains crash file
     * @return
     */
    public E crashHandler(String packName) {
        CrashHandler.getInstance().init(sContext).setLogDir(packName);
        return sE;
    }

    /**
     * set log level
     *
     * @param isDebug true debug, or realease
     * @return
     */
    public E setDebug(boolean isDebug) {
        if (isDebug) {
            LogUtil.setLevel(LogUtil.LEVEL_ERROR);
        } else {
            LogUtil.setLevel(LogUtil.LEVEL_NONE);
        }
        return sE;
    }

}
