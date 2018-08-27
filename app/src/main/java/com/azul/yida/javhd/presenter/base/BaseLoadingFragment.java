package com.azul.yida.javhd.presenter.base;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;


import com.azul.yida.javhd.R;
import com.azul.yida.javhd.common.JavHdApp;
import com.azul.yida.javhd.common.Mlog;
import com.azul.yida.javhd.common.ToastUtil;
import com.azul.yida.javhd.presenter.LifeCycleBasePresenter.BaseDialogFragment;
import com.azul.yida.javhd.view.myView.ColorfulProgressBar;
import com.trello.rxlifecycle2.components.RxDialogFragment;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import com.azul.yida.javhd.R;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class BaseLoadingFragment extends BaseDialogFragment {
    ColorfulProgressBar circleProgressBar;
    ArrayList<Integer>list=new ArrayList<>();
    private int backClicktimes=1;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        Dialog dialog=new Dialog(getActivity());
        dialog.setContentView(R.layout.loading_dialog);
        circleProgressBar=dialog.findViewById(R.id.progress_bar);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.dimAmount = 0f;
        window.setAttributes(params);
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_transparent));
        dialog.setOnKeyListener((a,b,c)->{
            if(b == KeyEvent.KEYCODE_BACK){
                backClicktimes++;
                ToastUtil.showToast(getContext(),"加载中。。。少侠留步，再按一次退出");
                if(backClicktimes>2){
                    return false;
                }
                return true;
            }
            else return false;

            }
        );
        return dialog;
    }

    @Override
    public void show(FragmentManager fm,String tag) {
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(this, tag);
        ft.commitAllowingStateLoss();
    }

    public void show(FragmentManager fm){
        show(fm,"loadingFragment");
    }



    @Override
    public void dismiss() {
        dismissAllowingStateLoss();
    }
}
