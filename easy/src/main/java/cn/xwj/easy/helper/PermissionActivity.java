package cn.xwj.easy.helper;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import cn.xwj.easy.R;
import cn.xwj.easy.util.ToastUtil;

public class PermissionActivity extends AppCompatActivity {

    private int mRequestCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        showPermissionConfigDialog("1", "2");
    }

    public void showPermissionConfigDialog(String... permissions) {
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("权限设置")
                .setMessage("亲, 您拒绝了权限，需要您授予")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ToastUtil.showMsg("权限被拒绝，程序退出");
                        PermissionActivity.this.finish();
                    }
                }).setPositiveButton("授予", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // requestPermission(mRequestCode, permissions);
                        dialog.dismiss();
                        PermissionActivity.this.finish();
                    }
                }).create();
        dialog.show();
    }

    public void shouldShowRequestPermissionRationale(int requestCode, String... permissions) {
        boolean isNeed = false;
        for (String permission : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
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
        ActivityCompat.requestPermissions(this, permissions, code);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        parserRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
