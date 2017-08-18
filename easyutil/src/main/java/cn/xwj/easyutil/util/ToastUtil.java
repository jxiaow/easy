package cn.xwj.easyutil.util;

import android.widget.Toast;

import cn.xwj.easyutil.E;

/**
 * Created by xw on 2017/7/27.
 */

public class ToastUtil {

    private static Toast sToast;

    public static void showMsg(String msg) {
        if (sToast == null) {
            sToast = Toast.makeText(E.getContext(), "", Toast.LENGTH_SHORT);
        }
        sToast.setText(msg);
        sToast.show();
    }
}
