package com.azul.yida.javhd.adapter;

import android.widget.ImageView;

import com.azul.yida.javhd.R;
import com.azul.yida.javhd.common.ImgLoader;
import com.azul.yida.javhd.model.javModel;
import com.azul.yida.javhd.view.myViewHolder;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;

import java.util.List;

/**
 * Created by xuyimin on 2018/8/23.
 * E-mail codingyida@qq.com
 */

public class AllFilmAdapter extends BaseItemDraggableAdapter<javModel, myViewHolder> {

    public AllFilmAdapter(List<javModel> data) {
        super(R.layout.item,data);
    }

    @Override
    protected void convert(myViewHolder helper, javModel item) {
        helper.setVisible(R.id.tv_title, true)
                .setText(R.id.tv_title,item.getFilm().getTitle())
                .setVisible(R.id.tv_time, false);
        ImageView imageView = helper.getView(R.id.im_beauty);
        ImgLoader.getInstance().faceCenter(imageView.getContext(),item.getFilm().getVideobackimg(),imageView);
    }
}
