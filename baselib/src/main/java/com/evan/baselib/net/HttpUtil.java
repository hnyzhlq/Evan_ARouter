package com.evan.baselib.net;

import android.app.Activity;
import android.content.Context;


import com.evan.baselib.LibraryConfig;
import com.evan.baselib.net.callback.BitmapCallback;
import com.evan.baselib.net.callback.Callback;
import com.evan.baselib.net.callback.FileCallBack;
import com.evan.baselib.net.callback.StringCallback;
import com.evan.baselib.net.https.HttpsUtils;
import com.evan.baselib.utils.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;
import okhttp3.Response;


/**
 * 有其他需求可以访问下面github地址
 * 参考资料：https://github.com/hongyangAndroid/okhttputils
 * Created by Administrator on 2017/7/12 0012.
 */

public class HttpUtil {
    private static Map<String,String> mHeaderMap = new HashMap<>();
    private static Map<String,String> mParamsMap = new HashMap<>();

    /**
     *  设置公用header
     */
    public static void setCommonnHeader(Map<String,String> headers){
        if (headers != null) mHeaderMap.putAll(headers);
    }

    /**
     * 设置公用参数
     */
    public static void setCommonParams(Map<String,String> params){
        if (params != null) mParamsMap.putAll(params);
    }

    public static void cleanHeaders(){
        mHeaderMap.clear();
    }
    public static void cleanCommonParams(){
        mParamsMap.clear();
    }

    private static Map<String,String> getHeaders(Map<String,String> headers){
        if (headers == null)
            headers = new HashMap<>();
        headers.putAll(mHeaderMap);
        return headers;
    }

    private static Map<String,String> getCommonParams(Map<String,String> params){
        if (params == null ){
            params = new HashMap<>();
        }
        params.putAll(mParamsMap);
        return params;
    }

    /**
     * get请求
     * */
    public static void get(Activity activity, String reqUrl, Map<String ,String> params, Callback callback){
        get(activity,reqUrl,null,params,callback);
    }
    /**
     * get请求
     * */
    public static void get(Activity activity, String reqUrl, Map<String ,String> headers, Map<String ,String> params, Callback callback){
        String url = "";
        if(StringUtils.isEmpty(LibraryConfig.getBaseUrl())){
            url = reqUrl;
        }else{
            url = LibraryConfig.getBaseUrl() + reqUrl;
        }
        OkHttpUtils
                .get()
                .url(url)
                .tag(activity)
//                .addParams("username", "hyman");
                .headers(getHeaders(headers))
                .params(getCommonParams(params))
                .build()
                .execute(callback);
    }
    /**
     * get请求
     * */
    public static void get(String tag, String reqUrl, Map<String ,String> params, Callback callback){
        get(tag,reqUrl,null,params,callback);
    }
    /**
     * get请求
     * */
    public static void get(String tag, String reqUrl, Map<String ,String> headers, Map<String ,String> params, Callback callback){
        String url = "";
        if(StringUtils.isEmpty(LibraryConfig.getBaseUrl())){
            url = reqUrl;
        }else{
            url = LibraryConfig.getBaseUrl() + reqUrl;
        }
        OkHttpUtils
                .get()
                .url(url)
                .tag(tag)
//                .addParams("username", "hyman");
                .headers(getHeaders(headers))
                .params(getCommonParams(params))
                .build()
                .execute(callback);
    }
    /**
     * post请求
     * */
    public static void post(Context activity, String reqUrl, Map<String ,String> params, Callback callback){
        post(activity,reqUrl,null,params,callback);
    }
    /**
     * post请求
     * */
    public static void post(Context activity, String reqUrl, Map<String ,String> headers, Map<String ,String> params, Callback callback){
        String url = "";
        if(StringUtils.isEmpty(LibraryConfig.getBaseUrl())){
            url = reqUrl;
        }else{
            url = LibraryConfig.getBaseUrl() + reqUrl;
        }
        OkHttpUtils
                .post()
                .url(url)
                .tag(activity)
//                .addParams("username", "hyman");
                .headers(getHeaders(headers))
                .params(getCommonParams(params))
                .build()
                .execute(callback);
    }
    /**
     * post请求
     * */
    public static void post(String tag, String reqUrl, Map<String ,String> params, Callback callback){
        post(tag,reqUrl,null,params,callback);
    }
    /**
     * post请求
     * */
    public static void post(String tag, String reqUrl, Map<String ,String> headers, Map<String ,String> params, Callback callback){
        String url = "";
        if(StringUtils.isEmpty(LibraryConfig.getBaseUrl())){
            url = reqUrl;
        }else{
            url = LibraryConfig.getBaseUrl() + reqUrl;
        }
        OkHttpUtils
                .post()
                .url(url)
                .tag(tag)
//                .addParams("username", "hyman");
                .headers(getHeaders(headers))
                .params(getCommonParams(params))
                .build()
                .execute(callback);
    }

