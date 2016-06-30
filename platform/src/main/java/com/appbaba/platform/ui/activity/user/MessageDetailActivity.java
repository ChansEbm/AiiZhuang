package com.appbaba.platform.ui.activity.user;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.appbaba.platform.ActivityMessageDetailBinding;
import com.appbaba.platform.ActivityMessageListBinding;
import com.appbaba.platform.R;
import com.appbaba.platform.base.BaseActivity;
import com.appbaba.platform.entity.Base.BaseBean;
import com.appbaba.platform.entity.User.MessageDetailBean;
import com.appbaba.platform.eum.NetworkParams;
import com.appbaba.platform.method.MethodConfig;

/**
 * Created by ruby on 2016/6/24.
 */
public class MessageDetailActivity extends BaseActivity {
    private ActivityMessageDetailBinding binding;


    @Override
    protected void InitView() {
        binding = (ActivityMessageDetailBinding)viewDataBinding;
        binding.includeTopTitle.toolBar.setNavigationIcon(R.mipmap.ic_back_dark);
        binding.includeTopTitle.toolBar.setBackgroundColor(Color.WHITE);
        binding.includeTopTitle.title.setTextColor(Color.BLACK);
    }

    @Override
    protected void InitData() {
        String id = getIntent().getStringExtra("id");
        if(TextUtils.isEmpty(id))
        {
            Toast.makeText(this,"id异常",Toast.LENGTH_LONG).show();
            binding.includeTopTitle.title.setText("消息详情");
        }
        else {
            networkModel.UserMessageDetail(MethodConfig.userInfo.getToken(), id, NetworkParams.CUPCAKE);
        }
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

    }

    @Override
    protected void OnClick(int id, View view) {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_message_detail;
    }

    @Override
    public void onJsonObjectSuccess(BaseBean baseBean, NetworkParams paramsCode) {
        if(paramsCode==NetworkParams.CUPCAKE)
        {
            if(baseBean.getErrorcode()==0)
            {
                MessageDetailBean detailBean = (MessageDetailBean)baseBean;
                binding.setItem(detailBean.getDetail());
                binding.includeTopTitle.title.setText(detailBean.getDetail().getTitle());
            }
        }
    }
}
