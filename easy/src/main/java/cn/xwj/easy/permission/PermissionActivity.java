package cn.xwj.easy.permission;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import cn.xwj.easy.R;
import cn.xwj.easy.permission.EPermission.PermissionResult;

/**
 * 权限申请的代理Activity
 * 启动模式为：SingleInstance
 */
public final class PermissionActivity extends AppCompatActivity {
    /**
     * 需要申请权限的数组的key
     */
    private static final String PERMISSIONS_KEY = "permissions_key";
    /**
     * 提示框的key
     */
    private static final String IS_TIP_DIALOG = "is_tip_dialog";
    /**
     * 引导用户去设置的key
     */
    private static final String IS_TO_SETTINGS = "is_to_settings";

    private static final int mRequestCode = 1;
    private static PermissionResult sPermissionResult;
    private String[] mPermissions;
    private boolean isTipDialog = true;
    private boolean isToSettings = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        //获取需要申请的权限
        Intent intent = getIntent();
        mPermissions = intent.getStringArrayExtra(PERMISSIONS_KEY);
        isTipDialog = intent.getBooleanExtra(IS_TIP_DIALOG, true);
        isToSettings = intent.getBooleanExtra(IS_TO_SETTINGS, true);
        //开始请求权限
        if (isTipDialog) {
            showPermissionConfigDialog(mPermissions);
        } else {
            requestPermission(mPermissions);
        }
    }

    /**
     * 权限申请提示对话框，先提示，点击授予申请权限，取消则关闭程序
     *
     * @param permissions 权限集合数组
     */
    public void showPermissionConfigDialog(final String... permissions) {
        new AlertDialog.Builder(this)
                .setTitle("权限设置")
                .setMessage("亲, 为了程序可以正常运行，需要您授予些重要权限")
                .setCancelable(false)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        setResult(false);
                    }
                }).setPositiveButton("授予", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                requestPermission(permissions);
                dialogInterface.dismiss();
            }
        }).show();
    }

    /**
     * 权限申请手动设置提示对话框，用户在第一次申请权限拒绝并且勾选了不再提示，就会调用此方法
     */
    public void showPermissionToSettingsDialog() {
        new AlertDialog.Builder(this)
                .setTitle("权限设置")
                .setMessage("亲, 有些权限需要您手动去开启，请按以下步骤：\r\n 权限--> 开启权限")
                .setCancelable(false)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        setResult(false);
                    }
                }).setPositiveButton("去设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivityForResult(intent, mRequestCode);
            }
        }).show();
    }


    /**
     * 判断是否需要手动去设置权限
     * 当用户勾选了禁止后不在提醒后，我们需要手动去开启
     *
     * @param permissions
     * @return true or false
     */
    public boolean shouldShowRequestPermissionRationale(String... permissions) {
        for (String permission : permissions) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 开始申请权限
     *
     * @param permissions
     */
    private void requestPermission(String... permissions) {
        ActivityCompat.requestPermissions(this, permissions, mRequestCode);
    }

    /**
     * 复写 onRequestPermissionsResult，解析请求结果，完成后finish掉当前代理activity
     *
     * @param requestCode  权限申请请求码
     * @param permissions  请求权限合集
     * @param grantResults 权限申请结果集
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        parserRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 解析权限的结果，并回调相应的权限请求接口
     *
     * @param requestCode  权限申请请求码
     * @param permissions  请求权限合集
     * @param grantResults 权限申请结果集
     * @return 返回权限结果是否申请成功， 权限申请结果集全部成功才会返回true
     */
    private boolean parserRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (mRequestCode == requestCode && grantResults != null && grantResults.length > 0) {
            for (int result : grantResults) {
                if (result == PackageManager.PERMISSION_DENIED) {
                    //判断是否已经申请过权限
                    if (isToSettings && shouldShowRequestPermissionRationale(permissions)) {
                        showPermissionToSettingsDialog();
                    } else {
                        setResult(false);
                    }
                    return false;
                }
            }
            setResult(true);
            return true;
        }
        return false;
    }

    /**
     * 去系统设置开启权限后，会回调此方法
     *
     * @param requestCode 请求码
     * @param resultCode  返回码
     * @param data        数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //需要再一次检查
        setResult(hasPermission(mPermissions));
    }

    /**
     * 判断是否已经授予了权限
     *
     * @param permission 需要判断的权限
     * @return true 已经授予，false 未授予
     */
    protected boolean hasPermission(String... permission) {
        for (String p : permission) {
            if (ContextCompat.checkSelfPermission(this, p) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private void setResult(boolean granted) {
        this.finish();
        mPermissions = null;
        if (sPermissionResult != null) {
            sPermissionResult.onResult(granted);
            sPermissionResult = null;
        }
    }

    /**
     * 设置监听回调
     *
     * @param permissionResult 监听回调接口
     */
    public static void setPermissionResult(PermissionResult permissionResult) {
        sPermissionResult = permissionResult;
    }

    public static void actionStart(Context context, String[] permissions,
                                   boolean isTipDialog, boolean isToSettings) {
        Intent intent = new Intent(context, PermissionActivity.class);
        intent.putExtra(PERMISSIONS_KEY, permissions);
        intent.putExtra(IS_TIP_DIALOG, isTipDialog);
        intent.putExtra(IS_TO_SETTINGS, isToSettings);
        context.startActivity(intent);
    }
}
