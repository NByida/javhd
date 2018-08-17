package com.azul.yida.javhd.view.base;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.azul.yida.javhd.R;
import com.azul.yida.javhd.common.Mlog;
import com.azul.yida.javhd.common.SnackBarUtils;
import com.azul.yida.javhd.common.ToastUtil;
import com.azul.yida.javhd.event.OnClickEvent;
import com.azul.yida.javhd.event.loadNetworkEvent;
import com.azul.yida.javhd.presenter.base.BaseLoadingFragment;
import com.azul.yida.javhd.presenter.base.BasePresentActivity;
import com.azul.yida.javhd.weidget.SmartToolbar;
import com.gyf.barlibrary.ImmersionBar;
import com.jakewharton.rxbinding2.view.RxView;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.InterruptedIOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLHandshakeException;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public abstract class MvpView implements BaseView {
    //    private BasePresentActivity activity;
    protected View rootView;
    private BaseLoadingFragment loadingFragment;
    private Unbinder unbinder;
    protected LifecycleProvider mLifecycleProvider;

    private long time=0;
    protected ImmersionBar mImmersionBar;




    @Override
    public void regist(@NonNull LayoutInflater inflater, LifecycleProvider provider) {
        rootView=inflater.inflate(getLayoutId(),null);
        this.mLifecycleProvider = provider;
        unbinder= ButterKnife.bind(this,rootView);
        if (null != getToolbar()) {
            mImmersionBar = ImmersionBar.with(getActivity())
                    .titleBar(getToolbar())
                    .fitsSystemWindows(false);
            mImmersionBar.init();
        }
    }


    public View getRootView() {
        return rootView;
    }

    @Override
    public void unRegist() {
        if(unbinder==null)return;
        unbinder.unbind();
        if (mImmersionBar != null) mImmersionBar.destroy();
        rootView=null;
    }

    @Override
    public void showLoading() {
        if(loadingFragment==null){
            this.loadingFragment=new BaseLoadingFragment();
        }
        loadingFragment.show(((AppCompatActivity)getActivity()).getSupportFragmentManager(),"");
        time=System.currentTimeMillis();
    }

    @Override
    public void dissmissLoading() {
        if(time==0) Mlog.e("dissmissLoading called before showLoading");
        if(loadingFragment==null)return;
       if(loadingFragment.getDialog().isShowing()){
           loadingFragment.dismiss();
       }


    }

    @Override
    public void showErrorMessage(String msg) {
        showToast(msg);
    }

    @Override
    public void showLongMessage(String msg) {
        showToast(msg);
    }

    public void showSnackbar(){
        SnackBarUtils.IndefiniteSnackbar(getRootView(),"无网络",R.color.white, R.color.colorPrimary,-1)
                .setAction("点击重试",v -> {
                    EventBus.getDefault().post(new loadNetworkEvent(this));
                }).show();
    }

    protected   void  go2Activity(Class<?extends BasePresentActivity>  activity) {
        Intent intent=new Intent(getActivity(), activity);
        getActivity().startActivity(intent);

    }

    @Override
    public <T extends Activity> T getActivity() {
        return null != rootView ? (T) rootView.getContext() : null;
    }

    public void showToast(String s){
        new ToastUtil().Short(getActivity(),s).setToastBackground(getActivity().getResources().getColor(R.color.white),R.drawable.bg_round_white).show();
    }

    protected void initToolbar(SmartToolbar toolbar) {
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
    }

    protected void postClick(View view) {
        RxView.clicks(view)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .compose(applySchedulersAndLifecycle())
                .subscribe(it -> EventBus.getDefault()
                        .post(new OnClickEvent(this, view)));
    }

    protected <T> ObservableTransformer<T, T> applySchedulersAndLifecycle() {
        if (mLifecycleProvider instanceof Activity) {
            return observable -> observable.observeOn(AndroidSchedulers.mainThread())
                    .compose(mLifecycleProvider.bindUntilEvent(ActivityEvent.DESTROY));
        } else {
            return observable -> observable.observeOn(AndroidSchedulers.mainThread())
                    .compose(mLifecycleProvider.bindUntilEvent(FragmentEvent.DESTROY));
        }
    }


    public abstract int getLayoutId();


    public View getToolbar() {
        return null;
    }



    public class errorConsumer implements Consumer<Throwable> {

        @Override
        public void accept(Throwable e) throws Exception {
            e.printStackTrace();
            Log.e("test",e.getClass().getName());
            dissmissLoading();
            if (e instanceof SSLHandshakeException) {
                showToast("请检关闭");
            } else if (e instanceof InterruptedIOException ||
                    e instanceof SocketException
                    || e instanceof UnknownHostException) {
                showToast("请检查网络");
            } else {
                //先注释掉，服务端总是报500，好烦。
                showToast("光纤被挖断");
                Log.e("test",""+getClass().getName()+":exception:"+e);
            }
        }
    }

    public errorConsumer consumer=new errorConsumer();

    public void  Back(View view){
        getActivity().onBackPressed();
    }
}

