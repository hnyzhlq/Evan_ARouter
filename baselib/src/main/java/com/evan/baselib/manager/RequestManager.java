package com.evan.baselib.manager;

import android.content.Context;
import android.os.Handler;

import com.evan.baselib.LibraryConfig;
import com.evan.baselib.utils.LogUtil;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * @author Evan
 * @time 2017/3/13  15:33
 * @desc 基于okhttp网络请求的管理类
 */


public class RequestManager {
    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");//mdiatype 这个需要和服务端保持一致
    private static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");//mdiatype 这个需要和服务端保持一致
    private static final MediaType MEDIA_OBJECT_STREAM = MediaType.parse("application/octet-stream");//mdiatype 这个需要和服务端保持一致
    private static final String TAG = RequestManager.class.getSimpleName();
    private static volatile RequestManager mInstance;//单利引用
    private OkHttpClient mOkHttpClient;//okHttpClient 实例
    private Handler okHttpHandler;//全局处理子线程和M主线程通信
    private CacheControl mCache;//内存缓存设定类

    /**
     * 初始化RequestManager
     */
    private RequestManager(Context context) {
        //初始化OkHttpClient
        mOkHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)//设置超时时间
                .readTimeout(60, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(60, TimeUnit.SECONDS)//设置写入超时时间
                .build();
        //初始化Handler
        okHttpHandler = LibraryConfig.getMainHandle();
        //初始化缓存设定，10秒内访问同一地址不会重新获取数据
        final CacheControl.Builder builder = new CacheControl.Builder();
        builder.maxAge(2, TimeUnit.SECONDS);
        mCache = builder.build();
    }


    /**
     * 获取单例引用
     *
     * @return
     */
    public static RequestManager getInstance() {
        RequestManager inst = mInstance;
        if (inst == null) {
            synchronized (RequestManager.class) {
                inst = mInstance;
                if (inst == null) {
                    inst = new RequestManager(LibraryConfig.getApplication().getApplicationContext());
                    mInstance = inst;
                }
            }
        }
        return inst;
    }

    /**
     * 请求方式枚举
     *
     * @return
     */
    public enum RequestMethod {
        GET,
        POST_JSON,//参数为JSON的POST请求
        POST_FORM//参数为FORM的POST请求
    }

    /**
     * 统一为请求添加头信息
     *
     * @return
     */
    private Request.Builder addHeaders() {
        Request.Builder builder = new Request.Builder();
//                .addHeader("ID", userId+"")
//                .addHeader("TOKEN", token);
        return builder;
    }

    /**
     * 初始化RequestManager
     */
    public void setCaChe(CacheControl cache) {
//        编辑缓存的基本方法如下
//        final CacheControl.Builder builder = new CacheControl.Builder();
//        builder.noCache();//不使用缓存，全部走网络
//        builder.noStore();//不使用缓存，也不存储缓存
//        builder.onlyIfCached();//只使用缓存
//        builder.noTransform();//禁止转码
//        builder.maxAge(10, TimeUnit.MILLISECONDS);//指示客户机可以接收生存期不大于指定时间的响应。
//        builder.maxStale(10, TimeUnit.SECONDS);//指示客户机可以接收超出超时期间的响应消息
//        builder.minFresh(10, TimeUnit.SECONDS);//指示客户机可以接收响应时间小于当前时间加上指定时间的响应。
//        CacheControl cache = builder.build();//cacheControl
        mCache = null;
        mCache = cache;
    }

    /**
     * okHttp同步请求统一入口
     *
     * @param actionUrl   接口地址
     * @param requestType 请求类型
     * @param paramsMap   请求参数
     */
    public Response requestSyn(String actionUrl, RequestMethod requestType, HashMap<String, String> paramsMap) {
        Response response = null;
        switch (requestType) {
            case GET:
                response = requestGetBySyn(actionUrl, paramsMap);
                break;
            case POST_JSON:
                response = requestPostBySyn(actionUrl, paramsMap);
                break;
            case POST_FORM:
                response = requestPostBySynWithForm(actionUrl, paramsMap);
                break;
        }
        return response;
    }

