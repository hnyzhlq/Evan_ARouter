package com.evan.arouter_share.sdk;

import com.evan.baselib.manager.ThreadPoolManager;
import com.evan.baselib.utils.LogUtil;

/**
 * 模拟第三方分享sdk组件
 */
public class ShareSdk {
    private static ShareSdk instans;
    private boolean isInit = false;
    public static void init(){
        getInstans().isInit = true;
        ThreadPoolManager.safeToast("分享组件初始化成功");
        LogUtil.e("sharesdk","分享组件初始化成功");
    }
    public static ShareSdk getInstans(){
        if(instans == null){
            instans =  new ShareSdk();
        }
        return instans;
    }

    public void share(){
        if(isInit){
            ThreadPoolManager.safeToast("分享成功");
        }else{
            ThreadPoolManager.safeToast("分享组件尚未初始化");
        }
    }
}
