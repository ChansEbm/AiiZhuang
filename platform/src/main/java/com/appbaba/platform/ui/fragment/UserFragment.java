package com.appbaba.platform.ui.fragment;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.appbaba.platform.AppKeyMap;
import com.appbaba.platform.FragmentUserBinding;
import com.appbaba.platform.R;
import com.appbaba.platform.base.BaseFragment;
import com.appbaba.platform.broadcast.UpdateUIBroadcast;
import com.appbaba.platform.entity.Base.BaseBean;
import com.appbaba.platform.entity.User.BaseItemBean;
import com.appbaba.platform.entity.User.UserBean;
import com.appbaba.platform.entity.User.UserInfo;
import com.appbaba.platform.eum.NetworkParams;
import com.appbaba.platform.impl.AnimationCallBack;
import com.appbaba.platform.impl.LoginCallBack;
import com.appbaba.platform.impl.UpdateUIListener;
import com.appbaba.platform.method.HYMethod;
import com.appbaba.platform.method.MethodConfig;
import com.appbaba.platform.tools.AppTools;
import com.appbaba.platform.ui.activity.user.DesignerCenterActivity;
import com.appbaba.platform.ui.activity.user.FriendsActivity;
import com.appbaba.platform.ui.activity.user.MessageListActivity;
import com.appbaba.platform.ui.activity.user.UserBeDesignerActivity;
import com.appbaba.platform.ui.activity.user.UserSettingActivity;
import com.appbaba.platform.widget.LoginDialog;
import com.appbaba.platform.widget.MyTextView;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruby on 2016/5/4.
 */
public class UserFragment extends BaseFragment implements ViewPager.OnPageChangeListener{
    private FragmentUserBinding binding;
    private ViewPager viewPager;
    private LinearLayout linear_move;
    private AnimationCallBack callBack;
    private List<Fragment> fragments;
    private int moveWidth = 0;

    private int hxCount = 0,jpCount = 0;
    private Handler handler = new Handler();

    private UpdateUIBroadcast receiver;

    private UpdateUIListener uiListener = new UpdateUIListener() {
        @Override
        public void uiUpData(Intent intent) {
            if(intent.getAction().equals(AppKeyMap.MESSAGE_ACTION))
            {
                List<EMMessage> list = intent.getExtras().getParcelableArrayList("message");
                hxCount+=list.size();
                UpdateCountView();
            }
            else if(intent.getAction().equals(AppKeyMap.MESSAGE_UN_READ)) {
                jpCount++;
                // UpdateCountView();
                GetUnReadMsg();
                if (!MethodConfig.IsLogin()) {
                    InitUserInfo();
                }
            }

        }
    };

    @Override
    protected void InitView() {
        binding = (FragmentUserBinding)viewDataBinding;
        viewPager = binding.viewpager;
        linear_move = binding.linearMove;

        binding.linearBeDesigner.getLayoutParams().height = MethodConfig.metrics.widthPixels*100/750;
    }

    @Override
    public void onResume() {
        super.onResume();
        GetUnReadMsg();
        InitUserInfo();
    }

    public void  setCallBack(AnimationCallBack callBack)
    {
        this.callBack = callBack;
    }
    @Override
    protected void InitData() {
            fragments = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                UserItemFragment itemFragment = new UserItemFragment();
                itemFragment.setIndex(i);
                itemFragment.setCallBack(callBack);
                fragments.add(itemFragment);
            }

        moveWidth = linear_move.getLayoutParams().width = MethodConfig.metrics.widthPixels/3;
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(new MyPageAdapter(getChildFragmentManager()));

