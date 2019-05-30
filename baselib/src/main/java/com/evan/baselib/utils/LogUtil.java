package com.evan.baselib.utils;

import android.util.Log;

import com.evan.baselib.LibraryConfig;

/**
 * log控制的工具
 * Evan
 */
public class LogUtil {


    public static void e(Object msg) {
        if (LibraryConfig.isLogable()) {
            if (msg == null) {
                Log.e(getTag(), "对象为null");
            } else {
                Log.e(getTag(), msg.toString());
            }

        }
    }

    public static void e(String tag, String msg) {
        if (LibraryConfig.isLogable()) {
            if (msg == null) {
                Log.e(tag, "对象为null");
            } else {
                Log.e(tag, msg);
            }

        }
    }

    public static void d(Object msg) {
        if (LibraryConfig.isLogable()) {
            if (msg == null) {
                Log.d(getTag(), "对象为null");
            } else {
                Log.d(getTag(), msg.toString());
            }

        }
    }

    public static void d(String tag, String msg) {
        if (LibraryConfig.isLogable()) {
            if (msg == null) {
                Log.d(tag, "对象为null");
            } else {
                Log.d(tag, msg);
            }

        }
    }

    /**
     * 分段打印过长log
     *
     * @param tag
     * @param msg
     */
    public static void Longe(String tag, String msg) {
        if (LibraryConfig.isLogable()) {
            if (msg == null) {
                Log.e(tag, "对象为null");
            } else {
                int max = 3000;
                int count = 0;
                int lenth = msg.length();
                if (lenth < max) {
                    Log.e(tag, msg);
                } else {
                    while ((lenth - count) > max) {
                        String show = msg.substring(count, count + max);
                        count = count + max;
                        e(tag, show);
                    }
                    String show = msg.substring(count, lenth);
                    e(tag, show);
                }
            }

        }
    }

    /**
     * 生成tag，为调用类的类名
     * @return
     */
    private static String getTag() {
        StackTraceElement[] trace = new Throwable().fillInStackTrace()
                .getStackTrace();
        String callingClass = "";
        for (int i = 2; i < trace.length; i++) {
            Class<?> clazz = trace[i].getClass();
            if (!clazz.equals(LogUtil.class)) {
                callingClass = trace[i].getClassName();
                callingClass = callingClass.substring(callingClass
                        .lastIndexOf('.') + 1);
                break;
            }
        }
        return callingClass;
    }

}
