package com.azul.yida.javhd.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.azul.yida.javhd.R;
import com.azul.yida.javhd.adapter.AllFilmAdapter;
import com.azul.yida.javhd.common.ImgLoader;
import com.azul.yida.javhd.common.JavHdApp;
import com.azul.yida.javhd.common.Mlog;
import com.azul.yida.javhd.model.Film;
import com.azul.yida.javhd.model.javModel;
import com.azul.yida.javhd.presenter.FilmDetailActivity;
import com.azul.yida.javhd.view.base.MvpView;
import com.azul.yida.javhd.weidget.SmartToolbar;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.rohitarya.glide.facedetection.transformation.FaceCenterCrop;
import com.rohitarya.glide.facedetection.transformation.core.GlideFaceDetector;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

import static com.chad.library.adapter.base.BaseQuickAdapter.SCALEIN;
import static com.chad.library.adapter.base.BaseQuickAdapter.SLIDEIN_BOTTOM;
import static com.chad.library.adapter.base.BaseQuickAdapter.SLIDEIN_RIGHT;

public class AllFilmView extends MvpView {
    @BindView(R.id.toolbar)
    SmartToolbar smartToolbar;
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;

    @BindView(R.id.rl_modulename_refresh)
    BGARefreshLayout rlModulenameRefresh;
    private AllFilmAdapter baseQuickAdapter;
    @Override
    public int getLayoutId() {
        return R.layout.activity_all_film;
    }

    @Override
    public void regist(@NonNull LayoutInflater inflater, LifecycleProvider provider) {
        super.regist(inflater,provider);
        GlideFaceDetector.initialize(getActivity());
        initRv();
        smartToolbar.post(()->smartToolbar.transparent());
    }
    private void initRv() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        baseQuickAdapter = new AllFilmAdapter(null);
        recycleView.setLayoutManager(linearLayoutManager);
        recycleView.setAdapter(baseQuickAdapter);
        baseQuickAdapter.setOnItemClickListener((a,v,p)->{
            FilmDetailActivity.StartFilmDetail(getActivity(), ((javModel)a.getData().get(p)).getFilm());
        });
        baseQuickAdapter.openLoadAnimation(SCALEIN);
        baseQuickAdapter.isFirstOnly(false);

        ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(baseQuickAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
        itemTouchHelper.attachToRecyclerView(recycleView);

// open drag
        baseQuickAdapter.enableDragItem(itemTouchHelper, R.id.tv_title, true);
       // baseQuickAdapter.setOnItemDragListener(onItemDragListener);

// open slide to delete
        baseQuickAdapter.enableSwipeItem();
        //baseQuickAdapter.setOnItemSwipeListener(onItemSwipeListener);
    }


    @Override
    public View getToolbar() {
        return smartToolbar;
    }

    public void setData(List<javModel> list){
        ArrayList<javModel> list2=new ArrayList<>();
        for(int i=0;i<list.size();i++){
            if(list.get(i).getFilm().getRealurl()!=null&&list.get(i).getFilm().getRealurl().length()!=0){
                list2.add(list.get(i));
            }
        }
        baseQuickAdapter.addData(list2);
        baseQuickAdapter.notifyDataSetChanged();

    }

}
