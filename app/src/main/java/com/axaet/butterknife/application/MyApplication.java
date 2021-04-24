package com.axaet.butterknife.application;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.axaet.module.base.BuildConfig;
import com.axaet.module.base.application.ApplicationService;

import androidx.multidex.MultiDex;

/**
 * date: 2019/3/7
 *
 * @author yuShu
 */
public class MyApplication extends Application implements ApplicationService {


    private static final String TAG = "MyApplication";


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        Log.i(TAG, "attachBaseContext: ");
        //dex分包
        MultiDex.install(this);
        INSTANCE = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate: ");
        if (BuildConfig.DEBUG) {
            // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();
            // 打印日志
            ARouter.openDebug();
            // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this);
        // 尽可能早，推荐在Application中初始化
        loadModuleApplicationService();
    }


    private static MyApplication INSTANCE = null;

    public static MyApplication getInstance() {
        return INSTANCE;
    }


    @Override
    public void loadModuleApplicationService() {
//        LoginApplicationImpl.getInstance().loadModuleApplicationService();
    }

    @Override
    public Application getApplication() {
        return INSTANCE;
    }
}