    /**
     * 同步post请求
     * */
    public static String post(String tag, String reqUrl, Map<String ,String> params){
       return post(tag,reqUrl,null,params);
    }
    /**
     * 同步post请求
     * */
    public static String post(String tag, String reqUrl, Map<String ,String> headers, Map<String ,String> params){
        String url = "";
        if(StringUtils.isEmpty(LibraryConfig.getBaseUrl())){
            url = reqUrl;
        }else{
            url = LibraryConfig.getBaseUrl() + reqUrl;
        }
        try {
            Response response = OkHttpUtils
                    .post()
                    .url(url)
                    .tag(tag)
    //                .addParams("username", "hyman");
                    .headers(getHeaders(headers))
                    .params(getCommonParams(params))
                    .build().execute();
            return response != null ? response.body().string() : null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 上传文件
     * */
    public static void postFile(Activity activity, String reqUrl, File file, StringCallback callback){
        String url = "";
        if(StringUtils.isEmpty(LibraryConfig.getBaseUrl())){
            url = reqUrl;
        }else{
            url = LibraryConfig.getBaseUrl() + reqUrl;
        }
        OkHttpUtils
                .postFile()
                .url(url)
                .tag(activity)
                .headers(mHeaderMap)
                .file(file)
                .build()
                .execute(callback);
    }

    /**
     * 下载文件
     * @param activity
     * @param reqUrl
     * @param callBack
     */
    public static void downloadFile(Activity activity, String reqUrl, FileCallBack callBack){
        String url = "";
        if(StringUtils.isEmpty(LibraryConfig.getBaseUrl())){
            url = reqUrl;
        }else{
            url = LibraryConfig.getBaseUrl() + reqUrl;
        }
        OkHttpUtils//
                .get()//
                .url(url)//
                .tag(activity)
                .build()//
                .execute(callBack);
    }
    /**
     * 下载图片
     * @param activity
     * @param reqUrl
     * @param callBack
     */
    public static void downloadImage(Activity activity, String reqUrl, BitmapCallback callBack){
        String url = "";
        if(StringUtils.isEmpty(LibraryConfig.getBaseUrl())){
            url = reqUrl;
        }else{
            url = LibraryConfig.getBaseUrl() + reqUrl;
        }
        OkHttpUtils//
                .get()//
                .url(url)//
                .tag(activity)
                .build()//
                .execute(callBack);
    }
    /**
     * Post表单形式上传文件
     * */
//    public static void postFiles(String method, File file,File file2,Map<String ,String> params, StringCallback callback){
//        String url = Config.HOSTMAIN + method;
//        OkHttpUtils.post()//
//                .addFile("mFile", "messenger_01.png", file)//
//                .addFile("mFile", "test1.txt", file2)//
//                .url(url)
//                .params(params)//
//                .headers(getHeader())//
//                .build()//
//                .execute(callback);
//    }
    /**
     * 取消同一个activity中的请求
     * @param activity
     */
    public static void cancel(Activity activity)
    {
        //可以取消同一个tag的
        OkHttpUtils.getInstance().cancelTag(activity);//取消以Activity.this作为tag的请求
    }
    //初始化HttpClient,不验证SSL
    public static void initClient(){
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
        OkHttpUtils.initClient(okHttpClient);
    }
}
