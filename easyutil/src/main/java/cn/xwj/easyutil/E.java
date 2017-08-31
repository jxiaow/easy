package cn.xwj.easyutil;

/**
 * Created by xiaowu on 2017/7/25.
 */

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import cn.xwj.easyutil.helper.CrashHelper;
import cn.xwj.easyutil.util.Action;
import cn.xwj.easyutil.util.ActivityManger;
import cn.xwj.easyutil.util.LogUtil;

/**
 * easyutil-core
 */
public final class E {
    private static Application sApp;
    private static E sE = new E();

    private E() {
    }

    /**
     * init easyUtil
     *
     * @param context context
     * @return E new instance
     */
    public static E init(Application context) {
        sApp = context;
        return sE;
    }

    /**
     * get Context
     *
     * @return Context
     */
    public static Context context() {
        return sApp.getApplicationContext();
    }

    /**
     * start CrashHandler
     *
     * @param packName the packageName folder contains crash file
     * @return E instance
     */
    public E crash(String packName) {
        CrashHelper.getInstance().init(context()).setLogDir(packName);
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

    /**
     * @param activity
     * @return
     */
    public static Action action(Activity activity) {
        return Action.getInstance(activity);
    }

}
