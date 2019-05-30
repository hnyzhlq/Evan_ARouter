package com.evan.baselib.base;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.evan.baselib.BuildConfig;
import com.evan.baselib.LibraryConfig;
import com.evan.baselib.net.OkHttpUtils;
import com.evan.baselib.net.https.HttpsUtils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;

public class BaseApplication extends Application {
    public static BaseApplication application = null;
    private static Handler mHandler;//主线程
    private static long mMainThreadId;//主线程ID
    @Override
    public void onCreate() {
        application = this;
        super.onCreate();
        mMainThreadId = android.os.Process.myTid();
        LibraryConfig.init(this);

        //信任所有https网站
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true; //主机名认证，强行返回true 即验证成功
                    }
                })
                //其他配置
                .build();
    }
    /**
     * 获取主线程
     *
     * @return
     */
    public static Handler getMainHandler() {
        if (mHandler == null) {
            mHandler = new Handler(Looper.getMainLooper());
        }
        return mHandler;
    }

    public static long getMainThreadId() {
        return mMainThreadId;
    }

}
