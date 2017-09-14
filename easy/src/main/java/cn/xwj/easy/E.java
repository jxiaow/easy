package cn.xwj.easy;

/**
 * Created by xiaowu on 2017/7/25.
 */

import android.content.Context;

import cn.xwj.easy.helper.CrashHelper;
import cn.xwj.easy.util.Action;
import cn.xwj.easy.util.ActivityManger;
import cn.xwj.easy.util.EView;
import cn.xwj.easy.util.LogUtil;

public final class E {
    private static Context sContext;

    private E() {
    }

    public static Context context() {
        return sContext;
    }


    public void crash(Context context) {
        sContext = context;
        CrashHelper.getInstance().init(context());
    }

    public void setDebug(boolean isDebug) {
        LogUtil.setDebug(isDebug);
    }


    public static ActivityManger activity() {
        return ActivityManger.getInstance();
    }


    public static Action action(Context context) {
        sContext = context;
        return Action.getInstance();
    }

    public static EView view(Context context) {
        sContext = context;
        return EView.getInstance();
    }

}
