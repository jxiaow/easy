package cn.xwj.easy.permission;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

import java.lang.ref.WeakReference;


/**
 * 权限申请简单封装类
 * <p>
 */

public class EPermission {
    /**
     * 当前需要请求权限的Activity
     */
    private WeakReference<Activity> mActivity;

    /**
     * 权限申请提示框开关
     */
    private boolean isTipDialog = true;

    /**
     * 权限申请失败，是否引导用户去设置中开启
     */
    private boolean isToSettings = true;

    /**
     * 需要申请权限的数组
     */
    private String[] permissions;

    private EPermission(Activity activity) {
        this.mActivity = new WeakReference<>(activity);
    }

    /**
     * 创建EPermission实例
     *
     * @param activity 需要申请权限的Activity
     * @return EPermission实例
     */
    public static EPermission create(Activity activity) {
        return new EPermission(activity);
    }

    /**
     * 是否开启权限申请前的说明提示框
     *
     * @param tipDialog true开始，false 不开启
     */
    public EPermission setTipDialog(boolean tipDialog) {
        isTipDialog = tipDialog;
        return this;
    }

    /**
     * 权限申请失败，是否引导用户去设置中开启
     *
     * @param toSettings true引导，false 不引导
     */
    public EPermission setToSettings(boolean toSettings) {
        isToSettings = toSettings;
        return this;
    }

    /**
     * 设置需要申请的权限
     *
     * @param permissions 需要申请的权限
     * @return 当前的EPermission 实例
     */
    public EPermission permission(String... permissions) {
        this.permissions = permissions;
        return this;
    }

    /**
     * 请求权限(已包含检查权限)
     *
     * @param permissionResult 权限申请回调接口
     */
    public void request(PermissionResult permissionResult) {
        if (permissions == null) {
            throw new IllegalArgumentException("Permission is not null");
        }
        boolean granted = hasPermission(permissions);//检查所有权限是否都已经授予
        if (!granted) {
            //开启一个activity用于进行授权
            PermissionActivity.setPermissionResult(permissionResult);
            PermissionActivity.actionStart(mActivity.get(), permissions, isTipDialog, isToSettings);
        } else {
            //回调接口
            if (permissionResult != null) {
                permissionResult.onResult(true);
                mActivity = null;
            }
        }
    }

    /**
     * 判断是否已经授予了权限
     *
     * @param permission 需要判断的权限
     * @return true 已经授予，false 未授予
     */
    protected boolean hasPermission(String... permission) {
        for (String p : permission) {
            if (ContextCompat.checkSelfPermission(mActivity.get(), p) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 权限申请回调接口
     */
    public interface PermissionResult {

        /**
         * 权限申请后回调此方法
         */
        void onResult(boolean granted);
    }
}
