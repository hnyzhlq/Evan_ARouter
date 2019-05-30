package com.evan.baselib;

import android.app.Application;
import android.os.Handler;

import com.evan.baselib.base.BaseApplication;


/**
 * 库项目全局配置
 * Created by Evan on 2018/3/27 0027.
 */

public class LibraryConfig {

    /**
     * 必传项
     */
    private static BaseApplication mApplication;
    private static Handler mMainHandle;
    private static boolean logable = false;

    /**
     * 选传项
     */
    private static String mAppName = "evan";//应用标识
    private static String mBaseUrl = "https://www.easy-mock.com/mock/5c135fb02398210dab1f26c6/evan";//请求接口根地址
    private static int mDefaultImgHolder = -1;//默认图片加载中显示
    private static int mDefaultHeadholder = -1;//默认头像加载中显示
    private static int mDefaultImgError = -1;//默认图片加载错误显示
    private static int mDefaultHeadError = -1;//默认头像加载错误显示


    public static void init(BaseApplication application){
        LibraryConfig.mApplication = application;
        mMainHandle = mApplication.getMainHandler();
        logable = true;
    }

    public static Application getApplication() {
        return mApplication;
    }

    public static Handler getMainHandle() {
        return mMainHandle;
    }
    public static void setMainHandle(Handler MainHandle) {
        LibraryConfig.mMainHandle = MainHandle;
    }
    public static boolean isLogable() {
        return logable;
    }

    public static void setLogable(boolean logable) {
        LibraryConfig.logable = logable;
    }
    public static String getAppName() {
        return mAppName;
    }

    public static void setAppName(String AppName) {
        LibraryConfig.mAppName = AppName;
    }
    public static int getDefaultImgHolder() {
        return mDefaultImgHolder;
    }
    public static void setBaseUrl(String baseUrl) {
        LibraryConfig.mBaseUrl = baseUrl;
    }
    public static String getBaseUrl() {
        return mBaseUrl;
    }

    public static void setDefaultImgHolder(int DefaultImgHolder) {
        LibraryConfig.mDefaultImgHolder = DefaultImgHolder;
    }

    public static int getDefaultHeadholder() {
        return mDefaultHeadholder;
    }

    public static void setDefaultHeadholder(int DefaultHeadholder) {
        LibraryConfig.mDefaultHeadholder = DefaultHeadholder;
    }

    public static int getDefaultImgError() {
        return mDefaultImgError;
    }

    public static void setDefaultImgError(int DefaultImgError) {
        LibraryConfig.mDefaultImgError = DefaultImgError;
    }

    public static int getDefaultHeadError() {
        return mDefaultHeadError;
    }

    public static void setDefaultHeadError(int DefaultHeadError) {
        LibraryConfig.mDefaultHeadError = DefaultHeadError;
    }
}
