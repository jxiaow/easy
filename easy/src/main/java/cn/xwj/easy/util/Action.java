package cn.xwj.easy.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by xw on 2017/7/27.
 */

public class Action {

    private Activity mActivity;
    private static Action sAction;

    private Action() {
    }

    private Action(Activity activity) {
        this.mActivity = activity;
    }

    public static Action getInstance(Activity activity) {
        if (sAction == null) {
            synchronized (Action.class) {
                if (sAction == null) {
                    sAction = new Action(activity);
                }
            }
        }
        sAction.mActivity = activity;
        return sAction;
    }

    public <T> void actionStart(Class<T> tClass) {
        actionStart(mActivity, tClass, null);
    }

    public <T> void actionStart(Class<T> tClass, Bundle bundle) {
        actionStart(mActivity, tClass, bundle);
    }


    public <T> void actionStart(Context context, Class<T> tClass, Bundle bundle) {
        if (context == null) {
            throw new IllegalArgumentException("context is null");
        }
        Intent intent = new Intent(context, tClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }
}
