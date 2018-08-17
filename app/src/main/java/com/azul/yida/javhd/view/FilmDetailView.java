package com.azul.yida.javhd.view;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.azul.yida.javhd.R;
import com.azul.yida.javhd.common.ImgLoader;
import com.azul.yida.javhd.model.Film;
import com.azul.yida.javhd.view.base.MvpView;
import com.azul.yida.javhd.weidget.SmartToolbar;
import com.rohitarya.glide.facedetection.transformation.core.GlideFaceDetector;
import com.trello.rxlifecycle2.LifecycleProvider;

import butterknife.BindView;

public class FilmDetailView extends MvpView {
    Film film;
    @BindView(R.id.toolbar)
    SmartToolbar toolbar;
    @BindView(R.id.tv_custom_title)
    TextView tvCustomTitle;
    @BindView(R.id.tv_title_right)
    TextView tvTitleRight;
    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;
    @BindView(R.id.download_progressBar)
    ProgressBar downloadProgressBar;
    @BindView(R.id.bt_play)
    Button btPlay;
    @BindView(R.id.vedio_view)
    VideoView videoView;
    @BindView(R.id.image_play)
    ImageView imageView;


    @Override
    public void regist(@NonNull LayoutInflater inflater, LifecycleProvider provider) {
        super.regist(inflater, provider);
        GlideFaceDetector.initialize(getActivity());
        toolbar.post(() -> toolbar.transparent());
        postClick(btPlay);
    }



    @Override
    public int getLayoutId() {
        return R.layout.activity_film_detail;
    }

    public void setFilm(Film film) {
        String pic = film.getPicurl();
        String url = film.getRealurl();
//        if(pic!=null) Mlog.t("pic"+pic);
//        if(url!=null) Mlog.t("url"+url);
        this.film = film;
        ImgLoader.getInstance().faceCenter(getActivity(), film.getVideobackimg(), imageView);
        if (film.getRealurl() == null) return;
        videoView.setVideoURI(Uri.parse(film.getRealurl()));
        imageView.setVisibility(View.GONE);
        videoView.start();
    }

    public void setDownloadProgressBar(int progress){
        downloadProgressBar.setProgress(progress);
    }

    @Override
    public View getToolbar() {
        return toolbar;
    }
}
