package com.axaet.ipc.application;


import android.app.Application;

import com.axaet.module.base.application.ApplicationService;

/**
 * date: 2019/3/7
 *
 * @author yuShu
 */
public class ApplicationImpl implements ApplicationService {

    private ApplicationImpl() {
    }

    private static class LoginApplicationHolder {
        private static final ApplicationImpl INSTANCE = new ApplicationImpl();
    }

    public static ApplicationImpl getInstance() {
        return LoginApplicationHolder.INSTANCE;
    }


    @Override
    public void loadModuleApplicationService() {
       ReleaseApplicationImpl.getInstance().loadModuleApplicationService();
    }

    @Override
    public Application getApplication() {
        return ReleaseApplicationImpl.getInstance().getApplication();
    }
}
