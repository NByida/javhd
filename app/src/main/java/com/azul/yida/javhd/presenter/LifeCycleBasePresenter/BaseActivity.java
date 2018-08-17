package com.azul.yida.javhd.presenter.LifeCycleBasePresenter;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.azul.yida.javhd.common.Mlog;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BaseActivity extends RxAppCompatActivity {
    protected static  String  tag = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        tag=getLocalClassName()+"-----";
        Mlog.l(tag+"onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Mlog.l(tag+"onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Mlog.l(tag+"onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Mlog.l(tag+"onPause");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Mlog.l(tag+"onRestart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Mlog.l(tag+"onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        Mlog.l(tag+"onDestroy");
    }

    protected <T> ObservableTransformer<T, T> applyIOSchedulersAndLifecycle() {
        return observable -> observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindUntilEvent(ActivityEvent.DESTROY));
    }
}

