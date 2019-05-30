package com.evan.baselib.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.widget.TextView;

import com.evan.baselib.LibraryConfig;


/**
 * UI相关的工具
 * Evan
 */
public class UIUtils {

    /**
     * 获取文本数据
     */
    public static String getString(int strResId) {
        return LibraryConfig.getApplication().getResources().getString(strResId);
    }

    /**
     * 获取颜色数据
     */
    public static int getColor(int colorResId) {
        return LibraryConfig.getApplication().getResources().getColor(colorResId);
    }

    /**
     * 获取控件文本数据
     */
    public static String getViewContent(TextView tv) {
        return tv.getText().toString().trim();
    }

    /**
     * 得到Resource对象
     */
    public static Resources getResources() {
        return LibraryConfig.getApplication().getResources();
    }
    /**
     * dp转px
     *
     * @param dpValue dp值
     * @return px值
     */
    public static int dp2px(float dpValue) {
        final float scale = LibraryConfig.getApplication().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    public static int dp2px(int dpValue) {
        final float scale = LibraryConfig.getApplication().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px转dp
     *
     * @param pxValue px值
     * @return dp值
     */
    public static int px2dp(float pxValue) {
        final float scale = LibraryConfig.getApplication().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
    public static int px2dp(int pxValue) {
        final float scale = LibraryConfig.getApplication().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * sp转px
     *
     * @param spValue sp值
     * @return px值
     */
    public static int sp2px(float spValue) {
        final float fontScale = LibraryConfig.getApplication().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
    public static int sp2px(int spValue) {
        final float fontScale = LibraryConfig.getApplication().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
    /**
     * px转sp
     *
     * @param pxValue px值
     * @return sp值
     */
    public static int px2sp(float pxValue) {
        final float fontScale = LibraryConfig.getApplication().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }
    public static int px2sp(int pxValue) {
        final float fontScale = LibraryConfig.getApplication().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 屏幕宽度
     */
    public static int getScreenWidth() {
        WindowManager wm = (WindowManager) LibraryConfig.getApplication()
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 屏幕高度
     */
    public static int getScreenHeight() {
        WindowManager wm = (WindowManager) LibraryConfig.getApplication()
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        return display.getHeight();
    }

    /**
     * 获取状态栏高度
     */
    public static int getStatusBarHeight() {
        int result = 0;
        int resourceId = LibraryConfig.getApplication().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = LibraryConfig.getApplication().getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

}
