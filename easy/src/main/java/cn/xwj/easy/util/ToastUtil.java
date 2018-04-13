package cn.xwj.easy.util;

import android.content.Context;
import android.widget.Toast;

/**
 * 吐司工具类
 * <p>
 * Created by xw on 2017/12/7.
 */

public class ToastUtil {

    private ToastUtil() {
    }

    private static Toast sToast;

    /**
     * 输出单例吐司
     *
     * @param msg 需要显示的消息
     */
    public static void showMsg(Context context, String msg) {
        if (sToast == null) {
            sToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        }
        sToast.setText(msg);
        sToast.show();
    }
}
