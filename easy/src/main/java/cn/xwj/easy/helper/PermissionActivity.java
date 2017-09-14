package cn.xwj.easy.helper;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import cn.xwj.easy.R;
import cn.xwj.easy.util.ToastUtil;

public class PermissionActivity extends AppCompatActivity {

    private static final int mRequestCode = 1;
    private static EPermission.PermissionResult sPermissionResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);

        Intent intent = getIntent();
        String[] permissions = intent.getExtras().getStringArray(EPermission.PermissionResult.PERMISSIONS_KEY);
        request(permissions);
    }

    private void request(String[] permissions) {
        showPermissionConfigDialog(permissions);
    }

    public void showPermissionConfigDialog(final String... permissions) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("权限设置")
                .setMessage("亲, 为了程序可以正常运行，需要您授予些重要权限")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ToastUtil.showMsg("权限被拒绝，程序退出");
                        PermissionActivity.this.finish();
                    }
                }).setPositiveButton("授予", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        requestPermission(permissions);
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();
    }

//    public void shouldShowRequestPermissionRationale(int requestCode, String... permissions) {
//        boolean isNeed = false;
//        for (String permission : permissions) {
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
//                isNeed = true;
//                break;
//            }
//        }
//        if (isNeed) {
//            showPermissionConfigDialog(permissions);
//        } else {
//            requestPermission(permissions);
//        }
//    }

    public void requestPermission(String... permissions) {
        ActivityCompat.requestPermissions(this, permissions, mRequestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        parserRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        this.finish();
    }

    public boolean parserRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (mRequestCode == requestCode && grantResults != null && grantResults.length > 0) {
            for (int result : grantResults) {
                if (result == PackageManager.PERMISSION_DENIED) {
                    if (sPermissionResult != null) {
                        sPermissionResult.onFail();
                    }
                    return false;
                }
            }
            if (sPermissionResult != null) {
                sPermissionResult.onGranted();
            }
            return true;
        }
        return false;
    }

    public static void setIPermission(EPermission.PermissionResult permissionResult) {
        sPermissionResult = permissionResult;
    }
}
