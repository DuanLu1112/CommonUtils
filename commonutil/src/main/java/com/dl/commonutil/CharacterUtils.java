package com.dl.commonutil;

import android.support.annotation.IntDef;
import android.text.TextUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * 文本处理工具类
 * Created by DuanLu on 2016/9/7.
 */
public class CharacterUtils {
    @IntDef({BYT_ZERO, BYT_ONE, BYT_TWO})
    @Retention(RetentionPolicy.SOURCE)//定义保留策略
    public @interface NeedByt {

    }

    public static final int BYT_ZERO = 0;
    public static final int BYT_ONE = 1;
    public static final int BYT_TWO = 2;

    /**
     * 将文字中间部分用*代替，只显示第一个和最后一个文字(不支持表情)
     * showText为空时返回空，
     * 长度等于1时直接返回，
     * 长度等于2时第二个字符用*代替
     *
     * @param showText 需要转换的文字
     * @return 转换后的文字
     */
    public static String hideMiddleText(String showText) {
        if (!TextUtils.isEmpty(showText)) {
            if (showText.length() == 1) {
                return showText;
            } else if (showText.length() == 2) {
                return showText.substring(0, 1) + "*";
            } else if (showText.length() > 2) {
                return showText.substring(0, 1) + "**" + showText.substring(showText.length() - 1, showText.length());
            }
        }
        return "";
    }

    /**
     * 将文字中间部分用*代替，只显示第一个和最后一个文字(支持表情)
     * showText为空时返回空，
     * 长度等于1时直接返回，
     * 长度等于2时第二个字符用*代替
     *
     * @param showText 需要转换的文字
     * @return 转换后的文字
     */
    public static String hideMiddleTextContainEmoji(String showText) {
        try {
            if (!TextUtils.isEmpty(showText)) {
                if (showText.length() == 1) {
                    return showText;
                } else if (showText.length() == 2) {
                    if (isEmojiChar(showText)) {
                        return showText;
                    } else {
                        return showText.substring(0, 1) + "*";
                    }
                } else if (showText.length() > 2) {
                    String start = showText.substring(0, 2);
                    String end = showText.substring(showText.length() - 2, showText.length());
                    start = isEmojiChar(start) == true ? showText.substring(0, 2) : showText.substring(0, 1);
                    end = isEmojiChar(end) == true ? showText.substring(showText.length() - 2, showText.length()) : showText.substring(showText.length() - 1, showText.length());
                    return start + "**" + end;
                }
            }
        } catch (Exception e) {
            return "***";
        }
        return "";
    }

    /**
     * 判断一个String是否包含字符串
     *
     * @param s 需要判断的字符串
     * @return true包含，false不包含
     */
    public static boolean isEmojiChar(String s) {
        boolean isEmoji;
        try {
            Pattern emojiPattern = Pattern.compile("[\\ud83c\\udc00-\\ud83c\\udfff]|[\\ud83d\\udc00-\\ud83d\\udfff]|[\\u2600-\\u27ff]");
            isEmoji = emojiPattern.matcher(s).find();
        } catch (Exception e) {
            isEmoji = false;
        }
        return isEmoji;
    }

    /**
     * 价格格式化
     * 注：根据digit返回小数点后保留需要位数的String
     *
     * @param price   需要格式化的价格
     * @param needByt 保留的位数，最多2位,值仅限于BYT_ZERO, BYT_ONE, BYT_TWO
     * @return format后的内容
     */
    public static String formatPriceText(double price, @NeedByt int needByt) {
        switch (needByt) {
            case BYT_ZERO:
                return String.format(Locale.getDefault(),"%.0f", price);
            case BYT_ONE:
                return String.format(Locale.getDefault(),"%.1f", price);
            case BYT_TWO:
                return String.format(Locale.getDefault(),"%.2f", price);
            default:
                return "";
        }
    }

}
