package com.axaet.ipc.service;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import com.axaet.ipc.aidl.Book;
import com.axaet.ipc.aidl.IBookManager;
import com.axaet.ipc.aidl.IOnNewBookArrivedListener;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import androidx.annotation.Nullable;

/**
 * date: 2019/3/18
 *
 * @author yuShu
 */
public class BookManagerService extends Service {

    private static final String TAG = "BookManagerService";
    private final CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<>();
    /**
     * 用于管理客户端的回调监听,因为多进程时，服务端和客户端的listener并不是同一对象
     */
    private final RemoteCallbackList<IOnNewBookArrivedListener> mListenerList = new RemoteCallbackList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        mBookList.add(new Book(1, "Java"));
        mBookList.add(new Book(2, "Android"));
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        //验证权限，防止其他客户端连接
        int check = checkCallingOrSelfPermission("com.axaet.ipc.permission.ACCESS_BOOK_SERVICE");
        if (check == PackageManager.PERMISSION_DENIED) {
            Log.i(TAG, "onBind: 拒绝访问");
            return null;
        }
        //返回Binder给客户端生成对应的接口
        return mBinder;
    }

    private final Binder mBinder = new IBookManager.Stub() {
        @Override
        public List<Book> getBookList() {
            //该方法在Binder线程池中执行,不必开线程执行，客户端调用后，客户端线程挂起，直到服务端执行完毕
            Log.i(TAG, Thread.currentThread().getName());
            return mBookList;
        }

        @Override
        public void addBook(Book book) {
            mBookList.add(book);
            //通知客户端(回调)
            onNewBookArrived(book);
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) {
            mListenerList.register(listener);
        }

        @Override
        public void unregisterListener(IOnNewBookArrivedListener listener) {
            mListenerList.unregister(listener);
        }
    };

    /**
     * 通知客户端（回调）
     *
     * @param book book
     */
    private void onNewBookArrived(Book book) {
        //开始遍历
        int count = mListenerList.beginBroadcast();
        for (int i = 0; i < count; i++) {
            IOnNewBookArrivedListener listener = mListenerList.getBroadcastItem(i);
            if (listener != null) {
                try {
                    //回调的执行，要防止阻塞
                    listener.onNewBookArrived(book);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
        //结束遍历
        mListenerList.finishBroadcast();
    }
}
