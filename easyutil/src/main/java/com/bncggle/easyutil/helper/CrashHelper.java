package com.bncggle.easyutil.helper;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;

import com.bncggle.easyutil.util.ActivityManger;
import com.bncggle.easyutil.util.ToastUtil;

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
 * Created by xiaowu on 2017/2/15.
 * <p>
 * 程序崩溃处理类
 */

public class CrashHelper implements Thread.UncaughtExceptionHandler {


    //程序崩溃默认处理器
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    //单例
    private static CrashHelper mInstance = new CrashHelper();
    //程序上下文
    private Context mContext;

    //存放设备信息
    private Map<String, String> infos = new HashMap<>();


    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH mm ss");


    private String root = Environment.getExternalStorageDirectory().getAbsolutePath();

    private String logDir = "crash";

    /**
     * 设置日志产生的目录
     *
     * @param logDir
     */
    public void setLogDir(String logDir) {
        this.logDir = logDir;
    }

    private CrashHelper() {

    }

    public static CrashHelper getInstance() {
        return mInstance;
    }


    public CrashHelper init(Context ctx) {
        this.mContext = ctx;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        return mInstance;
    }

    @Override
    public synchronized void uncaughtException(Thread t, Throwable e) {

        if (mDefaultHandler != null && !handleException(e)) {
            mDefaultHandler.uncaughtException(t, e);
        } else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            killProcess();
        }
    }

    private boolean handleException(Throwable e) {
        if (e == null) {
            return false;
        }

        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                ToastUtil.showMsg("程序出现错误，即将退出！");
                Looper.loop();
            }
        }.start();

        //收集设备信息
        collectDeviceInfo();

        //保存log
        saveLogToFile(e);
        return true;
    }

    private String saveLogToFile(Throwable e) {

        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> info : infos.entrySet()) {
            sb.append(info.getKey() + "=" + info.getValue() + "\n");
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
                return fileName;
            } catch (IOException e1) {
                //e1.printStackTrace();
                // killProcess();
            }

        }
        return null;

    }

    private void killProcess() {
        ActivityManger.finishAll();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
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
