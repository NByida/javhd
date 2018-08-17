package com.azul.yida.javhd.event;

import android.view.View;

import com.azul.yida.javhd.view.base.BaseView;

public class RefreshEvent {
    private BaseView mvpView;
    private View clickView;

    public RefreshEvent(BaseView mvpView, View clickView) {
        this.mvpView = mvpView;
        this.clickView = clickView;
    }

    public BaseView getMvpView() {
        return mvpView;
    }

    public void setMvpView(BaseView mvpView) {
        this.mvpView = mvpView;
    }

    public View getClickView() {
        return clickView;
    }

    public void setClickView(View clickView) {
        this.clickView = clickView;
    }
}
