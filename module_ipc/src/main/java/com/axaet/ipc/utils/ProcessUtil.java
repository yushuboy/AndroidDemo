package com.axaet.ipc.utils;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

/**
 * date: 2019/3/18
 *
 * @author yuShu
 */
public class ProcessUtil {

    /**
     * 根据Pid获取当前进程的名字，一般就是当前app的包名
     *
     * @param context 上下文
     * @return 返回进程的名字
     */
    public static String getAppName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (activityManager != null) {
            List<ActivityManager.RunningAppProcessInfo> infoList = activityManager.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo info : infoList) {
                try {
                    if (info.pid == pid) {
                        // 根据进程的信息获取当前进程的名字
                        return info.processName;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        // 没有匹配的项，返回为null
        return null;
    }

}
