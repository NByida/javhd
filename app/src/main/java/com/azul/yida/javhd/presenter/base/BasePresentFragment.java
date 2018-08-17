package com.azul.yida.javhd.presenter.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.azul.yida.javhd.event.loadNetworkEvent;
import com.azul.yida.javhd.presenter.LifeCycleBasePresenter.BaseFragment;
import com.azul.yida.javhd.view.base.MvpView;
import com.trello.rxlifecycle2.components.support.RxFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public abstract class BasePresentFragment<T extends MvpView> extends BaseFragment{
    private MvpView mvpView;

    private void initView(){
        Class<T> viewClass=getPresentClass();
        try {
            mvpView=viewClass.newInstance();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       initView();
        EventBus.getDefault().register(this);
       mvpView.regist(inflater,this);
       return mvpView.getRootView();
       }

    public abstract Class<T> getPresentClass();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        mvpView.unRegist();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoadNetworkEvent(loadNetworkEvent loadNetworkEvent){
        onNetWorkErorRetry();
    }

    public  abstract void  onNetWorkErorRetry();
}
