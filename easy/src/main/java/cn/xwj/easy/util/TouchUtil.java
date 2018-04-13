package cn.xwj.easy.util;

import android.view.View;

/**
 * Created by xw on 2017/10/18.
 */

public class TouchUtil {

    /**
     * (x,y)是否在view的区域内
     * @param view 需要判断的view
     * @param x x坐标点
     * @param y y坐标点
     * @return true or false
     */
    public static boolean isTouchPointInView(View view, int x, int y) {
        if (view == null) {
            return false;
        }
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int left = location[0];
        int top = location[1];
        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();
        if (y >= top && y <= bottom && x >= left
                && x <= right) {
            return true;
        }
        return false;
    }
}
