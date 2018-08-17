package com.azul.yida.javhd.weidget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.azul.yida.javhd.R;
import com.azul.yida.javhd.common.Mlog;

public class BaseLoadingLayout extends RelativeLayout {
    View loadView;
    public BaseLoadingLayout(Context context) {
        super(context);
    }

    public BaseLoadingLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseLoadingLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            String attributeName = attrs.getAttributeName(i);
            if (attributeName.equals("style")) {
                String attributeValue = attrs.getAttributeValue(i);

            }
        }
    }


    // @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BaseLoadingLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr,defStyleRes);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void startLoading(){
        if(getTag(R.id.always)!=null)return;
        ViewGroup layContent=this;
        for(int a=0;a<layContent.getChildCount();a++){
            layContent.getChildAt(a).setVisibility(View.GONE);
        }
        final Resources res = getContext().getResources();
        final XmlResourceParser parser = res.getLayout(R.layout.progress_bar_style);
        final AttributeSet attrs = Xml.asAttributeSet(parser);
        Mlog.t( "startLoading111@"+ attrs.getAttributeCount());
        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            String attributeName = attrs.getAttributeName(i);
            Mlog.t(String.format("当前属性索引为：%d,索引名为：%s", i,attributeName));
            if (attributeName.equals("style")) {
                String attributeValue = attrs.getAttributeValue(i);
                Mlog.t( "当前属性值为：："+attributeValue);


            }

        }



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            loadView=new ProgressBar(getContext(),null,R.attr.myProgressBaraaa,0);
            ProgressBar progressBar=(ProgressBar)loadView;

        }else {
            //6 loadView=new ProgressBar(getContext(),null,0);
        }
        //  loadView=new ProgressBar(getContext(),null, R.attr.myProgressBar);

        LayoutParams p = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        p.addRule(RelativeLayout.CENTER_IN_PARENT);
        layContent.addView(loadView,p);
        RelativeLayout.LayoutParams params=(RelativeLayout.LayoutParams)loadView.getLayoutParams();
        int width=getWidth()>getHeight()?getHeight()/3:getWidth()/3;
        params.height=width;
        params.width=width;
        loadView.setLayoutParams(params);
        setTag(R.id.always,loadView);
    }

    public void stoptLoading(){
        if(getTag(R.id.always)==null)return;
        removeView((View) getTag(R.id.always));
        setTag(R.id.always,null);
        for(int a=0;a<getChildCount();a++){
            getChildAt(a).setVisibility(View.VISIBLE);
        }
    }

}

