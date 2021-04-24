package com.axaet.ipc.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import com.axaet.ipc.application.ApplicationImpl;
import com.axaet.ipc.utils.ProcessUtil;

import androidx.annotation.Nullable;

/**
 * date: 2019/2/20
 *
 * @author yuShu
 */
public class MessengerService extends Service {

    private static final String TAG = "RemoteService";

    private Messenger mClientMessenger;

    @SuppressLint("HandlerLeak")
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String key = msg.getData().getString("key");
            Log.i(TAG, ProcessUtil.getAppName(ApplicationImpl.getInstance().getApplication())
                    + "   " + Thread.currentThread().getName() + "  key: " + key);

            mClientMessenger = msg.replyTo;

            postDelayed(() -> {
                Bundle bundle = new Bundle();
                bundle.putString("key", "来自服务端的回应");
                Message message = obtainMessage();
                message.setData(bundle);
                try {
                    mClientMessenger.send(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }, 1000);
        }
    };


    private final Messenger mServerMessenger = new Messenger(handler);

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mServerMessenger.getBinder();
    }

}
