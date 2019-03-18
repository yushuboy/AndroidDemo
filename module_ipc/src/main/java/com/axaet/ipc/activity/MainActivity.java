package com.axaet.ipc.activity;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.axaet.ipc.R;
import com.axaet.ipc.R2;
import com.axaet.ipc.application.ApplicationImpl;
import com.axaet.ipc.service.MessengerService;
import com.axaet.ipc.utils.ProcessUtil;
import com.axaet.ipc.utils.SerializeUtil;
import com.axaet.module.base.common.ARouterURL;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Messenger只能串行通信，不能做并发处理，需要AIDL
 * date: 2019/3/16
 *
 * @author yuShu
 */
@Route(path = ARouterURL.IPC_MAIN_ACTIVITY)
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    /**
     * 接收到服务端返回的binder后创建Messenger用于给服务端发送数据
     */
    private Messenger mServerMessenger;
    /**
     * 创建客户端Messenger用于接收服务端回应
     */
    private final Messenger mClientMessenger = new Messenger(HANDLER);

    @SuppressLint("HandlerLeak")
    private static final Handler HANDLER = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String key = msg.getData().getString("key");
            Log.i(TAG, ProcessUtil.getAppName(ApplicationImpl.getInstance().getApplication())
                    + "   " + Thread.currentThread().getName() + "  key: " + key);
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ipc_activity_main);
        ButterKnife.bind(this);
        Intent intent = new Intent(this, MessengerService.class);
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
    }

    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG, "onServiceConnected:  " + name);
            mServerMessenger = new Messenger(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mServerMessenger = null;
            Log.i(TAG, "onServiceDisconnected: " + name);
        }
    };

    int msgId=100;

    /**
     * 发送数据到服务端
     */
    private void sendMsgToRemoteService() {
        Bundle bundle = new Bundle();
        Message message = Message.obtain(null, msgId++);
        bundle.putString("key", "来自客户端的数据");
        message.setData(bundle);
        //把客户端的Messenger传递过去用于回应消息
        message.replyTo = mClientMessenger;
        try {
            mServerMessenger.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onDestroy() {
        unbindService(mServiceConnection);
        super.onDestroy();
    }

    @OnClick({R2.id.btnMessenger, R2.id.btnAidl, R2.id.btnSerialize, R2.id.btnUnSerialize})
    public void onViewClicked(View view) {
        int viewId = view.getId();
        if (viewId == R.id.btnMessenger) {
            sendMsgToRemoteService();
        } else if (viewId == R.id.btnAidl) {
            startActivity(new Intent(this, AidlActivity.class));
        } else if (viewId == R.id.btnSerialize) {
            //序列化
            SerializeUtil.serializePerson(ApplicationImpl.getInstance().getApplication());
        } else if (viewId == R.id.btnUnSerialize) {
            //反序列化
            SerializeUtil.unSerializePerson(ApplicationImpl.getInstance().getApplication());
        }
    }
}
