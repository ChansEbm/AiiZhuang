package com.appbaba.platform.widget;

import android.app.Dialog;
import android.content.Context;
import android.database.ContentObservable;
import android.databinding.DataBindingUtil;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.appbaba.platform.DialogChangeNameBinding;
import com.appbaba.platform.R;
import com.appbaba.platform.entity.Base.BaseBean;
import com.appbaba.platform.eum.NetworkParams;
import com.appbaba.platform.impl.LoginCallBack;
import com.appbaba.platform.impl.OkHttpResponseListener;
import com.appbaba.platform.method.MethodConfig;
import com.appbaba.platform.model.NetworkModel;

import java.util.List;

/**
 * Created by ruby on 2016/6/6.
 */
public class ChangeValueDialog implements OkHttpResponseListener {
    private Dialog dialog;
    private DialogChangeNameBinding changeNameBinding;
    private Context context;
    private NetworkModel networkModel;

    public ChangeValueDialog(Context context,String title)
    {
        this.context = context;
         dialog = new Dialog(context, R.style.dialog);
         changeNameBinding = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.dialog_change_name_view,null,false);
         changeNameBinding.tvTitle.setText(title);
        networkModel = new NetworkModel(context);
        Init();
    }

    public void  Init()
    {

        networkModel.setResultCallBack(this);
        changeNameBinding.tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        changeNameBinding.tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 if(!TextUtils.isEmpty(changeNameBinding.edtMsgCode.getText()))
                 {
                     networkModel.MeChangeName(MethodConfig.userInfo.getToken(),changeNameBinding.edtMsgCode.getText().toString().trim(),NetworkParams.DONUT);
                 }
            }
        });
    }

    public void  Show()
    {
        if(!dialog.isShowing())
        {
            dialog.show();
            Window window = dialog.getWindow();
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = MethodConfig.metrics.widthPixels/4*3; //设置宽度
            window.setAttributes(lp);
            window.setContentView(changeNameBinding.getRoot());
        }
    }

    @Override
    public void onJsonObjectResponse(Object o, NetworkParams paramsCode) {
        BaseBean baseBean = (BaseBean)o;
        if(baseBean.getErrorcode()==0)
        {
            Toast.makeText(context,baseBean.getMsg(),Toast.LENGTH_LONG).show();
            MethodConfig.userInfo.setName(changeNameBinding.edtMsgCode.getText().toString().trim());
            dialog.dismiss();
        }
    }

    @Override
    public void onJsonArrayResponse(List t, NetworkParams paramsCode) {

    }

    @Override
    public void onError(String error, NetworkParams paramsCode) {

    }
}
