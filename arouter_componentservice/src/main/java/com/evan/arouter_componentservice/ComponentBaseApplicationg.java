package com.evan.arouter_componentservice;

import com.evan.baselib.base.BaseApplication;

public class ComponentBaseApplicationg extends BaseApplication {
    private static final String[] MODULESLIST =
            {"com.evan.arouter_share.applike.ShareApplike"};

    @Override
    public void onCreate() {
        super.onCreate();
        //Module类的APP初始化
        modulesApplicationInit();
    }

    private void modulesApplicationInit(){
        for (String moduleImpl : MODULESLIST){
            try {
                Class<?> clazz = Class.forName(moduleImpl);
                Object obj = clazz.newInstance();
                if (obj instanceof IComponentApplication){
                    ((IComponentApplication) obj).init(ComponentBaseApplicationg.application);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }
}
