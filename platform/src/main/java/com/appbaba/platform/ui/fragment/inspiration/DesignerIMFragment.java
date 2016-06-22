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
import com.appbaba.platform.entity.User.DesignerDetailBean;
import com.appbaba.platform.entity.User.DesignerEMBean;
import com.appbaba.platform.entity.User.FriendsBean;
import com.appbaba.platform.eum.NetworkParams;
import com.appbaba.platform.method.MethodConfig;
import com.appbaba.platform.ui.fragment.comm.CommChatFragment;


/**
 * Created by ruby on 2016/6/8.
 */
public class DesignerIMFragment extends BaseFragment {

    private FragmentDesignerIMBinding binding;
    private ViewSwitcher viewSwitcher;

    private CommChatFragment chatFragment;

    public String designerID = "";
    public String designerEmID="",designerImage="",designerName="";

    public DesignerDetailBean detailBean;


    @Override
    protected void InitView() {
       binding = (FragmentDesignerIMBinding)viewDataBinding;
        viewSwitcher = binding.viewSwitcher;

    }

    @Override
    protected void InitData() {
        if(MethodConfig.IsLogin()) {
            networkModel.GetUserEMID(MethodConfig.userInfo.getToken(), designerID, NetworkParams.CUPCAKE);
            viewSwitcher.showNext();
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
                chatFragment = new CommChatFragment();
                chatFragment.toChatUsername = designerEmID;
                chatFragment.toChatUserImage = MethodConfig.userImage.containsKey(designerID) ? MethodConfig.userImage.get(designerID) : designerImage;
                networkModel.GetFriendList(MethodConfig.userInfo.getToken(),1,100,NetworkParams.FROYO);

                getChildFragmentManager().beginTransaction().add(R.id.frame_content,chatFragment).commit();
            }
            else if(paramsCode==NetworkParams.DONUT)
            {
                Toast.makeText(getContext(),"你和对方已经成为好友啦",Toast.LENGTH_LONG).show();

            }
            else if(paramsCode==NetworkParams.FROYO)
            {
                FriendsBean friendsBean = (FriendsBean)baseBean;//坑爹的加好友方式
                boolean hasFriend = false;
                for(int i=0;i<friendsBean.getFriends_list().size();i++)
                {
                    if(designerID.equals(""+friendsBean.getFriends_list().get(i).getFriend_id()))
                    {
                        hasFriend = true;
                        break;
                    }
                }
                if(!hasFriend)
                {
                    networkModel.AddFriend(MethodConfig.userInfo.getToken(),designerID,NetworkParams.DONUT);
                }

            }
        }
    }

}
