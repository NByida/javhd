package com.azul.yida.javhd.presenter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.azul.yida.javhd.R;
import com.azul.yida.javhd.net.FilmServices;
import com.azul.yida.javhd.net.RetrofitInstance;
import com.azul.yida.javhd.presenter.base.BaseloadingRvActivity;
import com.azul.yida.javhd.view.AllFilmView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AllFilmActivity extends BaseloadingRvActivity<AllFilmView> {
    FilmServices filmServices;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
        filmServices= RetrofitInstance.getInstance().getFilmServices(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public Class<AllFilmView> getPresentClass() {
        return AllFilmView.class;
    }

    @Override
    protected void pullData(int p) {
        mvpView.showLoading();
        filmServices.getData(p)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(model->{
                    rootView.post(()->mvpView.dissmissLoading());
                    if(p==1) {mRefreshLayout.endRefreshing();}
                    else { mRefreshLayout.endLoadingMore();}
                    // Mlog.t(model.toString());
                    mvpView.setData(model);
                },e->{consumer.accept(e);
                    if(p==1)mRefreshLayout.endRefreshing();
                    else  mRefreshLayout.endLoadingMore();
                });
    }
}
