package cn.xwj.easyutil;

/**
 * Created by xiaowu on 2017/7/25.
 */

import android.content.Context;

import cn.xwj.easyutil.helper.CrashHelper;
import cn.xwj.easyutil.util.ActivityManger;
import cn.xwj.easyutil.util.LogUtil;

/**
 * easyutil-core
 */
public final class E {

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
     * @return Context
     */
    public static Context getContext() {
        return sContext;
    }

    /**
     * start CrashHandler
     *
     * @param packName the packageName folder contains crash file
     * @return E instance
     */
    public E crash(String packName) {
        CrashHelper.getInstance().init(sContext).setLogDir(packName);
        return sE;
    }

    /**
     * set log level
     *
     * @param isDebug true debug, or realease
     * @return E instance
     */
    public E setDebug(boolean isDebug) {
        LogUtil.setDebug(isDebug);
        return sE;
    }

    /**
     * Activity管理
     *
     * @return
     */
    public static ActivityManger activity() {
        return ActivityManger.getInstance();
    }

}
