package com.evan.baselib.net.callback;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by zhy on 15/12/14.
 */
public abstract class ResponseCallback extends Callback<Response>
{
    @Override
    public Response parseNetworkResponse(Response response, int id) throws IOException
    {
        return response;
    }
}
