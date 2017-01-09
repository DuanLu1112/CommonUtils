package com.dl.commonutils;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by DuanLu on 2016/11/9 14:05.
 *
 * @author DuanLu
 * @version 1.0.0
 * @class KeyBoardUtils
 * @describe 软键盘相关工具类
 */
public class KeyBoardUtils {
    private final String TAG = this.getClass().getSimpleName();

    private KeyBoardUtils() {

    }

    /**
     * 打开软键盘
     *
     * @param context  上下文对象
     * @param editText 输入框
     */
    public static void openKeyBoard(Context context, EditText editText) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 关闭软键盘
     *
     * @param context  上下文对象
     * @param editText 输入框
     */
    public static void closeKeyBoard(Context context, EditText editText) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

}
