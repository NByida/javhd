package com.azul.yida.javhd.view.base;

import android.app.Activity;
import android.view.LayoutInflater;

import com.trello.rxlifecycle2.LifecycleProvider;

public interface BaseView {
    public void regist(LayoutInflater inflater, LifecycleProvider provider);
    public void unRegist();
    public void showLoading();
    public void dissmissLoading();
    public void showErrorMessage(String msg);
    public void showLongMessage(String msg);
    public <T extends Activity> T getActivity();

}