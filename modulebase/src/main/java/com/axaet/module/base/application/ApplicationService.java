package com.axaet.module.base.application;

import android.app.Application;

/**
 * date: 2019/3/7
 *
 * @author yuShu
 */
public interface ApplicationService {
    /**
     * 初始化工作
     */
    void loadModuleApplicationService();

    /**
     * 获取主APP的Application或者module在debug时自己的application
     *
     * @return application
     */
    Application getApplication();
}
