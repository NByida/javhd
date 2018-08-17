package com.azul.yida.javhd.presenter.LifeCycleBasePresenter;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.azul.yida.javhd.common.Mlog;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxFragment;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BaseFragment extends RxFragment {
    protected   String  tag = null;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        tag=getClass().getName()+"----";
        Mlog.l(tag+"onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tag=getClass().getName()+"----";
        Mlog.l(tag+"onCreate");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Mlog.l(tag+"onViewCreated");

    }

    @Override
    public void onStart() {
        super.onStart();
        Mlog.l(tag+"onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Mlog.l(tag+"onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Mlog.l(tag+"onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Mlog.l(tag+"onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Mlog.l(tag+"onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Mlog.l(tag+"onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Mlog.l(tag+"onDetach");
    }

    protected <T> ObservableTransformer<T, T> applyIOSchedulersAndLifecycle() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindUntilEvent(FragmentEvent.DESTROY.DESTROY));}
}

