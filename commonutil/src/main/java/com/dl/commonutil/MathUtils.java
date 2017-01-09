package com.dl.commonutil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by DuanLu on 2016/4/5 17:00
 *
 * @author DuanLu
 * @version 1.0.0
 * @class MathUtils
 * @describe 关于数字的处理工具类
 */
public class MathUtils {
    private final String TAG = "MathUtils";

    private MathUtils() {

    }

    /**
     * 数字大于四位数时单位转换并保留一位小数，返回String类型字符串
     *
     * @param count     需要处理的数字
     * @param smallUnit 需要转换时的单位单位
     * @param bigunit   不需要转换时的单位单位
     * @return
     */
    public static String fourConvert(int count, String smallUnit, String bigunit) {
        double c;
        if (count > 10000) {
            c = ((double) (count)) / 10000;
            return String.format(Locale.getDefault(), "%.1f", c) + bigunit;
        } else {
            return Integer.toString(count) + smallUnit;
        }
    }

    /**
     * 距离转换，当距离大于1000m时单位转换为km
     *
     * @param count
     * @return
     */
    public static String distanceConvert(double count) {
        double c;
        if (count > 1000) {
            c = count / 1000;
            return String.format(Locale.getDefault(), "%.0f", c) + "km";
        } else {
            return String.format(Locale.getDefault(), "%.0f", count) + "m";
        }
    }

    /**
     * 将double型数据转换为int型以String形式输出
     *
     * @param d
     * @return
     */
    public static String doubleConvertInt(double d) {
        return String.format(Locale.getDefault(), "%.0f", d);
    }

    /**
     * 将数组转换为ArrayList
     *
     * @param ts
     * @param <T>
     * @return
     */
    public static <T> List<T> arrConvertToArrList(T[] ts) {
        if (null == ts) return null;
        ArrayList<T> arrs = new ArrayList<>();
        for (int i = 0; i < ts.length; i++) {
            arrs.add(ts[i]);
        }
        return arrs;
    }
    public static <T> ArrayList<T> listConvertToArrList(List<T> ts) {
        if (null == ts||ts.size()<=0) return null;
        ArrayList<T> arrs = new ArrayList<>();
        for (int i = 0; i < ts.size(); i++) {
            arrs.add(ts.get(i));
        }
        return arrs;
    }

}
