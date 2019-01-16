package com.zrdb.app.rxbus;


import com.zrdb.app.annotation.Register;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.CopyOnWriteArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RxBus {
    private static RxBus rxBus;
    private CopyOnWriteArrayList<Object> list;

    public RxBus() {
        list = new CopyOnWriteArrayList<>();
    }

    public static RxBus getInstance() {
        synchronized (RxBus.class) {
            if (rxBus == null) {
                rxBus = new RxBus();
            }
        }
        return rxBus;
    }

    public void register(Object o) {
        if (!list.contains(o)) {
            list.add(o);
        }
    }

    public void remove(Object o) {
        if (list.contains(o)) {
            list.remove(o);
        }
    }

    public void chainProcess(Function function) {
        Observable.just("")
                .map(function)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer() {
                    @Override
                    public void accept(Object o) throws Exception {
                        send(o);

                    }
                });
    }

    public void send(Object o) {
        if (list == null) return;
        for (int i = 0; i < list.size(); i++) {
            Object obj = list.get(i);
            if (null != obj && null != o) send(obj, o);
        }
    }

    private void send(Object object, Object obj) {
        Class<?> objectClass = object.getClass();
        Method[] methods = objectClass.getDeclaredMethods();
        try {
            for (Method method : methods) {
                method.setAccessible(true);
                if (method.isAnnotationPresent(Register.class)) {
                    method.invoke(object, obj);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
