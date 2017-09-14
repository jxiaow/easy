package cn.xwj.easy.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;

import cn.xwj.easy.E;

/**
 * Created by xw on 2017/7/27.
 */

public class EView {

    private static volatile EView instance;

    private EView() {
    }

    public synchronized static EView getInstance() {
        if (instance == null) {
            instance = new EView();
        }
        return instance;
    }

    /**
     * 设置沉寝式状态栏
     *  需要设置主题为Theme.AppCompat.Light.NoActionBar 才能生效
     */
    public void transparentStatusBar() {
        Context context = E.context();
        if (!(context instanceof Activity)) {
            throw new IllegalArgumentException("context is not activity");
        }
        if (Build.VERSION.SDK_INT >= 21 && context != null) {
            Activity activity = (Activity) context;
            View decorView = activity.getWindow().getDecorView();
            if (decorView != null) {
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
            }
        }
    }

    public int dp2px(float dpValue) {
        float density = E.context().getResources().getDisplayMetrics().density;
        return (int) (dpValue * density + 0.5f);
    }

    public int px2dp(float pxValue) {
        float density = E.context().getResources().getDisplayMetrics().density;
        return (int) (pxValue / density + 0.5f);
    }

    public int sp2px(float spValue) {
        float scaledDensity = E.context().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * scaledDensity + 0.5f);
    }

    public int px2sp(float pxValue) {
        float scaledDensity = E.context().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / scaledDensity + 0.5f);
    }
}
