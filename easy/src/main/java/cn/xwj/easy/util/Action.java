package cn.xwj.easy.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import cn.xwj.easy.E;

/**
 * Created by xw on 2017/7/27.
 */

public class Action {

    private volatile static Action sAction;

    private Action() {
    }

    public static Action getInstance() {
        if (sAction == null) {
            synchronized (Action.class) {
                if (sAction == null) {
                    sAction = new Action();
                }
            }
        }
        return sAction;
    }

    public <T> void actionStart(Class<T> tClass) {
        actionStart(E.context(), tClass, null);
    }

    public <T> void actionStart(Class<T> tClass, Bundle bundle) {
        actionStart(E.context(), tClass, bundle);
    }


    public <T> void actionStart(Context context, Class<T> tClass, Bundle bundle) {
        if (context == null) {
            throw new IllegalArgumentException("context is null");
        }
        Intent intent = new Intent(context, tClass);

        if (!(context instanceof Activity)) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }
}
