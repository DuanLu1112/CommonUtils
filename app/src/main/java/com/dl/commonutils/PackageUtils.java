package com.dl.commonutils;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;

/**
 * APK信息
 * Created by DuanLu on 2016/8/2.
 */
public class PackageUtils {
    private final String TAG = this.getClass().getSimpleName();

    private PackageUtils() {

    }

    public static int getAndroidSDKVersion() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 判断应用程序运行是否运行在后台
     *
     * @param context 上下文对象
     * @return true后台，false前台
     */
    public static boolean isAppOnForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
                    Log.i("后台", appProcess.processName);
                    return true;
                } else {
                    Log.i("前台", appProcess.processName);
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * 获取应用程序名称
     *
     * @param context 上下文对象
     * @return 应用程序名称
     */
    @Nullable
    public static String getAppName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            getVersionName(null);
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 获取应用程序版本名
     *
     * @param context 上下文对象
     * @return 应用程序版本名
     */
    @Nullable
    public static String getVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取应用程序版本号
     *
     * @param context 上下文对象
     * @return 应用程序版本号
     */
    public static int getVersionCode(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 判断手机是否安装微信客户端
     *
     * @param context 上下文对象
     * @return true已安装，false未安装
     */
    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断手机是否已经安装QQ客户端
     *
     * @param context 上下文对象
     * @return true已安装，false未安装
     */
    public static boolean isQQAvilible(Context context) {
        PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        if (packageInfos != null) {
            for (PackageInfo info : packageInfos) {
                if (info.packageName.equals("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 重新启动当前APP
     *
     * @param context
     */
    public void restartAPP(Context context) {
        Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage("");
        context.startActivity(launchIntent);
    }

    /**
     * 延迟millis时间后重新启动APP
     *
     * @param context
     * @param millis
     */
    public void pendingRestartAPP(Context context, long millis) {
        Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage("");
        int requestCode = 0;
        PendingIntent pendingIntent = PendingIntent.getActivity(context, requestCode, launchIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC, System.currentTimeMillis() + millis, pendingIntent);
        AppManager.getInstance().AppExit(context);
    }

}
