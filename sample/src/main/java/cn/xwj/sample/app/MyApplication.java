package cn.xwj.sample.app;

import android.app.Application;

import com.bncggle.easyutil.E;

/**
 * Created by xw on 2017/7/27.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        E.init(this).setDebug(true);

        //开启CrashHandler
        // E.init(this).crashHandler("EasyUtil").setDebug(true);
    }
}
