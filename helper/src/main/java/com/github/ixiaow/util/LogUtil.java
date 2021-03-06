package com.github.ixiaow.util;

import android.annotation.SuppressLint;
import android.util.Log;

/**
 * Created by xiaowu on 2017/2/15.
 * <p>
 * 日志工具类，根据不同的level可以控制输出
 */

public class LogUtil {

    /**
     * 级别最低，不会打印日志
     */
    public static final int LEVEL_NONE = 0;
    /**
     * 只打印 VERBOSE级别及以下的日志
     */
    public static final int LEVEL_VERBOSE = 1;
    /**
     * 只打印 DEBUG级别及以下的日志
     */
    public static final int LEVEL_DEBUG = 2;
    /**
     * 只打印 INFO级别及以下的日志
     */
    public static final int LEVEL_INFO = 3;
    /**
     * 只打印 WARN级别及以下的日志
     */
    public static final int LEVEL_WARN = 4;
    /**
     * 只打印 ERROR级别及以下的日志
     */
    public static final int LEVEL_ERROR = 5;

    private static int mLevel = LEVEL_ERROR;

    /**
     * 设置日志级别
     * @param level
     */
    public static void setLevel(int level) {
        mLevel = level;
    }

    /**
     * 设置是否输出日志
     * @param debug
     */
    public static void setDebug(boolean debug) {
        setLevel(debug ? LEVEL_ERROR : LEVEL_VERBOSE);
    }

    /**
     * 获取TAG
     * @return 返回tag
     */
    @SuppressLint("DefaultLocale")
    public static String getTag() {
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
