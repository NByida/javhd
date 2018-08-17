package com.azul.yida.javhd.view.myView;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.azul.yida.javhd.R;
import com.azul.yida.javhd.common.JavHdApp;
import com.azul.yida.javhd.common.Mlog;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class ColorfulProgressBar extends ProgressBar {


    public int TintColor;
    ArrayList<Integer> list=new ArrayList<>();
    public ColorfulProgressBar(Context context) {

        super(context);
        initColor();

    }

    public ColorfulProgressBar(Context context, AttributeSet attrs) {

        super(context, attrs);
        initColor();

    }

    public ColorfulProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initColor();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ColorfulProgressBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initColor();

    }

    public int getTintColor() {
        return TintColor;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setTintColor(int tintColor) {
        TintColor = tintColor;
        setIndeterminateTintList(ColorStateList.valueOf(TintColor));
    }

    private void initColor(){
        list.add(JavHdApp.getInstance().getResources().getColor(R.color.f));
        list.add(JavHdApp.getInstance().getResources().getColor(R.color.colorPrimary));
        list.add(JavHdApp.getInstance().getResources().getColor(R.color.colorAccent));
        list.add(JavHdApp.getInstance().getResources().getColor(R.color.d));
        list.add(JavHdApp.getInstance().getResources().getColor(R.color.e));
        list.add(JavHdApp.getInstance().getResources().getColor(R.color.c));
        list.add(JavHdApp.getInstance().getResources().getColor(R.color.a));
        list.add(JavHdApp.getInstance().getResources().getColor(R.color.colorYellow));
        list.add(JavHdApp.getInstance().getResources().getColor(R.color.f));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            changeColor();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void changeColor(){
        Observable.interval(0,400, TimeUnit.MILLISECONDS)
                .filter(n->this.isAttachedToWindow())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(n->{
                    //Mlog.t(n+"progressBar");
                    ColorStateList colorStateList = ColorStateList.valueOf(list.get((int) (n%list.size())));
                    this.setIndeterminateTintList(colorStateList);
                });
    }




}

