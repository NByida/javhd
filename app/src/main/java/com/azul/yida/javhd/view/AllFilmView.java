package com.azul.yida.javhd.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.azul.yida.javhd.R;
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
import com.rohitarya.glide.facedetection.transformation.FaceCenterCrop;
import com.rohitarya.glide.facedetection.transformation.core.GlideFaceDetector;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

public class AllFilmView extends MvpView {
    @BindView(R.id.toolbar)
    SmartToolbar smartToolbar;
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;

    @BindView(R.id.rl_modulename_refresh)
    BGARefreshLayout rlModulenameRefresh;
    private BaseQuickAdapter<javModel, myViewHolder> baseQuickAdapter;
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
        baseQuickAdapter = new BaseQuickAdapter<javModel, myViewHolder>(R.layout.item) {
            @Override
            protected void convert(myViewHolder helper, javModel item) {
                helper.setVisible(R.id.tv_title, true)
                        .setText(R.id.tv_title,item.getFilm().getTitle())
                        .setVisible(R.id.tv_time, false);
                ImageView imageView = helper.getView(R.id.im_beauty);
                ImgLoader.getInstance().faceCenter(imageView.getContext(),item.getFilm().getPicurl(),imageView);

            }
        };
        recycleView.setLayoutManager(linearLayoutManager);
        recycleView.setAdapter(baseQuickAdapter);
        baseQuickAdapter.setOnItemClickListener((a,v,p)->{
//            setPaper((String)a.getData().get(p));
            FilmDetailActivity.StartFilmDetail(getActivity(), ((javModel)a.getData().get(p)).getFilm());
        });
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
