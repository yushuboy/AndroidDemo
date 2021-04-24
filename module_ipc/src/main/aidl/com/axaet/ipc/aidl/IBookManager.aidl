// IBookManager.aidl
package com.axaet.ipc.aidl;
//记得导包,实体类和aidl必须相同的包名
import com.axaet.ipc.aidl.Book;

import com.axaet.ipc.aidl.IOnNewBookArrivedListener;

interface IBookManager {

   //获取服务器端数据
    List<Book> getBookList();
    //添加数据到服务端
    void addBook(in Book book);

    //注册回调监听
    void registerListener(IOnNewBookArrivedListener listener);
    //取消注册
    void unregisterListener(IOnNewBookArrivedListener listener);
}
