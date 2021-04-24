package com.axaet.fix.application;

import com.tencent.mmkv.MMKV;
import com.tencent.tinker.loader.TinkerLoader;
import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * date: 2019/3/26
 *
 * @author yuShu
 */
public class MyApplication extends TinkerApplication {

    public MyApplication() {
        super(ShareConstants.TINKER_ENABLE_ALL, SampleApplicationLike.class.getName(),
                TinkerLoader.class.getName(), false);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MMKV.initialize(this);
    }
}