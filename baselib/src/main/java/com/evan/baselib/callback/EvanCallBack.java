package com.evan.baselib.callback;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.evan.baselib.LibraryConfig;
import com.evan.baselib.R;
import com.evan.baselib.manager.ThreadPoolManager;
import com.evan.baselib.throwable.HttpError;
import com.evan.baselib.utils.LogUtil;


import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 自定义的请求接口
 * Created by Evan on 2018/1/2.
 */

public abstract class EvanCallBack<T> implements Callback {
    public HttpError error;
    public String result;

    @Override
    public void onFailure(Call call, IOException e) {
        error = new HttpError(LibraryConfig.getApplication().getString(R.string.net_error));
        LogUtil.e("HttpCallBack", "error:" + error.msg + "     " + e.toString());
        ThreadPoolManager.postTaskSafely(new Runnable() {
            @Override
            public void run() {
                onError(error);
            }
        });
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        String url = call.request().url().toString();
        if (!response.isSuccessful()) {
            error = new HttpError(LibraryConfig.getApplication().getString(R.string.net_error));
        }
        String res = response.body().string();
        LogUtil.e("HttpCallBack", "url = " + url + ", response = " + res);
        JSONObject obj = null;
        try {
            obj = JSONObject.parseObject(res);
        } catch (Exception e) {
            e.printStackTrace();
            error = new HttpError(LibraryConfig.getApplication().getString(R.string.data_error));
        }
        if (obj == null) {
            error = new HttpError(LibraryConfig.getApplication().getString(R.string.data_error));
            onError(error);
            return;
        }
        if (obj.getIntValue("code") != 1) {
            error = new HttpError(obj.getIntValue("code"), obj.getString("msg"));
        }
        if (error == null) {
            if (obj.containsKey("data")) {
                result = obj.getString("data");
            } else {
                error = new HttpError(LibraryConfig.getApplication().getString(R.string.null_error));
            }
        }
        ThreadPoolManager.postTaskSafely(new Runnable() {
            @Override
            public void run() {
                if (error == null) {
                    T t = null;
                    try {
                        t = JSON.parseObject(result, getType());
                    }catch (Exception e){
                        t = (T)result;
                    }
                    onSuccess(t, result);
                } else {
                    LogUtil.e("HttpCallBack", "error:" + error.msg);
                    onError(error);
                }
            }
        });

    }

    public abstract void onSuccess(T t, String json);

    public abstract void onError(HttpError error);

    /**
     * @des 解析网络返回的jsonString
     */
    protected Type getType() {
        // 通过反射拿泛型的具体类型
        // 得到字节码对象
        // 强转为参数化类型
        ParameterizedType parameterizedType = (ParameterizedType) getClass()
                .getGenericSuperclass();// 获得带有泛型的父类
        // 得到的实际类型
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        return actualTypeArguments[0];

    }

}
