package com.axaet.login.application;

import android.app.Application;
import android.util.Log;

import com.axaet.module.base.application.ApplicationService;
import com.axaet.module.base.common.AppCommon;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * date: 2019/3/7
 *
 * @author yuShu
 */
public class ReleaseApplicationImpl implements ApplicationService {


    private static final String TAG = "ReleaseApplication";

    private static Application application;

    private ReleaseApplicationImpl() {
    }

    private static class ReleaseApplicationHolder {
        private static final ReleaseApplicationImpl INSTANCE = new ReleaseApplicationImpl();
    }

    public static ReleaseApplicationImpl getInstance() {
        return ReleaseApplicationHolder.INSTANCE;
    }


    @Override
    public Application getApplication() {
        if (null == application) {
            try {
                String methodName = "getInstance";
                Class clazz = Class.forName(AppCommon.MAIN_APPLICATION);
                //从主Application中的getInstance获取实力
                Method method = clazz.getMethod(methodName);
                application = (Application) method.invoke(null);
                return application;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return application;
    }

    @Override
    public void loadModuleApplicationService() {
        Log.i(TAG, "发布时需要在Application中初始化该module的数据");
    }

}
