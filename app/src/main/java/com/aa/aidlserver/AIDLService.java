package com.aa.aidlserver;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class AIDLService extends Service {

    /**
     * 书本列表
     */
    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<>();


    private Binder mBinder = new IBookManager.Stub() {

        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            mBookList.add(book);
        }

        @Override
        public void deleteBook(Book book) throws RemoteException {
            mBookList.remove(0);
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(mBookList.size() == 0) {
            mBookList.add(new Book("他们最幸福", "一个屌丝写的书"));
            mBookList.add(new Book("晓松奇谈", "矮大紧写的书"));
        } else {
            addBook();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 通过定时任务来添加书本信息
     */
    private void addBook() {
        Log.e("TAG", "Adding...");

        int len = mBookList.size() + 1;
        String name = "图书" + len;
        String describe = "这是第" + len + "本书";
        Book book = new Book(name, describe);
        mBookList.add(book);
    }
}
