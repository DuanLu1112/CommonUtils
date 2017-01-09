package com.dl.commonutils;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 时间处理工具类
 * Created by DuanLu on 2016/9/14.
 *
 * @version 1.0.0
 */
public class DateUtils {
    private static final String TAG = "DateUtils";

    /**
     * 当前时间的向后延迟postponeMinutes分钟
     *
     * @param postponeMinutes 要延迟多少分钟
     * @return 延迟后的Date格式时间
     */
    public static Date postponeCurrentMinutes(int postponeMinutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, postponeMinutes);
        return calendar.getTime();
    }

    /**
     * 将指定的时间向后延迟postponeMinutes分钟
     *
     * @param millisecond     需要延迟的时间(单位：毫秒)
     * @param postponeMinutes 要延迟多少分钟
     * @return 延迟后的Date格式时间
     */
    public static Date postponeAppointMinutes(long millisecond, int postponeMinutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millisecond);
        calendar.add(Calendar.MINUTE, postponeMinutes);
        return calendar.getTime();
    }

    /**
     * 当前时间的向后延迟postponeHour小时
     *
     * @param postponeHour 要延迟多少小时
     * @return 延迟后的Date格式时间
     */
    public static Date postponeCurrentHour(int postponeHour) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, postponeHour);
        return calendar.getTime();
    }

    /**
     * 将指定的时间向后延迟postponeHour小时
     *
     * @param millisecond  需要延迟的时间(单位：毫秒)
     * @param postponeHour 要延迟多少小时
     * @return 延迟后的Date格式时间
     */
    public static Date postponeAppointHour(long millisecond, int postponeHour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millisecond);
        calendar.add(Calendar.HOUR_OF_DAY, postponeHour);
        return calendar.getTime();
    }

    /**
     * 将指定的时间向后延迟postponeHour小时
     *
     * @param millisecond  需要延迟的时间(单位：毫秒)
     * @param postponeHour 要延迟多少小时
     * @return 延迟后的Date格式时间
     */
    public static Date postponeAppointHourAndMinutes(long millisecond, int postponeHour, int postponeMinutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millisecond);
        calendar.add(Calendar.HOUR_OF_DAY, postponeHour);
        calendar.add(Calendar.MINUTE, postponeMinutes);
        return calendar.getTime();
    }

    /**
     * 将Date格式时间格式化成yyyy-MM-dd HH:mm:ss格式字符串
     *
     * @param date 需要格式化的时间
     * @return 格式化后的String类型字符串
     */
    public static String dateParseLongStr(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(date);
    }

    /**
     * 将Date格式时间格式化成HH:mm格式字符串
     *
     * @param date 需要格式化的时间
     * @return 格式化后的String类型字符串
     */
    public static String dateParseHourAndMinute(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(date);
    }

    /**
     * 小时转换为对应的时段
     *
     * @param date
     * @return
     */
    @NonNull
    public static String convertNowHour2CN(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH", Locale.getDefault());
        String hourString = sdf.format(date);
        int hour = Integer.parseInt(hourString);
        if (hour < 6) {
            return "凌晨";
        } else if (hour >= 6 && hour < 12) {
            return "早上";
        } else if (hour == 12) {
            return "中午";
        } else if (hour > 12 && hour <= 18) {
            return "下午";
        } else if (hour >= 19) {
            return "晚上";
        }
        return "";
    }

    /**
     * 将yyyy-MM-dd HH:mm:ss格式字符串转换为yyyy年MM月dd日格式
     *
     * @param strDate yyyy-MM-dd HH:mm:ss格式字符串
     * @return yyyy年MM月dd日格式字符串
     */
    public static String dataParseYearMonthDay(String strDate) {
        if (TextUtils.isEmpty(strDate)) {
            return "";
        }
        String strFormatDate;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault());
            Date date = strParseDate(strDate);
            strFormatDate = sdf.format(date);
        } catch (Exception e) {
            //e.printStackTrace();
            Log.e(TAG, "e=" + e.getMessage());
            return "";
        }
        return strFormatDate;
    }

    /**
     * 将yyyy-MM-dd HH:mm:ss格式字符串转换为yyyy-MM-dd格式
     *
     * @param strDate yyyy-MM-dd HH:mm:ss格式字符串
     * @return yyyy-MM-dd格式字符串
     */
    public static String dataParseYear_Month_Day(String strDate) {
        if (TextUtils.isEmpty(strDate)) {
            return "";
        }
        String strFormatDate;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = strParseDate(strDate);
            strFormatDate = sdf.format(date);
        } catch (Exception e) {
            //e.printStackTrace();
            Log.e(TAG, "e=" + e.getMessage());
            return "";
        }
        return strFormatDate;
    }

    /**
     * 将字符串转换为Date格式
     *
     * @param strDate yyyy-MM-dd HH:mm:ss格式字符串
     * @return
     */
    private static Date strParseDate(String strDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = null;
        try {
            date = sdf.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 计算两个年份之间的年份差
     *
     * @param startYear yyyy格式年份
     * @return
     */
    public static String difYear(String startYear) {
        if (TextUtils.isEmpty(startYear)) {
            return "";
        }
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(new Date());
        int endYear = endCalendar.get(Calendar.YEAR);
        int difYear = 0;
        try {
            difYear = endYear - Integer.parseInt(startYear) + 1;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return difYear + "";
    }

    /**
     * 转换时间毫秒值
     *
     * @param startYear 格式年份
     * @return
     */
    public static long strToLong(String startYear) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        // 此处会抛异常
        try {
            Date date = sdf.parse(startYear);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