    /**
     * okHttp get同步请求
     *
     * @param actionUrl 接口地址
     * @param paramsMap 请求参数
     */
    public Response requestGetBySyn(String actionUrl, HashMap<String, String> paramsMap) {
        StringBuilder tempParams = new StringBuilder();
        try {
            String requestUrl = "";
            if (paramsMap != null && !paramsMap.isEmpty()) {
                //处理参数
                int pos = 0;
                for (String key : paramsMap.keySet()) {
                    if (pos > 0) {
                        tempParams.append("&");
                    }
                    //对参数进行URLEncoder
                    tempParams.append(String.format("%s=%s", key, URLEncoder.encode(paramsMap.get(key), "utf-8")));
                    pos++;
                }
                //补全请求地址
                requestUrl = String.format("%s/%s?%s", LibraryConfig.getBaseUrl(), actionUrl, tempParams.toString());
            } else {
                //补全请求地址
                requestUrl = String.format("%s/%s", LibraryConfig.getBaseUrl(), actionUrl);
            }

            //创建一个请求
            Request request = addHeaders().cacheControl(mCache).url(requestUrl).build();
            //创建一个Call
            final Call call = mOkHttpClient.newCall(request);
            //执行请求
            return call.execute();
        } catch (Exception e) {
            LogUtil.e(TAG, e.toString());
        }
        return null;
    }

    /**
     * okHttp post同步请求
     *
     * @param actionUrl 接口地址
     * @param paramsMap 请求参数
     */
    public Response requestPostBySyn(String actionUrl, HashMap<String, String> paramsMap) {
        try {
            FormBody.Builder builder = null;
            if (paramsMap != null && !paramsMap.isEmpty()) {
                builder = new FormBody.Builder();
                for (String key : paramsMap.keySet()) {
                    builder.add(key, paramsMap.get(key));
                }
            }
            RequestBody requestBodyPost;
            if (builder == null) {
                requestBodyPost = RequestBody.create(MEDIA_TYPE_JSON, "");
            } else {
                requestBodyPost = builder.build();
                final Buffer buffer = new Buffer();
                requestBodyPost.writeTo(buffer);
                LogUtil.e("HttpCallBack", buffer.readUtf8());
            }
            //补全请求地址
            String requestUrl = String.format("%s/%s", LibraryConfig.getBaseUrl(), actionUrl);
            //创建一个请求
            final Request request = addHeaders().cacheControl(mCache).url(requestUrl).post(requestBodyPost).build();
            //创建一个Call
            final Call call = mOkHttpClient.newCall(request);
            //执行请求
            Response response = call.execute();
            //请求执行成功
            if (response.isSuccessful()) {
                //获取返回数据 可以是String，bytes ,byteStream
                LogUtil.e(TAG, "response ----->" + response.body().string());
            }
            return response;
        } catch (Exception e) {
            LogUtil.e(TAG, e.toString());
        }
        return null;
    }

    /**
     * okHttp post同步请求表单提交
     *
     * @param actionUrl 接口地址
     * @param paramsMap 请求参数
     */
    public Response requestPostBySynWithForm(String actionUrl, HashMap<String, String> paramsMap) {
        try {
            //创建一个FormBody.Builder
            FormBody.Builder builder = new FormBody.Builder();
            if (null != paramsMap && !paramsMap.isEmpty()) {
                for (String key : paramsMap.keySet()) {
                    //追加表单信息
                    builder.add(key, paramsMap.get(key));
                }
            }
            //生成表单实体对象
            RequestBody formBody = builder.build();
            //补全请求地址
            String requestUrl = String.format("%s/%s", LibraryConfig.getBaseUrl(), actionUrl);
            //创建一个请求
            final Request request = addHeaders().cacheControl(mCache).url(requestUrl).post(formBody).build();
            //创建一个Call
            final Call call = mOkHttpClient.newCall(request);
            //执行请求
            Response response = call.execute();
            //请求执行成功
            if (response.isSuccessful()) {
                //获取返回数据 可以是String，bytes ,byteStream
                LogUtil.e(TAG, "response ----->" + response.body().string());
            }
            return response;
        } catch (Exception e) {
            LogUtil.e(TAG, e.toString());
        }
        return null;
    }

    /**
     * okHttp异步请求统一入口
     *
     * @param actionUrl   接口地址
     * @param requestType 请求类型
     * @param paramsMap   请求参数
     * @param callBack    请求返回数据回调
     **/
    public Call requestAsyn(String actionUrl, RequestMethod requestType, HashMap<String, String> paramsMap, Callback callBack) {
        Call call = null;
        switch (requestType) {
            case GET:
                call = requestGetByAsyn(actionUrl, paramsMap, callBack);
                break;
            case POST_JSON:
                call = requestPostByAsyn(actionUrl, paramsMap, callBack);
                break;
            case POST_FORM:
                call = requestPostByAsynWithForm(actionUrl, paramsMap, callBack);
                break;
        }
        return call;
    }

