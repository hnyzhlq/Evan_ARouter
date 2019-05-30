package debug;

import com.evan.arouter_share.sdk.ShareSdk;
import com.evan.baselib.base.BaseApplication;

public class ShareApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        ShareSdk.init();
    }
}
