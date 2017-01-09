package com.dl.commonutils;

import java.util.List;

/**
 * Created by WangFei on 2016/11/16 11:27.
 *
 * @author WangFei
 * @version 1.0.0
 * @class CommonTextUtils
 * @describe 处理字符串的工具类
 */

public class CommonTextUtils {
    public String content = "";

    public CommonTextUtils() {

    }

    public String getText(List<String> str) {
        for (int i = 0; i < str.size(); i++) {
            if (str.size() > 1) {
                if (i == str.size() - 1) {
                    content = content + str.get(i);
                } else {
                    content = content + str.get(i) + "丶 ";
                }
            } else {
                content = content + str.get(i);
            }
        }
        return content;
    }
}
