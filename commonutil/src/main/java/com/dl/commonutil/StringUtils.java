package com.dl.commonutil;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;

/**
 * Created by DuanLu on 2016/12/19 16:51.
 *
 * @author DuanLu
 * @version 1.0.0
 * @class StringUtils
 * @describe
 */
public class StringUtils {

    /**
     * 隐藏手机号中间4位
     *
     * @param mobile 手机号
     * @return 123****1234
     */
    public static String hindMobileMiddleFore(String mobile) {
        if (TextUtils.isEmpty(mobile)) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        sb.append(mobile.substring(0, 3))
                .append("****")
                .append(mobile.substring(7));
        return sb.toString();
    }

    /**
     * TextView文本关键字高亮
     *
     * @param context Context
     * @param text    需要显示的原始文本
     * @param key     需要高亮的关键字
     * @return SpannableStringBuilder对象
     */
    public static SpannableStringBuilder highlightKey(Context context, String text, String key, @ColorRes int keyColor) {
        String[] newTexts = text.split(key);
        SpannableStringBuilder builder = new SpannableStringBuilder();
        if (newTexts.length > 0) {
            int length;
            int endLength = 0;
            for (int i = 0; i < newTexts.length; i++) {
                builder.append(newTexts[i]);
                length = newTexts[i].length() + endLength;
                if (i != newTexts.length - 1) {
                    builder.append(key);
                } else if (text.endsWith(key)) {
                    builder.append(key);
                }
                endLength = builder.length();
                builder.setSpan(new ForegroundColorSpan(context.getResources().getColor(keyColor)), length, endLength, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        } else if (text.contains(key)) {//全部为key
            builder.append(text);
            builder.setSpan(new ForegroundColorSpan(context.getResources().getColor(keyColor)), 0, builder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {//不包含key
            builder.append(text);
        }
        return builder;
    }

}
