package com.appbaba.platform.ui.fragment.inspiration;

import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.appbaba.platform.FragmentDesignerIMBinding;
import com.appbaba.platform.R;
import com.appbaba.platform.base.BaseFragment;
import com.appbaba.platform.entity.Base.BaseBean;
import com.appbaba.platform.entity.User.DesignerEMBean;
import com.appbaba.platform.eum.NetworkParams;
import com.appbaba.platform.method.MethodConfig;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMChatManager;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.exceptions.HyphenateException;

/**
 * Created by ruby on 2016/6/8.
 */
public class DesignerIMFragment extends BaseFragment {

    private FragmentDesignerIMBinding binding;
    private ViewSwitcher viewSwitcher;

    private Handler handler;
    private String designerEmID;

    @Override
    protected void InitView() {
       binding = (FragmentDesignerIMBinding)viewDataBinding;
        viewSwitcher = binding.viewSwitcher;

    }

    @Override
    protected void InitData() {
        handler = new Handler();
        if(EMClient.getInstance().isLoggedInBefore())
        {
            viewSwitcher.showNext();
        }
        else
        {
            if(MethodConfig.userInfo!=null && !TextUtils.isEmpty(MethodConfig.userInfo.getToken()))
            {
                binding.etUsername.setText(MethodConfig.userInfo.getEusername());
                binding.etPassword.setText(MethodConfig.userInfo.getEpassword());
            }
        }
        if(MethodConfig.IsLogin()) {
            networkModel.GetUserEMID(MethodConfig.userInfo.getToken(), designerID, NetworkParams.CUPCAKE);
        }
    }

    @Override
    protected void InitEvent() {

    }

    @Override
    protected void InitListening() {
        binding.btnLogin.setOnClickListener(this);
    }

    @Override
    protected void OnClick(int id, View view) {
          switch (id)
          {
              case R.id.btn_login:
              {

                          //登录
                          EMClient.getInstance().login(binding.etUsername.getText().toString(),binding.etPassword.getText().toString(), new EMCallBack() {

                              @Override
                              public void onSuccess() {
                                 onSuccessDo();
                              }

                              @Override
                              public void onProgress(int progress, String status) {

                              }

                              @Override
                              public void onError(int code, String error) {
                                  onFailureDo();
                              }
                          });

              }
                  break;
          }
    }

    EaseChatFragment easeChatFragment;
    public void onSuccessDo()
    {
        handler.post(new Runnable() {
            @Override
            public void run() {
                viewSwitcher.showNext();
                //.putExtra(EaseConstant.EXTRA_USER_ID, conversation.getUserName()));
//                EaseChatFragment easeChatFragment = new
            }
        });
    }

    public void onFailureDo()
    {
        handler.post(new Runnable() {
            @Override
            public void run() {
                    Toast.makeText(getContext(), "登录失败", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_designer_im;
    }

    @Override
    public void onJsonObjectSuccess(BaseBean baseBean, NetworkParams paramsCode) {
        if(baseBean.getErrorcode()==0)
        {
            if(paramsCode==NetworkParams.CUPCAKE)
            {
                DesignerEMBean emBean = (DesignerEMBean) baseBean;
                designerEmID = emBean.getDesign_infor().getEasemob_username();
                easeChatFragment = new EaseChatFragment();
                //传入参数
                Intent intent = new Intent();
                intent.putExtra(EaseConstant.EXTRA_USER_ID, designerEmID);
                easeChatFragment.setArguments(intent.getExtras());
                getChildFragmentManager().beginTransaction().add(R.id.frame_content,easeChatFragment).commit();
            }
        }
    }

    private String designerID = "";

    public String getDesignerID() {
        return designerID;
    }

    public void setDesignerID(String designerID) {
        this.designerID = designerID;
    }
}
