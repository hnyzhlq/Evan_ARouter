package com.evan.baselib.net.utils;

import android.util.Log;

/**
 * Created by zhy on 15/11/6.
 */
public class L
{
    private static boolean debug = true;

    public static void setDebugLog(boolean isDebug){
        debug = isDebug;
    }
    public static void e(String msg)
    {
        if (debug)
        {
            Log.e("OkHttp", msg);
        }
    }

}

