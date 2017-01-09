package com.dl.commonutil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by DuanLu on 2016/11/9 14:21.
 *
 * @author DuanLu
 * @version 1.0.0
 * @class NetWorkUtils
 * @describe 网络相关辅助工具类
 */
public class NetWorkUtils {
    private static final String TAG = "NetWorkUtils";
    public static final int RESULT_OPEN_SETTINGS = 0;

    private NetWorkUtils() {

    }

    /**
     * 判断网络是否连接
     *
     * @param context 上下文对象
     * @return 连接true时返回true，否则返回false
     */
    public static boolean isConnected(@NonNull Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null != connectivityManager) {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (null != networkInfo && networkInfo.isConnected()) {
                if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断网络是否连接,并且在连接时是否可以访问外网
     *
     * @param context 上下文对象
     * @return 连接true时返回true，否则返回false
     */
    public static boolean isConnectedWithPing(@NonNull Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null != connectivityManager) {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (null != networkInfo && networkInfo.isConnected()) {
                if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                    if (ping()) {//检查连接的网络能否访问外网
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 判断是否是wifi连接(该方法会判断是否有网络连接)
     *
     * @param context 上下文对象
     * @return 是wifi连接时返回true，否则返回false
     */
    public static boolean isWifi(@NonNull Context context) {
        int connectedType = getConnectedType(context);
        if (connectedType != -1) {
            return connectedType == ConnectivityManager.TYPE_WIFI;
        }
        return false;
    }

    /**
     * 判断MOBILE是否可用(该方法会判断是否有网络连接)
     *
     * @param context 上下文对象
     * @return MOBILE可用时返回true，否则返回false
     */
    public static boolean isMobile(@NonNull Context context) {
        int connectedType = getConnectedType(context);
        if (connectedType != -1) {
            return connectedType == ConnectivityManager.TYPE_MOBILE;
        }
        return false;
    }

    /**
     * 获取当前网络连接的类型信息
     *
     * @param context 上下文对象
     * @return 当前网络连接的类型信息,-1时获取信息失败,或没有网络连接
     */
    public static int getConnectedType(@NonNull Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null != connectivityManager) {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (null != networkInfo && networkInfo.isConnected()) {
                if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                    return networkInfo.getType();
                }
            }
        }
        return -1;
    }

    /**
     * 判断是否有外网连接(普通方法不能判断外网的网络是否连接，比如连接上局域网)
     *
     * @return 外网连接时返回true，否则返回false
     */
    public static final boolean ping() {
        String result = null;
        String ip = "www.baidu.com";//ping的地址，可以换成任意一种可靠的外网
        try {
            Process process = Runtime.getRuntime().exec("ping -c 3 -w 100 " + ip);//ping网址3次
            //读取ping的内容，可以不加
            InputStream is = process.getInputStream();
            BufferedReader buf = new BufferedReader(new InputStreamReader(is));
            StringBuffer sb = new StringBuffer();
            String connect;
            while ((connect = buf.readLine()) != null) {
                sb.append(connect);
            }
            LogUtils.d(TAG, "--ping--result connect:" + sb.toString());
            //ping的状态
            int status = process.waitFor();
            if (status == 0) {
                result = "succeed";
                return true;
            } else {
                result = "failed";
            }
        } catch (IOException e) {
            //e.printStackTrace();
            result = "IOException";
        } catch (InterruptedException e) {
            //e.printStackTrace();
            result = "InterruptedException";
        } finally {
            LogUtils.d(TAG, "--ping--result：" + result);
        }
        return false;
    }

    /**
     * 打开设备网络设置界面
     *
     * @param activity
     */
    public static void openSystemNetWorkSetting(@NonNull Activity activity) {
        Intent intent;
        if (Build.VERSION.SDK_INT > 10) {
            //判断手机系统的版本  即API大于10 就是3.0或以上版本
            intent = new Intent(Settings.ACTION_SETTINGS);
        } else {//3.0以下
            intent = new Intent();
            ComponentName cn = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
            intent.setComponent(cn);
            intent.setAction("android.intent.action.VIEW");
        }
        activity.startActivityForResult(intent, RESULT_OPEN_SETTINGS);
    }

    /**
     * 获取一个网络不佳时提示对话框
     */
    public static void showNetWorkManagerDialog() {
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == AlertDialog.BUTTON_POSITIVE) {//去设置
                    openSystemNetWorkSetting(AppManager.getInstance().currentActivity());
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(
                AppManager.getInstance().currentActivity());//依赖于当前顶层Activity
        builder
                .setTitle("提示")
                .setMessage("网络不给力，请确认您的网络连接，或者点击\"去设置\"去重新设置网络")
                .setNegativeButton("取消", null)
                .setPositiveButton("去设置", listener);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * 弹出一个网络不给力的Toast
     *
     * @param context
     */
    public static void showNotNetWorkToast(Context context) {
        ToastUtil.showToastOnce(context, "网络不给力，请确认您的网络连接");
    }

}
