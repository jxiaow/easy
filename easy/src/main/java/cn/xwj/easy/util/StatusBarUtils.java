package cn.xwj.easy.util;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import cn.xwj.easy.util.RomUtils;

/**
 * 状态栏工具类
 * <p>
 * Created by xw on 2017/7/27.
 */

public class StatusBarUtils {

    private StatusBarUtils() {
    }

    /**
     * 设置沉寝式状态栏
     * 需要设置主题为Theme.AppCompat.*.NoActionBar,
     * 如果没有生效，请 在自定义布局或者title上加入android:fitsSystemWindows=true
     */
    public static void transparentStatusBar(Activity activity) {
        setAndroidNativeLightStatusBar(activity, false);
    }

    public static void transparentStatusBar(Activity activity, boolean dark) {
        setAndroidNativeLightStatusBar(activity, dark);
    }

    private static void setLightStatusBar(Activity activity, boolean dark) {

        switch (RomUtils.getLightStatausBarAvailableRomType()) {
            case RomUtils.AvailableRomType.MIUI:
                setMIUILightStatusBar(activity, dark);
                break;

            case RomUtils.AvailableRomType.FLYME:
                setFlymeLightStatusBar(activity, dark);
                break;

            case RomUtils.AvailableRomType.ANDROID_NATIVE:
                setAndroidNativeLightStatusBar(activity, dark);
                break;

            case RomUtils.AvailableRomType.NA:
                // N/A do nothing
                break;
        }
    }

    private static boolean setMIUILightStatusBar(Activity activity, boolean darkmode) {
        Class<? extends Window> clazz = activity.getWindow().getClass();
        try {
            int darkModeFlag = 0;
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(activity.getWindow(), darkmode ? darkModeFlag : 0, darkModeFlag);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean setFlymeLightStatusBar(Activity activity, boolean dark) {
        boolean result = false;
        if (activity != null) {
            try {
                WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                activity.getWindow().setAttributes(lp);
                result = true;
            } catch (Exception e) {
            }
        }
        return result;
    }

    private static void setAndroidNativeLightStatusBar(Activity activity, boolean dark) {
        View decorView = activity.getWindow().getDecorView();
        if (Build.VERSION.SDK_INT >= 21) {
            if (decorView != null) {
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
            }
        } else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        if (dark && decorView != null && Build.VERSION.SDK_INT >= 23) {
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }
}
