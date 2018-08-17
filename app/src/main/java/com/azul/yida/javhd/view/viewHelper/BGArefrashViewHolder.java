package com.azul.yida.javhd.view.viewHelper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.RequiresApi;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.azul.yida.javhd.R;
import com.azul.yida.javhd.common.DisplayUtils;

import cn.bingoogolapple.refreshlayout.BGAStickinessRefreshViewHolder;

public class BGArefrashViewHolder extends BGAStickinessRefreshViewHolder {

    private BGArefreshView mStickinessRefreshView;
    private int colorText=mContext.getResources().getColor(R.color.white);
    private int colorBackground=mContext.getResources().getColor(R.color.colorPrimaryDark);
    private int mRotateImageResId = -1;
    private int mStickinessColorResId = -1;
    /**
     * @param context
     * @param isLoadingMoreEnabled 上拉加载更多是否可用
     */
    public BGArefrashViewHolder(Context context, boolean isLoadingMoreEnabled) {
        super(context, isLoadingMoreEnabled);
        setFootColor(colorText,colorBackground);
    }

    public void setFootColor(@ColorInt int textcolor, @ColorInt int backgroundcolor){
        getLoadMoreFooterView().setBackgroundColor(backgroundcolor);
        ViewGroup.MarginLayoutParams layoutParams=new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams layoutParams1=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams1.gravity= Gravity.CENTER;
        TextView textView=new TextView(mContext);
        textView.setTextColor(textcolor);
        textView.setTextSize(DisplayUtils.sp2px(mContext,8));
        textView.setLayoutParams(layoutParams1);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            textView.setBackground(null);
        }
        textView.setText("努力加載中>.~<");
        ( (ViewGroup)getLoadMoreFooterView()).removeAllViews();
        ((ViewGroup) getLoadMoreFooterView()).addView(textView);
        getLoadMoreFooterView().setLayoutParams(layoutParams);
    }



    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("ResourceAsColor")
    @Override
    public View getRefreshHeaderView() {
        LinearLayout linearLayout= new LinearLayout(mContext);
        linearLayout.setBackgroundColor(colorBackground);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        ViewGroup.LayoutParams layoutParams= new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        ViewGroup.LayoutParams textParams= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        TextView textView=new TextView(mContext);
        linearLayout.addView(textView);
        textView.setLayoutParams(textParams);
        textView.setText("刷新中>.~<");
        textView.setTextSize(DisplayUtils.sp2px(mContext,8));
        textView.setTextColor(colorText);
        textView.setGravity( Gravity.CENTER);
        linearLayout.setLayoutParams(layoutParams);
        mRefreshHeaderView=linearLayout;
        //setPullDistanceScale((float) 0);
        setSpringDistanceScale((float) 0);
        mStickinessRefreshView=new BGArefreshView(mContext);
        mStickinessRefreshView.setStickinessRefreshViewHolder(this);
        return mRefreshHeaderView;
    }

    /**
     * 设置旋转图片资源
     *
     * @param resId
     */
    public void setRotateImage(@DrawableRes int resId) {
        mRotateImageResId = resId;
    }

    /**
     * 设置黏性颜色资源
     *
     * @param resId
     */
    public void setStickinessColor(@ColorRes int resId) {
        mStickinessColorResId = resId;
    }

    @Override
    public void handleScale(float scale, int moveYDistance) {
        //mStickinessRefreshView.setMoveYDistance(500);
    }

    @Override
    public void changeToIdle() {

        mStickinessRefreshView.smoothToIdle();
    }

    @Override
    public void changeToPullDown() {
    }

    @Override
    public void changeToReleaseRefresh() {
    }

    @Override
    public void changeToRefreshing() {
        mStickinessRefreshView.startRefreshing();
    }

    @Override
    public void onEndRefreshing() {
        mStickinessRefreshView.stopRefresh();
    }

    @Override
    public boolean canChangeToRefreshingStatus() {
        return mStickinessRefreshView.canChangeToRefreshing();
    }
}

