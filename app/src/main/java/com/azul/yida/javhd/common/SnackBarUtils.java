package com.azul.yida.javhd.common;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.azul.yida.javhd.R;


public class SnackBarUtils {

    public static final   int Info = 1;
    public static final  int Confirm = 2;
    public static final  int Warning = 3;
    public static final  int Alert = 4;


    public static  int red = 0xfff44336;
    public static  int green = 0xff4caf50;
    public static  int purple =0xff4caf50;
    public static  int orange = 0xffffc107;

    /**
     * 短显示Snackbar，自定义颜色
     * @param view
     * @param message
     * @param messageColor
     * @param backgroundColor
     * @return
     */
    public static Snackbar ShortSnackbar(View view, String message, int messageColor, int backgroundColor, @DrawableRes int resId ){
        Snackbar snackbar = Snackbar.make(view,message, Snackbar.LENGTH_SHORT);
        setSnackbarColor(snackbar,messageColor,backgroundColor);
        addLeftDrawable(snackbar,resId);
        return snackbar;
    }

    /**
     * 长显示Snackbar，自定义颜色
     * @param view
     * @param message
     * @param messageColor
     * @param backgroundColor
     * @return
     */
    public static Snackbar LongSnackbar(View view, String message, int messageColor, int backgroundColor,@DrawableRes int resId ){
        Snackbar snackbar = Snackbar.make(view,message, Snackbar.LENGTH_LONG);
        setSnackbarColor(snackbar,messageColor,backgroundColor);
        addLeftDrawable(snackbar,resId);
        return snackbar;
    }

    /**
     * 自定义时常显示Snackbar，自定义颜色
     * @param view
     * @param message
     * @param messageColor
     * @param backgroundColor
     * @return
     */
    public static Snackbar IndefiniteSnackbar(View view, String message,int messageColor, int backgroundColor,@DrawableRes int resId ){
        Snackbar snackbar = Snackbar.make(view,message, Snackbar.LENGTH_INDEFINITE);
        setSnackbarColor(snackbar,messageColor,backgroundColor);
        addLeftDrawable(snackbar,resId);
        return snackbar;
    }

    /**
     * 短显示Snackbar，可选预设类型
     * @param view
     * @param message
     * @param type
     * @return
     */
    public static Snackbar ShortSnackbar(View view, String message, int type,@DrawableRes int resId ){
        Snackbar snackbar = Snackbar.make(view,message, Snackbar.LENGTH_SHORT);
        switchType(snackbar,type);
        addLeftDrawable(snackbar,resId);
        return snackbar;
    }

    /**
     * 长显示Snackbar，可选预设类型
     * @param view
     * @param message
     * @param type
     * @return
     */
    public static Snackbar LongSnackbar(View view, String message,int type,@DrawableRes int resId ){
        Snackbar snackbar = Snackbar.make(view,message, Snackbar.LENGTH_LONG);
        switchType(snackbar,type);

        addLeftDrawable(snackbar,resId);
        return snackbar;
    }

    /**
     * 自定义时常显示Snackbar，可选预设类型
     * @param view
     * @param message
     * @param type
     * @return
     */
    public static Snackbar IndefiniteSnackbar(View view, String message,int type,@DrawableRes int resId ){
        Snackbar snackbar = Snackbar.make(view,message, Snackbar.LENGTH_INDEFINITE);
        switchType(snackbar,type);
        addLeftDrawable(snackbar,resId);
        return snackbar;
    }

    //选择预设类型
    private static void switchType(Snackbar snackbar,int type){
        switch (type){
            case Info:
                setSnackbarColor(snackbar,purple);
                break;
            case Confirm:
                setSnackbarColor(snackbar,green);
                break;
            case Warning:
                setSnackbarColor(snackbar,orange);
                break;
            case Alert:
                setSnackbarColor(snackbar, Color.YELLOW,red);
                break;
        }
    }

    /**
     * 设置Snackbar背景颜色
     * @param snackbar
     * @param backgroundColor
     */
    public static void setSnackbarColor(Snackbar snackbar, int backgroundColor) {
        View view = snackbar.getView();
        if(view!=null){
            view.setBackgroundColor(backgroundColor);
        }
    }

    /**
     * 设置Snackbar文字和背景颜色
     * @param snackbar
     * @param messageColor
     * @param backgroundColor
     */
    public static void setSnackbarColor(Snackbar snackbar, int messageColor, int backgroundColor) {
        View view = snackbar.getView();
        if(view!=null){
            view.setBackgroundColor(view.getContext().getResources().getColor(backgroundColor));
            ((TextView) view.findViewById(R.id.snackbar_text)).setTextColor(view.getContext().getResources().getColor(messageColor));
            ((TextView) view.findViewById(R.id.snackbar_action)).setTextColor(view.getContext().getResources().getColor(messageColor));
        }
    }

    /**
     * 向Snackbar中添加view
     * @param snackbar
     * @param layoutId
     * @param index 新加布局在Snackbar中的位置
     */
    public static void SnackbarAddView(Snackbar snackbar, int layoutId, int index) {
        View snackbarview = snackbar.getView();
        Snackbar.SnackbarLayout snackbarLayout=(Snackbar.SnackbarLayout)snackbarview;

        View add_view = LayoutInflater.from(snackbarview.getContext()).inflate(layoutId,null);

        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        p.gravity= Gravity.CENTER_VERTICAL;

        snackbarLayout.addView(add_view,index,p);
    }

    @SuppressLint("ResourceType")
    public static void addLeftDrawable(Snackbar snackbar, @DrawableRes int resId){
        if(resId<=0)return;
        TextView textView=((View)snackbar.getView()).findViewById(R.id.snackbar_text);

        ImageView imageView=new ImageView(textView.getContext());
        imageView.setImageResource(resId);
        Drawable drawable=textView.getContext().getResources().getDrawable(resId);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
            textView.setCompoundDrawables(drawable,null,null,null);
            textView.setCompoundDrawablePadding(DisplayUtils.dip2px(textView.getContext(),1));
        }
    }

    /**
     * 向Snackbar中添加view
     * @param snackbar
     * @param view
     * @param index 新加布局在Snackbar中的位置
     */
    public static void SnackbarAddView(Snackbar snackbar, View view, int index) {
        View snackbarview = snackbar.getView();
        Snackbar.SnackbarLayout snackbarLayout=(Snackbar.SnackbarLayout)snackbarview;
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        p.gravity= Gravity.CENTER_VERTICAL;
        snackbarLayout.addView(view,index,p);
    }

}
