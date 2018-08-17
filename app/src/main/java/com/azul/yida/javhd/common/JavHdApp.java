package com.azul.yida.javhd.common;

import android.app.Application;

public class JavHdApp extends Application {
    public static JavHdApp instance;


    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
    }

    public static JavHdApp getInstance() {
        return instance;
    }
}
