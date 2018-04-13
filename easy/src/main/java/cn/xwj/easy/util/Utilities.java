package cn.xwj.easy.util;

import java.util.Locale;

/**
 * Author: xw
 * Date: 2018-04-12 13:30:42
 * Description: Utilities: .
 */
public class Utilities {
    public static String formatString(String string, Object... args) {
        return String.format(Locale.CHINA, string, args);
    }

    public static String combineText(String startStr, String symbol, String endStr) {
        return formatString("%s %s %s", startStr, symbol, endStr);
    }

}
