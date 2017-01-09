package com.dl.commonutil;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

/**
 * Created by DuanLu on 2016/12/28 18:01.
 *
 * @author DuanLu
 * @version 1.0.0
 * @class IntentUtils
 * @describe Activity跳转工具类
 */
public class IntentUtils {
    /**
     * 打电话,跳到拨号界面
     *
     * @param context
     * @param mobile
     */
    public static void callMobile(Context context, String mobile) {
        if (!TextUtils.isEmpty(mobile)) {
            Uri uri = Uri.parse("tel:" + mobile);
            context.startActivity(new Intent(Intent.ACTION_DIAL, uri));
        } else {
            ToastUtil.showToastOnce(context, "号码为空");
        }
    }

}
