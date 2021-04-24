package com.axaet.rxhttp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * date:2016/11/19
 * time:17:43
 *
 * @author yuShu
 */
public class NetworkUtil {
    /**
     * 判断网络
     */
    public static boolean networkIsAvailable(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService
                (Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connManager != null) {
            networkInfo = connManager.getActiveNetworkInfo();
        }
        return networkInfo != null && connManager.getActiveNetworkInfo().isAvailable();
    }
}