    /**
     * okHttp get异步请求
     *
     * @param actionUrl 接口地址
     * @param paramsMap 请求参数
     * @param callBack  请求返回数据回调
     * @return
     */
    public Call requestGetByAsyn(String actionUrl, HashMap<String, String> paramsMap, Callback callBack) {
        StringBuilder tempParams = new StringBuilder();
        try {
            String requestUrl = "";
            if (paramsMap != null && !paramsMap.isEmpty()) {
                //处理参数
                int pos = 0;
                for (String key : paramsMap.keySet()) {
                    if (pos > 0) {
                        tempParams.append("&");
                    }
                    //对参数进行URLEncoder
                    tempParams.append(String.format("%s=%s", key, URLEncoder.encode(paramsMap.get(key), "utf-8")));
                    pos++;
                }
                //补全请求地址
                requestUrl = String.format("%s/%s?%s", LibraryConfig.getBaseUrl(), actionUrl, tempParams.toString());
            } else {
                //补全请求地址
                requestUrl = String.format("%s/%s", LibraryConfig.getBaseUrl(), actionUrl);
            }
            final Request request = addHeaders().cacheControl(mCache).url(requestUrl).build();
            final Call call = mOkHttpClient.newCall(request);
            call.enqueue(callBack);
            return call;
        } catch (Exception e) {
            LogUtil.e(TAG, e.toString());
        }
        return null;
    }

    /**
     * okHttp post异步请求
     *
     * @param actionUrl 接口地址
     * @param paramsMap 请求参数
     * @param callBack  请求返回数据回调
     * @return
     */
    public Call requestPostByAsyn(String actionUrl, HashMap<String, String> paramsMap, Callback callBack) {
        try {
//            StringBuilder tempParams = new StringBuilder();
            FormBody.Builder builder = null;
            if (paramsMap != null && !paramsMap.isEmpty()) {
                //处理参数
//                int pos = 0;
                builder = new FormBody.Builder();
                for (String key : paramsMap.keySet()) {
                    builder.add(key, paramsMap.get(key));
//                    if (pos > 0) {
//                        tempParams.append("&");
//                    }
                    //对参数进行URLEncoder
//                    tempParams.append(String.format("%s=%s", key, URLEncoder.encode(paramsMap.get(key), "utf-8")));
//                    tempParams.append(String.format("%s=%s", key, paramsMap.get(key)));
//                    pos++;
                }
            }
//            String params = tempParams.toString();
//            RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, params);
            RequestBody requestBodyPost;
            if (builder == null) {
                requestBodyPost = RequestBody.create(MEDIA_TYPE_JSON, "");
            } else {
                requestBodyPost = builder.build();
                final Buffer buffer = new Buffer();
                requestBodyPost.writeTo(buffer);
                LogUtil.e("HttpCallBack", buffer.readUtf8());
            }
            String requestUrl = String.format("%s/%s", LibraryConfig.getBaseUrl(), actionUrl);
            final Request request = addHeaders().cacheControl(mCache).url(requestUrl).post(requestBodyPost).build();
            final Call call = mOkHttpClient.newCall(request);
            call.enqueue(callBack);
            return call;
        } catch (Exception e) {
            LogUtil.e(TAG, e.toString());
        }
        return null;
    }

    /**
     * okHttp post异步请求表单提交
     *
     * @param actionUrl 接口地址
     * @param paramsMap 请求参数
     * @param callBack  请求返回数据回调
     * @return
     */
    public Call requestPostByAsynWithForm(String actionUrl, HashMap<String, String> paramsMap, Callback callBack) {
        try {
            FormBody.Builder builder = new FormBody.Builder();
            if (null != paramsMap && !paramsMap.isEmpty()) {
                for (String key : paramsMap.keySet()) {
                    //追加表单信息
                    builder.add(key, paramsMap.get(key));
                }
            }
            RequestBody formBody = builder.build();
            LogUtil.e("HttpCallBack", formBody.toString());
            String requestUrl = String.format("%s/%s", LibraryConfig.getBaseUrl(), actionUrl);
            final Request request = addHeaders().cacheControl(mCache).url(requestUrl).post(formBody).build();
            final Call call = mOkHttpClient.newCall(request);
            call.enqueue(callBack);
            return call;
        } catch (Exception e) {
            LogUtil.e(TAG, e.toString());
        }
        return null;
    }
}
