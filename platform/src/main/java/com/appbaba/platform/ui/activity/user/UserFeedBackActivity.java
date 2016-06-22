package com.appbaba.platform.ui.activity.user;

import android.graphics.Color;
import android.view.View;
import android.widget.Toast;

import com.appbaba.platform.ActivityFeedBackBinding;
import com.appbaba.platform.R;
import com.appbaba.platform.base.BaseActivity;
import com.appbaba.platform.entity.Base.BaseBean;
import com.appbaba.platform.eum.NetworkParams;
import com.appbaba.platform.impl.BinderOnItemClickListener;
import com.appbaba.platform.method.MethodConfig;

/**
 * Created by ruby on 2016/6/21.
 */
public class UserFeedBackActivity extends BaseActivity {
    private ActivityFeedBackBinding binding;

    @Override
    protected void InitView() {
        binding = (ActivityFeedBackBinding)viewDataBinding;
        binding.includeTopTitle.title.setText(R.string.activity_user_feedback_title);
        binding.includeTopTitle.title.setTextColor(Color.BLACK);
        binding.includeTopTitle.toolBar.setNavigationIcon(R.mipmap.icon_back);

    }

    @Override
    protected void InitData() {

    }

    @Override
    protected void InitEvent() {
        binding.includeTopTitle.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void InitListening() {
        binding.btnSend.setOnClickListener(this);

    }

    @Override
    protected void OnClick(int id, View view) {

        switch (id)
        {
            case R.id.btn_send:
            {
                //没登录不能反馈？ ->_->
                if(binding.edtFeedback.getText().toString().trim().length()<6)
                {
                    Toast.makeText(this,"填写内容不能少于6字",Toast.LENGTH_LONG).show();
                }
                else {
                    String token = MethodConfig.IsLogin() ? MethodConfig.userInfo.getToken() : "";
                    networkModel.FeedBack(token,binding.edtFeedback.getText().toString(), NetworkParams.CUPCAKE);
                }
            }
                break;
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_feedback;
    }

    @Override
    public void onJsonObjectSuccess(BaseBean baseBean, NetworkParams paramsCode) {
        if(paramsCode==NetworkParams.CUPCAKE)
        {
            if(baseBean.getErrorcode()==0)
            {
                Toast.makeText(this,"反馈成功",Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }
}
