package com.github.ixiaow.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 程序崩溃处理类
 * 会收集相关的信息，存入文件中或者上传至服务器
 * <p>
 * Created by xw on 2017/2/15.
 */

public class CrashHelper implements Thread.UncaughtExceptionHandler {

    //程序崩溃默认处理器
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    //单例
    private static CrashHelper sInstance;
    //程序上下文
    private Context mContext;
    //存放设备信息
    private Map<String, String> infos = new HashMap<>();
    @SuppressLint("SimpleDateFormat")
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH mm ss");
    private String root = Environment.getExternalStorageDirectory().getAbsolutePath();
    private String logDir = "crash";
    private static final String CRASH_FILE_PATH = "crash_file_path";

    /**
     * 设置日志产生的根目录
     *
     * @param logDir
     */
    public void setLogDir(String logDir) {
        this.logDir = logDir;
    }

    private CrashHelper() {
    }

    /**
     * 获取CrashHelper对象
     *
     * @return 获取CrashHelper对象
     */
    public static CrashHelper getInstance() {
        if (sInstance == null) {
            synchronized (CrashHelper.class) {
                if (sInstance == null) {
                    sInstance = new CrashHelper();
                }
            }
        }
        return sInstance;
    }


    public void init(Context ctx) {
        this.mContext = ctx;
        String packageName = ctx.getPackageName();
        this.logDir = packageName.substring(packageName.lastIndexOf(".") + 1);
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 截获Exception并处理
     *
     * @param t
     * @param e
     */
    @Override
    public synchronized void uncaughtException(Thread t, Throwable e) {
        handleException(e);
        if (mDefaultHandler != null) {
            mDefaultHandler.uncaughtException(t, e);
        } else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    /**
     * 处理异常
     */
    private boolean handleException(Throwable e) {
        if (e == null) {
            return false;
        }
        //收集设备信息
        collectDeviceInfo();

        //保存log
        String filePath = saveLogToFile(e);
        saveFilePath(filePath);
        return true;
    }

    private void saveFilePath(String filePath) {
        SharedPreferences preferences =
                mContext.getSharedPreferences(CRASH_FILE_PATH, Context.MODE_PRIVATE);
        preferences.edit().putString(CRASH_FILE_PATH, filePath).apply();
    }

    /**
     * 获取生成的文件位置
     *
     * @return
     */
    public String getCrashFilePath() {
        return mContext.getSharedPreferences(CRASH_FILE_PATH, Context.MODE_PRIVATE)
                .getString(CRASH_FILE_PATH, "");
    }

    /**
     * 将信息写入文件中
     *
     * @param e
     * @return 返回文件的绝对路径
     */
    public String saveLogToFile(Throwable e) {

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> info : infos.entrySet()) {
            sb.append(info.getKey()).append("=").append(info.getValue()).append("\n");
        }

        StringWriter sw = new StringWriter();
        PrintWriter printWriter = new PrintWriter(sw);
        e.printStackTrace(printWriter);
        Throwable cause = e.getCause();
        while (cause != null) {
            e.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();

        sb.append(sw.toString());

        long timestamp = System.currentTimeMillis();
        String date = dateFormat.format(new Date());
        String fileName = "crash-" + date + "-" + timestamp + ".log";
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File file = new File(root + (root.endsWith("/") ? "" : "/") + logDir + "/crash/");
            if (!file.exists()) {
                file.mkdirs();
            }
            try {
                FileOutputStream fos = new FileOutputStream(file.getAbsolutePath()
                        + (file.getAbsolutePath().endsWith("/") ? "" : "/") + fileName);
                fos.write(sb.toString().getBytes());
                fos.flush();
                fos.close();
                return file.getAbsolutePath();
            } catch (IOException ignore) {
            }

        }
        return null;
    }


    private void collectDeviceInfo() {
        try {
            infos.clear();
            PackageManager pm = mContext.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
            String versionName = pi.versionName == null ? "null" : pi.versionName;
            String versionCode = pi.versionCode + "";

            infos.put("versionName", versionName);
            infos.put("versionCode", versionCode);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                infos.put(field.getName(), field.get(null).toString());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
