package cn.xwj.easyutil.util;

import android.util.Log;

/**
 * Created by xiaowu on 2017/2/15.
 * <p>
 * 日志工具类，根据不同的level可以控制输出
 */

public class LogUtil {

    public static final int LEVEL_NONE = 0;
    public static final int LEVEL_VERBOSE = 1;
    public static final int LEVEL_DEBUG = 2;
    public static final int LEVEL_INFO = 3;
    public static final int LEVEL_WARN = 4;
    public static final int LEVEL_ERROR = 5;

    private static int mLevel = LEVEL_NONE;

    public static void setLevel(int level) {
        mLevel = level;
    }

    public static void setDebug(boolean debug) {
        setLevel(debug ? LEVEL_ERROR : LEVEL_VERBOSE);
    }

    private static String getTag() {
        StackTraceElement call = new Throwable().getStackTrace()[2];
        String tag = "%s.%s (Line: %d)";
        String callClassName = call.getClassName().substring(call.getClassName().lastIndexOf("/") + 1);
        return String.format(tag, callClassName, call.getMethodName(), call.getLineNumber());
    }

    public static void v(String msg) {
        if (mLevel >= LEVEL_VERBOSE) {
            Log.v(getTag(), msg);
        }
    }

    public static void d(String msg) {
        if (mLevel >= LEVEL_DEBUG) {
            Log.d(getTag(), msg);
        }
    }


    public static void i(String msg) {
        if (mLevel >= LEVEL_INFO) {
            Log.i(getTag(), msg);
        }
    }

    public static void w(String msg) {
        if (mLevel >= LEVEL_WARN) {
            Log.w(getTag(), msg);
        }
    }

    public static void e(String msg) {
        if (mLevel >= LEVEL_ERROR) {
            Log.e(getTag(), msg);
        }
    }
}
