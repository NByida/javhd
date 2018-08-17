package com.azul.yida.javhd.presenter.base;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.azul.yida.javhd.R;
import com.azul.yida.javhd.presenter.LifeCycleBasePresenter.BaseDialogFragment;


/**
 * Created by xuyimin on 2018/6/19.
 * E-mail codingyida@qq.com
 */

public  class BaseConfirmFragment extends BaseDialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog=new Dialog(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View parent=inflater.inflate(R.layout.base_confirm_dialog,null);
        ViewGroup rlLayout=parent.findViewById(R.id.lay_content);
        TextView textView=new TextView(getActivity(),null,1);
        textView.setText("aaaaa");
        rlLayout.addView(textView);
        dialog.setContentView(parent);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_transparent));
        return dialog;
    }
}
