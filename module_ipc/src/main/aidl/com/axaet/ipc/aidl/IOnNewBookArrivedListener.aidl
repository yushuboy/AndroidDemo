// IOnNewBookArrivedListener.aidl
package com.axaet.ipc.aidl;

import com.axaet.ipc.aidl.Book;

interface IOnNewBookArrivedListener {

    void onNewBookArrived(in Book book);
}
