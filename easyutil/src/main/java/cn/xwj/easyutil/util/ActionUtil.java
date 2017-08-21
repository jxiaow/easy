package cn.xwj.easyutil.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import cn.xwj.easyutil.E;

/**
 * Created by xw on 2017/7/27.
 */

public class ActionUtil {

    public static ActionUtil getInstance() {
        return new ActionUtil();
    }

    public <T> void actionStart(Class<T> tClass) {
        if (E.getContext() == null) {
            throw new IllegalStateException("E is not init");
        }
        actionStart(E.getContext(), tClass, null);
    }

    public <T> void actionStart(Class<T> tClass, Bundle bundle) {
        if (E.getContext() == null) {
            throw new IllegalStateException("E is not init");
        }
        actionStart(E.getContext(), tClass, bundle);
    }

    public <T> void actionStart(Context context, Class<T> tClass, Bundle bundle) {
        if (context == null) {
            throw new IllegalArgumentException("Context context is null");
        }
        Intent intent = new Intent(context, tClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }
}
