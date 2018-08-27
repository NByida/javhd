package com.azul.yida.javhd.view;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
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
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.rohitarya.glide.facedetection.transformation.core.GlideFaceDetector;
import com.trello.rxlifecycle2.LifecycleProvider;

import butterknife.BindView;

public class FilmDetailView extends MvpView {
    Film film;
    @BindView(R.id.toolbar)
    SmartToolbar toolbar;

    @BindView(R.id.tv_title_right)
    TextView tvTitleRight;
    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;
    @BindView(R.id.download_progressBar)
    ProgressBar downloadProgressBar;
    @BindView(R.id.bt_play)
    Button btPlay;
    @BindView(R.id.vedio_view)
    PlayerView videoView;
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

        this.film = film;
        ImgLoader.getInstance().faceCenter(getActivity(), film.getVideobackimg(), imageView);
        if (film.getRealurl() == null) return;
//        videoView.setVideoURI(Uri.parse(film.getRealurl()));
        imageView.setVisibility(View.GONE);
//        videoView.start();
        // 1. Create a default TrackSelector
        Handler mainHandler = new Handler();
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        DefaultTrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);

// 2. Create the player
        SimpleExoPlayer player =
                ExoPlayerFactory.newSimpleInstance((Context) getActivity(), trackSelector);
        videoView.setPlayer(player);


// Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getActivity(),Util.getUserAgent(getActivity(), "javhd"));
// This is the MediaSource representing the media to be played.
        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(film.getRealurl()));
// Prepare the player with the source.
        player.prepare(videoSource);


    }

    public void setDownloadProgressBar(int progress){
        if(downloadProgressBar==null)return;
        downloadProgressBar.setProgress(progress);
    }

    @Override
    public View getToolbar() {
        return toolbar;
    }
}