        receiver = new UpdateUIBroadcast();
        receiver.setListener(uiListener);
        AppTools.registerBroadcast(receiver,AppKeyMap.MESSAGE_ACTION);
        AppTools.registerBroadcast(receiver,AppKeyMap.MESSAGE_UN_READ);
    }

    @Override
    protected void InitEvent() {

    }


    @Override
    protected void InitListening() {

         viewPager.addOnPageChangeListener(this);
        binding.tvMyCollection.setOnClickListener(this);
        binding.tvMyExport.setOnClickListener(this);
        binding.tvMyLike.setOnClickListener(this);

         binding.ivSetting.setOnClickListener(this);
         binding.tvLogin.setOnClickListener(this);
        binding.linearBeDesigner.setOnClickListener(this);
        binding.ivBubble.setOnClickListener(this);
        binding.ivMsg.setOnClickListener(this);
    }

    public void UpdateCountView()
    {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if(hxCount==0)
                {
                    binding.tvHxCount.setVisibility(View.INVISIBLE);
                }
                else
                {
                    binding.tvHxCount.setText(""+hxCount);
                    binding.tvHxCount.setVisibility(View.VISIBLE);
                }

                if(jpCount==0)
                {
                    binding.tvJpCount.setVisibility(View.INVISIBLE);
                }
                else
                {
                    binding.tvJpCount.setText(""+jpCount);
                    binding.tvJpCount.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    @Override
    protected void OnClick(int id, View view) {
        switch (id)
        {
            case R.id.iv_setting:
                startActivityForResult(new Intent(getContext(),UserSettingActivity.class),101);
                break;
            case R.id.tv_login:
                String username = AppTools.getSharePreferences().getString("username","");
                String password = AppTools.getSharePreferences().getString("password","");

                if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password))
                {
                    //不存在账号或者密码，请求用户输入
                    LoginDialog loginDialog = new LoginDialog(getContext());
                    loginDialog.callBack = new LoginCallBack() {
                        @Override
                        public void Login(String username, String password, String token) {
                            networkModel.Login(username,password,token,NetworkParams.DONUT);
                        }
                    };
                    loginDialog.Show();
                }
                else {
                    //直接调用登录
                    MethodConfig.userInfo = new UserInfo();
                    MethodConfig.userInfo.setUsername(username);
                    MethodConfig.userInfo.setPassword(password);
                    networkModel.Login(username,password,"", NetworkParams.DONUT);
                }
                break;
            case R.id.linear_be_designer:
            {
                if(MethodConfig.userInfo.getType()==2)
                {
                    Toast.makeText(getContext(),"已经在审核啦",Toast.LENGTH_LONG).show();
                    return;
                }
                if(MethodConfig.userInfo.getType()!=1) {
                    StartActivity(UserBeDesignerActivity.class);
                }
                else
                {
                     StartActivity(DesignerCenterActivity.class);
                }
            }
                break;
            case R.id.tv_my_collection:
                viewPager.setCurrentItem(0);
                break;
            case R.id.tv_my_export:
                viewPager.setCurrentItem(1);
                break;
            case R.id.tv_my_like:
                viewPager.setCurrentItem(2);
                break;
            case R.id.iv_bubble:
                if(MethodConfig.IsLogin())
                {
                    hxCount = 0;
                    UpdateCountView();
                    StartActivity(FriendsActivity.class);
                }
                else
                {
                    Toast.makeText(getContext(),"你还没有登录...",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.iv_msg:
            {
                if(MethodConfig.IsLogin())
                {
                    StartActivity(MessageListActivity.class);
                }
            }
                break;
        }
    }


    @Override
    protected int getContentView() {
        return R.layout.fragment_me;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode)
        {
            case 101:
                binding.tvLogin.performClick();
                break;
        }
    }

    int first=0;
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if(first==0)
        {
            binding.tvMyCollection.setMM(moveWidth);
            first = 1;
        }
        if(positionOffsetPixels>0)
        {
            MyTextView textView1 = (MyTextView)binding.linearTitleParent.getChildAt(position);
            MyTextView textView2 = (MyTextView)binding.linearTitleParent.getChildAt(position+1);
            textView1.setMM(-1*(int)((1-positionOffset)*moveWidth));
            textView2.setMM((int)(positionOffset*moveWidth));
        }
        else if(positionOffsetPixels<0)
        {
            MyTextView textView1 = (MyTextView)binding.linearTitleParent.getChildAt(position);
            MyTextView textView2 = (MyTextView)binding.linearTitleParent.getChildAt(position-1);
            textView1.setMM((int)((1-positionOffset)*moveWidth));
            textView2.setMM(-1*(int)(positionOffset*moveWidth));
        }
        linear_move.setX(moveWidth*position+moveWidth*positionOffset);
    }

    @Override
    public void onJsonObjectSuccess(BaseBean baseBean, NetworkParams paramsCode) {
        MethodConfig.userBean = null;
        if(paramsCode==NetworkParams.DONUT)
        {
        if(baseBean.getErrorcode()==0)
        {
                String username = MethodConfig.userInfo.getUsername();
                String password = MethodConfig.userInfo.getPassword();
                AppTools.putStringSharedPreferences("username",username);
                AppTools.putStringSharedPreferences("password",password);
                UserBean userbean = (UserBean)baseBean;
                MethodConfig.userInfo = new UserInfo();
                MethodConfig.userInfo.setName(userbean.getUser().getUser_infor().getName());
                MethodConfig.userInfo.setToken(userbean.getUser().getUser_infor().getToken());
                MethodConfig.userInfo.setUsername(username);
                MethodConfig.userInfo.setPassword(password);
                MethodConfig.userInfo.setType(userbean.getUser().getUser_infor().getType());
                MethodConfig.userInfo.setImgUrl(userbean.getUser().getUser_infor().getPicture_thumb());
                MethodConfig.userInfo.setEusername(userbean.getUser().getUser_infor().getEasemob_username());
                MethodConfig.userInfo.setEpassword(userbean.getUser().getUser_infor().getEasemob_password());
            HYMethod hyMethod = new HYMethod();
            hyMethod.Login(MethodConfig.userInfo.getEusername(),MethodConfig.userInfo.getEpassword(),null);
                for(int i = 0 ;i <fragments.size();i++)
                {
                    switch (i)
                    {
                        case 0:
                            ((UserItemFragment)fragments.get(i)).AddListData(userbean.getUser().getInspiration_list());
                            break;
                        case 1:
                            ((UserItemFragment)fragments.get(i)).AddListData(userbean.getUser().getFavorite_list());
                            break;
                        case 2:
                            ((UserItemFragment)fragments.get(i)).AddListData(userbean.getUser().getConcern_list());
                            break;
                    }
                }
            }
            else{
            LoginDialog loginDialog = new LoginDialog(getContext());
            loginDialog.callBack = new LoginCallBack() {
                @Override
                public void Login(String username, String password, String token) {
                    networkModel.Login(username,password,token,NetworkParams.DONUT);
                }
            };
            loginDialog.Show();
        }
        }
        else if(paramsCode==NetworkParams.LOLLIPOP)
        {
             if(baseBean.getErrorcode()==0)
             {
                 jpCount = baseBean.getData();
                 UpdateCountView();
             }
        }
        InitUserInfo();
    }

    public void InitUserInfo()
    {
        if(MethodConfig.IsLogin())
        {
            binding.tvName.setVisibility(View.VISIBLE);
            binding.tvLogin.setVisibility(View.GONE);
            binding.tvName.setText(MethodConfig.userInfo.getName());

            if(!TextUtils.isEmpty(MethodConfig.userInfo.getImgUrl()))
            {
                binding.dvHead.setImageURI(Uri.parse(MethodConfig.userInfo.getImgUrl()));
            }
            else
            {
                binding.dvHead.setImageURI(Uri.parse(""));
            }
            binding.ivType.setImageResource(R.mipmap.icon_designer);
            if(MethodConfig.userInfo.getType()==0)
            {
                binding.linearBeDesigner.setVisibility(View.VISIBLE);
                binding.tvDesignDetail.setText("成为设计师");
            }
            else if(MethodConfig.userInfo.getType()==1)
            {
                binding.linearBeDesigner.setVisibility(View.VISIBLE);
                binding.ivType.setImageResource(R.mipmap.icon_center);
                binding.tvDesignDetail.setText("设计师中心");
            }
            else if(MethodConfig.userInfo.getType()==2)
            {
                binding.linearBeDesigner.setVisibility(View.VISIBLE);
                binding.tvDesignDetail.setText("资格审核中");
            }
            hxCount = EMClient.getInstance().chatManager().getUnreadMsgsCount();
            UpdateCountView();

        }
        else {
            binding.dvHead.setImageURI(Uri.parse(""));
            binding.tvName.setVisibility(View.GONE);
            binding.tvLogin.setVisibility(View.VISIBLE);
            binding.linearBeDesigner.setVisibility(View.GONE);

            if(!MethodConfig.IsLogin())
            {
                if(fragments!=null) {
                    for (int i = 0; i < fragments.size(); i++) {
                        ((UserItemFragment) fragments.get(i)).AddListData(new ArrayList<BaseItemBean>());
                    }
                }
            }
        }
    }

    public void GetUnReadMsg()
    {
        if(MethodConfig.IsLogin())
        networkModel.GetMessageRead(MethodConfig.userInfo.getToken(),NetworkParams.LOLLIPOP);
    }


    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public class MyPageAdapter extends FragmentPagerAdapter
    {

        public MyPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}
