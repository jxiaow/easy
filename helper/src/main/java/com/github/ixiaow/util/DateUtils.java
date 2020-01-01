package com.github.ixiaow.util;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.text.format.Time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by xw on 2017/10/17.
 */

public class DateUtils {
    @SuppressLint("SimpleDateFormat")
    private static SimpleDateFormat sDateTimeFormat =
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @SuppressLint("SimpleDateFormat")
    private static SimpleDateFormat sDateFormat =
            new SimpleDateFormat("yyyy-MM-dd");

    private static Date sDate = new Date();

    /**
     * @return true if the supplied when is today else false
     */
    public static boolean isToday(long when) {
        Time time = new Time();
        time.set(when);

        int thenYear = time.year;
        int thenMonth = time.month;
        int thenMonthDay = time.monthDay;

        time.set(System.currentTimeMillis());
        return (thenYear == time.year)
                && (thenMonth == time.month)
                && (thenMonthDay == time.monthDay);
    }

    public static String formatDate(long date) {
        sDate.setTime(date);
        return sDateFormat.format(sDate);
    }

    public static String formatCurrentDate() {
        sDate.setTime(System.currentTimeMillis());
        return sDateFormat.format(sDate);
    }

    public static long parserDate(String date) {
        try {
            Date parse = sDateFormat.parse(date);
            return parse.getTime();
        } catch (ParseException e) {
            return 0;
        }
    }

    public static long parserDateTime(String dateTime) {
        return parserDateTime(dateTime, false);
    }

    /**
     * @param dateTime
     * @param isConcat 这是专门针对只有年月日转为年月日时分秒，是否拼接 23：59：59
     * @return
     */
    public static long parserDateTime(String dateTime, boolean isConcat) {
        try {
            if (isConcat) {
                dateTime = dateTime + " 23:59:59";
            }
            return sDateTimeFormat.parse(dateTime).getTime();
        } catch (ParseException e) {
            return 0;
        }
    }

    public static String formatDateTime(String dateTime) {
        if (!TextUtils.isEmpty(dateTime) && TextUtils.isDigitsOnly(dateTime)) {
            return formatDateTime(Long.parseLong(dateTime));
        }
        return "";
    }

    public static String formatDateTime(long dateTime) {
        sDate.setTime(dateTime);
        return sDateTimeFormat.format(sDate);
    }


    public static String formatCurrentDateTime() {
        sDate.setTime(System.currentTimeMillis());
        return sDateTimeFormat.format(sDate);
    }


    public static boolean isToday(String date) {
        long dateLong = parserDate(date);
        return isToday(dateLong);
    }
}
