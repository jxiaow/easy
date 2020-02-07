package com.github.ixiaow.provider;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import androidx.core.content.FileProvider;

import java.io.File;


/**
 * Author: xw
 * Date: 2018-04-12 16:57:31
 * Description: FileProviderHelper: FileProviderHelper 帮助类.
 */
public class FileProviderHelper {

    /**
     * 从文件中获取Uri
     *
     * @param context 上下文
     * @param file    文件
     * @return uri
     */
    public static Uri fromFileUri(Context context, File file) {
        if (Build.VERSION.SDK_INT > 23) {
            return FileProvider.getUriForFile(context,
                    context.getPackageName() + ".easy.provider",
                    file);
        }
        return Uri.fromFile(file);
    }

    /**
     * 给Intent设置data and type
     *
     * @param context     上下文
     * @param intent      意图
     * @param type        文件类型
     * @param file        文件
     * @param isWriteAble 是否对uri 可写
     */
    public static void setIntentDataAndType(Context context, Intent intent, String type,
                                            File file, boolean isWriteAble) {

        intent.setDataAndType(fromFileUri(context, file), type);

        if (Build.VERSION.SDK_INT > 23) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            if (isWriteAble) {
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }
        }
    }
}
