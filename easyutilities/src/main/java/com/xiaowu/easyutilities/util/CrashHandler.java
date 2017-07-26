package com.xiaowu.easyutilities.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.widget.Toast;

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

public class CrashHandler implements Thread.UncaughtExceptionHandler {


    //程序崩溃默认处理器
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    //单例
    private static CrashHandler mInstance = new CrashHandler();
    //程序上下文
    private Context mContext;

    //存放设备信息
    private Map<String, String> infos = new HashMap<String, String>();


    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH mm ss");

    private String logDir = Environment.getExternalStorageDirectory() + "/crash/";


    private CrashHandler() {

    }

    public static CrashHandler getInstance() {
        return mInstance;
    }


    public void init(Context ctx) {
        this.mContext = ctx;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {

        if (mDefaultHandler != null && !handleException(e)) {
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

    private boolean handleException(Throwable e) {
        if (e == null) {
            return false;
        }

        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext, "程序出现错误，即将退出！", Toast.LENGTH_LONG).show();
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
            cause = e.getCause();
        }

        printWriter.close();

        sb.append(sw.toString());

        long timestamp = System.currentTimeMillis();
        String date = dateFormat.format(new Date());
        String fileName = "crash-" + date + "-" + timestamp + ".log";
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File file = new File(fileName);
            if (!file.exists()) {
                file.mkdirs();
            }

            try {
                FileOutputStream fos = new FileOutputStream(logDir + fileName);
                fos.write(sb.toString().getBytes());
                fos.flush();
                fos.close();
                return fileName;
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }
        return null;

    }

    private void collectDeviceInfo() {

        try {
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
