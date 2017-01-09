package com.dl.commonutil;

/**
 * Created by DuanDaZhi on 2016/11/10 16:59
 *
 * @author DuanLu
 * @version 1.0.0
 * @class LogUtils
 * @describe 日志工具类
 */
public class LogUtils {
    public static String TAG = "LogUtils";
    public static boolean isDebug = true;

    private LogUtils() {

    }

    public static void v(String msg) {
        LogUtils.v(TAG, msg);
    }

    public static void v(String tag, String msg) {
        if (isDebug)
            try {
                android.util.Log.v(tag, msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public static void v(String tag, String msg, Throwable t) {
        if (isDebug)
            try {
                android.util.Log.v(tag, msg, t);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public static void d(String msg) {
        LogUtils.d(TAG, msg);
    }

    public static void d(String tag, String msg) {
        if (isDebug)
            android.util.Log.d(tag, msg);
    }

    public static void d(String tag, String msg, Throwable t) {
        if (isDebug)
            try {
                android.util.Log.d(tag, msg, t);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public static void i(String msg) {
        LogUtils.i(TAG, msg);
    }

    public static void i(String tag, String msg) {
        if (isDebug)
            try {
                android.util.Log.i(tag, msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public static void i(String tag, String msg, Throwable t) {
        if (isDebug)
            try {
                android.util.Log.i(tag, msg, t);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public static void w(String msg) {
        LogUtils.w(TAG, msg);
    }

    public static void w(String tag, String msg) {
        if (isDebug)
            try {
                android.util.Log.w(tag, msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public static void w(String tag, String msg, Throwable t) {
        if (isDebug)
            try {
                android.util.Log.w(tag, msg, t);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public static void e(String msg) {
        LogUtils.e(TAG, msg);
    }

    public static void e(String tag, String msg) {
        if (isDebug)
            try {
                android.util.Log.e(tag, msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public static void e(String tag, String msg, Throwable t) {
        if (isDebug)
            try {
                android.util.Log.e(tag, msg, t);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

}
