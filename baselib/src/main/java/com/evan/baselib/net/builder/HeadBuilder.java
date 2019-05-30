package com.evan.baselib.net.builder;


import com.evan.baselib.net.OkHttpUtils;
import com.evan.baselib.net.request.OtherRequest;
import com.evan.baselib.net.request.RequestCall;

/**
 * Created by zhy on 16/3/2.
 */
public class HeadBuilder extends GetBuilder
{
    @Override
    public RequestCall build()
    {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers,id).build();
    }
}
