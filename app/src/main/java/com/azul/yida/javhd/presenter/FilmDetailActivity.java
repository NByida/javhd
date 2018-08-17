package com.azul.yida.javhd.presenter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.azul.yida.javhd.R;
import com.azul.yida.javhd.common.Contants;
import com.azul.yida.javhd.common.JavHdApp;
import com.azul.yida.javhd.common.Mlog;
import com.azul.yida.javhd.model.Film;
import com.azul.yida.javhd.net.DownloadUtils;
import com.azul.yida.javhd.net.JsDownloadListener;
import com.azul.yida.javhd.presenter.base.BasePresentActivity;
import com.azul.yida.javhd.view.FilmDetailView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.lang.reflect.Field;

public class FilmDetailActivity extends BasePresentActivity<FilmDetailView> {
    @Override
    public Class<FilmDetailView> getPresentClass() {
        return FilmDetailView.class;
    }
    private Film film;
    @Override
    public void onNetWorkErorRetry() {

    }

    public static void StartFilmDetail(Context context,Film film){
        Intent intent=new Intent(context,FilmDetailActivity.class);
        intent.putExtra("film",film);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
        this.film=getIntent().getParcelableExtra("film");
        super.onCreate(savedInstanceState);
        mvpView.setFilm(getIntent().getParcelableExtra("film"));
    }

    @Override
    protected void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_play:
                startDownloadFilm();
                break;
        }
    }

    private void startDownloadFilm(){
        DownloadUtils downloadUtils=new DownloadUtils(new JsDownloadListener() {
            @Override
            public void onStartDownload() {
                Mlog.t("开始下载");
            }

            @Override
            public void onProgress(int progress) {
                mvpView.setDownloadProgressBar(progress);
               // Mlog.t("开始下载"+progress);
            }

            @Override
            public void onFinishDownload() {
                Mlog.t("onFinishDownload");
            }

            @Override
            public void onFail(String errorInfo) {
                Mlog.t("onFail"+errorInfo);
            }
        });
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granked -> {
                    if (granked) {
                        downloadUtils.download(film.getRealurl(), Environment.getExternalStorageDirectory()+Contants.ROOT_DIR,film.getPid());
                    }
                });
    }
}
