package cn.xwj.easy.helper;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

import cn.xwj.easy.util.ToastUtil;


/**
 * Created by xw on 2017/7/28.
 */

public class PermissionsHelper {

    private int mRequestCode;
    private Activity mActivity;
    private static PermissionsHelper sHelper;

    private PermissionsHelper() {

    }

    private PermissionsHelper(Activity activity) {
        this.mActivity = activity;

    }

    public static PermissionsHelper create(Activity activity) {
        if (sHelper == null) {
            synchronized (PermissionsHelper.class) {
                if (sHelper == null) {
                    sHelper = new PermissionsHelper(activity);
                }
            }
        }
        if (sHelper != null
                && sHelper.mActivity != null && sHelper.mActivity != activity) {
            sHelper = new PermissionsHelper(activity);
        }
        return sHelper;
    }


    public PermissionsHelper checkPermission(String... permissions) {
        boolean granted = hasPermission(permissions);
        if (!granted) {
            //shouldShowRequestPermissionRationale();
        } else {

        }
        return sHelper;
    }


    public boolean hasPermission(String... permission) {
        for (String p : permission) {
            if (ContextCompat.checkSelfPermission(mActivity, p) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


    public void shouldShowRequestPermissionRationale(int requestCode, String... permissions) {
        boolean isNeed = false;
        for (String permission : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, permission)) {
                isNeed = true;
                break;
            }
        }

        if (isNeed) {
            showPermissionConfigDialog(permissions);
        } else {
            requestPermission(requestCode, permissions);
        }
    }

    public void requestPermission(int code, String... permissions) {
        this.mRequestCode = code;
        ActivityCompat.requestPermissions(mActivity, permissions, code);
    }

    public void showPermissionConfigDialog(final String... permissions) {
        final AlertDialog dialog = new AlertDialog.Builder(mActivity)
                .setTitle("权限设置")
                .setMessage("亲, 您拒绝了权限，需要您授予")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ToastUtil.showMsg("权限被拒绝，程序退出");
                        mActivity.finish();
                    }
                }).setPositiveButton("授予", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        requestPermission(mRequestCode, permissions);
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();
    }


    public boolean parserRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (mRequestCode == requestCode && grantResults != null && grantResults.length > 0) {
            for (int result : grantResults) {
                if (result == PackageManager.PERMISSION_DENIED) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }


    interface IPermission {
        void onGranted();

        void onFail();
    }
}
