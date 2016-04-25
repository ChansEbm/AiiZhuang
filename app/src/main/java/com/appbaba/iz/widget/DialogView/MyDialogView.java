package com.appbaba.iz.widget.DialogView;

import android.app.AlertDialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.appbaba.iz.DialogBinding;
import com.appbaba.iz.R;

/**
 * Created by ruby on 2016/3/8.
 */
public class MyDialogView {

    private AlertDialog dialog;
    private View view;
    private  TextView tv_title,tv_message;
    private Button btn_left,btn_right;
    private DialogBinding binding;

    public MyDialogView(Context context, String title, String message)
    {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_custom_buttons_view,null,false);
        view = binding.getRoot();
        dialog = new AlertDialog.Builder(context,R.style.dialog).create();
        init();
        tv_title.setText(title);
        tv_message.setText(message);
        btn_right.setTag(dialog);
        btn_left.setTag(dialog);
    }

    public  void init()
    {
        tv_title = binding.tvDialogTitle;
        tv_message = binding.tvDialogMessage;
        btn_left = binding.btnDialogLeft;
        btn_right = binding.btnDialogRight;
    }

    public  void setTitle(String title)
    {
        tv_title.setText(title);
    }

    public  void  setMessage(String text)
    {
        tv_message.setText(text);
    }

    public  void  setNegativeButton(String text,View.OnClickListener onClickListener)
    {
        btn_right.setText(text);
        btn_right.setOnClickListener(onClickListener);

    }

    public  void  SetPositiveTag(int key,Object object)
    {
        btn_left.setTag(key,object);
    }

    public  void  setPositiveButton(String text,View.OnClickListener onClickListener)
    {
        btn_left.setText(text);
        btn_left.setOnClickListener(onClickListener);
    }



    public  void  show()
    {
        if(!dialog.isShowing())
        {
            dialog.show();
            Window window = dialog.getWindow();
            window.setContentView(view);
        }
    }

    public  void  dismiss()
    {
        dialog.dismiss();
    }

}
