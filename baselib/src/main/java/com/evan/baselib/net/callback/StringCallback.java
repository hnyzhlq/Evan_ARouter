package com.evan.baselib.net.callback;


import com.evan.baselib.net.utils.L;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by zhy on 15/12/14.
 */
public abstract class StringCallback extends Callback<String>
{
    @Override
    public String parseNetworkResponse(Response response, int id) throws IOException
    {
        String result = response.body().string();
        return result;
    }
}
