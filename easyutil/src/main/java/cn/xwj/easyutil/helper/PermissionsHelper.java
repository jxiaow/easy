package cn.xwj.easyutil.helper;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

import cn.xwj.easyutil.util.ToastUtil;


/**
 * Created by xw on 2017/7/28.
 */

public class PermissionsHelper {

    private int mRequestCode;
    private Activity mActivity;

    public PermissionsHelper(Activity activity) {
        this.mActivity = activity;
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
                        //Intent intent = new Intent();
                        // intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        // intent.setData(Uri.parse("package:" + mActivity.getPackageName()));
                        // mActivity.startActivity(intent);
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
}
