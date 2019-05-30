package com.evan.baselib.throwable;

import java.io.Serializable;

/**
 * Created by Evan on 2018/1/2.
 */

public class HttpError extends Throwable implements Serializable {

    public int code;
    public String msg;
    public Throwable throwable;
    public String strCode;

    public HttpError() {

    }
    public HttpError(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public HttpError(String msg) {
        this(0, msg);
    }

    public HttpError(String strCode, String msg) {
        this.strCode = strCode;
        this.msg = msg;
    }

    public HttpError(Throwable throwable, boolean showMsg) {
        this.throwable = throwable;
        if (showMsg && throwable != null) {
            this.msg = throwable.getMessage();
        }
    }

    public HttpError(Throwable throwable) {
        this(throwable, false);
    }

    public String getMessage() {
        if (msg == null) {
            return "";
        }
        return msg;
    }

}
