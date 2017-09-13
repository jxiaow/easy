package cn.xwj.easy.helper;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

import cn.xwj.easy.E;


/**
 * Created by xw on 2017/7/28.
 */

public class EPermission {

    private Activity mActivity;
    private static EPermission sHelper;
    private IPermission mPermission;
    private String[] permissions;


    private EPermission() {

    }

    private EPermission(Activity activity) {
        this.mActivity = activity;
    }

    public static EPermission create(Activity activity) {
        if (sHelper == null) {
            synchronized (EPermission.class) {
                if (sHelper == null) {
                    sHelper = new EPermission(activity);
                }
            }
        }
        return sHelper;
    }

    public EPermission checkPermission(String... permissions) {
        this.permissions = permissions;
        return sHelper;
    }

    public void request(IPermission permission) {
        this.mPermission = permission;
        checkPermission();
    }

    private void checkPermission() {
        if (permissions == null) {
            throw new IllegalArgumentException("Permission is not null");
        }
        boolean granted = hasPermission(permissions);//检查所有权限是否都已经授予
        if (!granted) {
            //shouldShowRequestPermissionRationale();
            //shouldShowRequestPermissionRationale(1, permissions);
            //开启一个activity用于进行授权
            E.action(mActivity).actionStart(PermissionActivity.class);
        } else {
            //回调接口
            if (mPermission != null) {
                mPermission.onGranted();
            }
        }
    }


    private boolean hasPermission(String... permission) {
        for (String p : permission) {
            if (ContextCompat.checkSelfPermission(mActivity, p) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


    public interface IPermission {
        void onGranted();

        void onFail();
    }
}
