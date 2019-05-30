package com.evan.arouter_share.applike;

import android.app.Application;

import com.evan.arouter_componentservice.IComponentApplication;
import com.evan.arouter_share.sdk.ShareSdk;

public class ShareApplike implements IComponentApplication {
    @Override
    public void init(Application application) {
        ShareSdk.init();
    }
}
