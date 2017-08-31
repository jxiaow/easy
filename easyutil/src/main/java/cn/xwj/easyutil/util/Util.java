package cn.xwj.easyutil.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import cn.xwj.easyutil.E;
import cn.xwj.easyutil.bean.VersionInfo;

/**
 * Created by xw on 2017/7/27.
 */

public class Util {

    /**
     * 返回当前的版本
     *
     * @return
     */
    public static VersionInfo version() {
        Context context = E.context();
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            int versionCode = packageInfo.versionCode;
            String versionName = packageInfo.versionName;
            return new VersionInfo(versionCode, versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
