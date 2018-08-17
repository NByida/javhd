package com.azul.yida.javhd.presenter.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;


import com.azul.yida.javhd.R;
import com.azul.yida.javhd.view.base.MvpView;
import com.azul.yida.javhd.view.viewHelper.BGArefrashViewHolder;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

public abstract class BaseloadingRvActivity<T extends MvpView>  extends com.azul.yida.javhd.presenter.base.BasePresentActivity implements BGARefreshLayout.BGARefreshLayoutDelegate{
    public T mvpView;
    protected BGARefreshLayout mRefreshLayout;
    protected int page=1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mvpView= (T) getMvpView();
        mRefreshLayout=rootView.findViewById(R.id.rl_modulename_refresh);
        initRefreshLayout(mRefreshLayout);
        mRefreshLayout.beginRefreshing();
    }

    @SuppressLint("ResourceType")
    private void initRefreshLayout(BGARefreshLayout refreshLayout) {
        mRefreshLayout =  findViewById(R.id.rl_modulename_refresh);
        mRefreshLayout.setDelegate(this);
        BGArefrashViewHolder refreshViewHolder = new BGArefrashViewHolder(this, true) {};
        mRefreshLayout.setIsShowLoadingMoreView(true);
        refreshViewHolder.setLoadingMoreText("疯狂加载中...");
        refreshViewHolder.setStickinessColor(R.color.colorAccent);
        refreshViewHolder.setRotateImage(R.mipmap.bga_refresh_loading12);
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        page=1;
        pullData(1);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        pullData(++page);
        return true;
    }

    public void onNetWorkErorRetry(){
        pullData(page);
    }

    protected abstract void pullData(int p);
}
