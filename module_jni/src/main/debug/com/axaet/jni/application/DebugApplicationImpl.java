package com.axaet.jni.application;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.axaet.jni.BuildConfig;
import com.axaet.module.base.application.ApplicationService;

/**
 * date: 2019/3/7
 *
 * @author yuShu
 */
public class DebugApplicationImpl extends Application implements ApplicationService {


    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();
            // 打印日志
            ARouter.openDebug();
            // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this);
        // 尽可能早，推荐在Application中初始化
    }

    private static DebugApplicationImpl INSTANCE = null;

    public static DebugApplicationImpl getInstance() {
        return INSTANCE;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        INSTANCE = this;
    }


    @Override
    public void loadModuleApplicationService() {
    }

    @Override
    public Application getApplication() {
        return getInstance();
    }
}
