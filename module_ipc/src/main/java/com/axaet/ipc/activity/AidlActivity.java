package com.axaet.ipc.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.axaet.ipc.R;
import com.axaet.ipc.R2;
import com.axaet.ipc.aidl.Book;
import com.axaet.ipc.aidl.IBookManager;
import com.axaet.ipc.aidl.IOnNewBookArrivedListener;
import com.axaet.ipc.service.BookManagerService;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * date: 2019/3/18
 *
 * @author yuShu
 */
public class AidlActivity extends AppCompatActivity {

    private static final String TAG = "AidlActivity";
    private IBookManager iBookManager = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ipc_aidl_activity);
        ButterKnife.bind(this);
        Intent intent = new Intent(this, BookManagerService.class);
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
    }

    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG, "onServiceConnected:  " + name);
            iBookManager = IBookManager.Stub.asInterface(service);
            try {
                //注册回调监听，监听服务端数据变化
                iBookManager.registerListener(listener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(TAG, "onServiceDisconnected: " + name);
        }
    };

    @OnClick({R2.id.btnGetData, R2.id.btnAddData})
    public void onViewClicked(View view) {
        int viewId = view.getId();
        if (viewId == R.id.btnGetData) {
            getDataFromServer();
        } else if (viewId == R.id.btnAddData) {
            addDataToServer();
        }
    }

    /**
     * 从服务端获取数据
     */
    private void getDataFromServer() {
        if (iBookManager != null) {
            try {
                //此处会阻塞线程,方法运行在服务端的Binder线程池中
                List<Book> books = iBookManager.getBookList();
                for (Book book : books) {
                    Log.i(TAG, book.toString());
                }
            } catch (RemoteException e) {
                e.printStackTrace();
                return;
            }
            Toast.makeText(this, "获取成功，Log查看数据", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 添加数据到服务端
     */
    private void addDataToServer() {
        if (iBookManager != null) {
            Book book = new Book(3, "Python");
            try {
                //此处会阻塞线程,方法运行在服务端的Binder线程池中
                iBookManager.addBook(book);
            } catch (RemoteException e) {
                e.printStackTrace();
                return;
            }
            Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
        }
    }

    private final IOnNewBookArrivedListener listener = new IOnNewBookArrivedListener.Stub() {
        @Override
        public void onNewBookArrived(Book book) {
            Log.i(TAG, Thread.currentThread().getName() + "   监听到服务端数据变化: " + book.toString());
        }
    };


    @Override
    protected void onDestroy() {
        if (iBookManager != null && iBookManager.asBinder().isBinderAlive()) {
            try {
                //取消监听
                iBookManager.unregisterListener(listener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        unbindService(mServiceConnection);
        super.onDestroy();
    }
}
