package com.axaet.login.application;

import android.app.Application;

import com.axaet.module.base.application.ApplicationService;

/**
 * date: 2019/3/7
 *
 * @author yuShu
 */
public class LoginApplicationImpl implements ApplicationService {

    private LoginApplicationImpl() {
    }

    private static class LoginApplicationHolder {
        private static final LoginApplicationImpl INSTANCE = new LoginApplicationImpl();
    }

    public static LoginApplicationImpl getInstance() {
        LoginApplicationImpl instance = LoginApplicationHolder.INSTANCE;
        return instance;
    }


    @Override
    public void loadModuleApplicationService() {
        DebugApplicationImpl.getInstance().loadModuleApplicationService();
    }

    @Override
    public Application getApplication() {
        return DebugApplicationImpl.getInstance().getApplication();
    }
}
