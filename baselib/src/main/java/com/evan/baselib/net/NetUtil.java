package com.evan.baselib.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import com.evan.baselib.LibraryConfig;


/**
 * 网络状况工具类
 * Created by lenovo on 2016/3/24.
 */
public class NetUtil {
    /**
     * 网络是否连接
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        if (context == null) return false;
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    // 当前所连接的网络可用
                    return true;
                }
            }
        }
        return false;
    }


    public static String getNetType(Context context) throws NullPointerException {
        String netTypeValue = "";
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobileInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);//移动网络
        NetworkInfo wifiInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);//WIFi网络
        NetworkInfo activeInfo = manager.getActiveNetworkInfo();

        if (activeInfo == null) {
            //ToastUtil.showToast("无网络");
        } else {
            if (wifiInfo.isConnected()) {
                netTypeValue = "wifi";
            } else if (mobileInfo.isConnected()) {
                switch (mobileInfo.getType()) {
                    case ConnectivityManager.TYPE_MOBILE:// 手机网络
                        switch (mobileInfo.getSubtype()) {
                            case TelephonyManager.NETWORK_TYPE_UMTS:
                            case TelephonyManager.NETWORK_TYPE_EVDO_0:
                            case TelephonyManager.NETWORK_TYPE_EVDO_A:
                            case TelephonyManager.NETWORK_TYPE_HSDPA:
                            case TelephonyManager.NETWORK_TYPE_HSUPA:
                            case TelephonyManager.NETWORK_TYPE_HSPA:
                            case TelephonyManager.NETWORK_TYPE_EVDO_B:
                            case TelephonyManager.NETWORK_TYPE_EHRPD:
                            case TelephonyManager.NETWORK_TYPE_HSPAP:
                                netTypeValue = "3G";
                                break;
                            case TelephonyManager.NETWORK_TYPE_CDMA:
                            case TelephonyManager.NETWORK_TYPE_GPRS:
                            case TelephonyManager.NETWORK_TYPE_EDGE:
                            case TelephonyManager.NETWORK_TYPE_1xRTT:
                            case TelephonyManager.NETWORK_TYPE_IDEN:
                                netTypeValue = "2G";
                                break;
                            case TelephonyManager.NETWORK_TYPE_LTE:
                                netTypeValue = "4G";
                                break;

                        }
                }
            }
        }

        return netTypeValue;
    }

    public static boolean isWiFi() {
        boolean wifiConnected = false;
        ConnectivityManager connMgr = (ConnectivityManager) LibraryConfig.getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
        if (activeInfo != null && activeInfo.isConnected()) {
            wifiConnected = (activeInfo.getType() == ConnectivityManager.TYPE_WIFI);
        }
        return wifiConnected;
    }
}
