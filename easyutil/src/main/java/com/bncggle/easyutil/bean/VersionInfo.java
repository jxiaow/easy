package com.bncggle.easyutil.bean;

/**
 * Created by xw on 2017/7/27.
 */

public class VersionInfo {
    private int versionCode;
    private String versionName;

    public VersionInfo(int versionCode, String versionName) {
        this.versionCode = versionCode;
        this.versionName = versionName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }
}
