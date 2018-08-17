package com.azul.yida.javhd.view;

import android.content.Context;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.ImageView;

import com.azul.yida.javhd.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseViewHolder;

public class myViewHolder extends BaseViewHolder {
    public myViewHolder(View view) {
        super(view);
    }

    public void setIamgeUrl(Context context, @IdRes int id, String url){
        Glide.with(context).load(url).error(R.color.colorAccent).diskCacheStrategy(DiskCacheStrategy.SOURCE ).into((ImageView) getView(id));
    }
}
