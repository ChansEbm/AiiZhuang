package com.appbaba.platform.ui.fragment;

import android.content.Intent;
import android.net.Uri;
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
import com.appbaba.platform.entity.Base.BaseBean;
import com.appbaba.platform.entity.User.BaseItemBean;
import com.appbaba.platform.entity.User.UserBean;
import com.appbaba.platform.entity.User.UserInfo;
import com.appbaba.platform.eum.NetworkParams;
import com.appbaba.platform.impl.LoginCallBack;
import com.appbaba.platform.method.MethodConfig;
import com.appbaba.platform.tools.AppTools;
import com.appbaba.platform.ui.activity.user.DesignerCenterActivity;
import com.appbaba.platform.ui.activity.user.UserBeDesignerActivity;
import com.appbaba.platform.ui.activity.user.UserSettingActivity;
import com.appbaba.platform.widget.LoginDialog;
import com.appbaba.platform.widget.MyTextView;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruby on 2016/5/4.
 */
public class UserFragment extends BaseFragment implements ViewPager.OnPageChangeListener{
    private FragmentUserBinding binding;
    private ViewPager viewPager;
    private LinearLayout linear_move;

    private List<Fragment> fragments;
    private int moveWidth = 0;

    @Override
    protected void InitView() {
        binding = (FragmentUserBinding)viewDataBinding;
        viewPager = binding.viewpager;
        linear_move = binding.linearMove;
    }

    @Override
    public void onResume() {
        super.onResume();
        InitUserInfo();
    }

    @Override
    protected void InitData() {
            fragments = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                UserItemFragment itemFragment = new UserItemFragment();
                itemFragment.setIndex(i);
                fragments.add(itemFragment);
            }

        moveWidth = linear_move.getLayoutParams().width = MethodConfig.metrics.widthPixels/3;
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(new MyPageAdapter(getChildFragmentManager()));
    }

    @Override
    protected void InitEvent() {
    }

    @Override
    protected void InitListening() {

         viewPager.addOnPageChangeListener(this);
         binding.ivSetting.setOnClickListener(this);
         binding.tvLogin.setOnClickListener(this);
        binding.linearBeDesigner.setOnClickListener(this);
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
                MethodConfig.userInfo.setImgUrl(AppKeyMap.BASEURL+userbean.getUser().getUser_infor().getPicture_thumb());
                MethodConfig.userInfo.setEusername(userbean.getUser().getUser_infor().getEasemob_username());
                MethodConfig.userInfo.setEpassword(userbean.getUser().getUser_infor().getEasemob_password());

            EMClient.getInstance().login(MethodConfig.userInfo.getEusername(), MethodConfig.userInfo.getEpassword(), new EMCallBack() {
                @Override
                public void onSuccess() {
                    Log.e("hx login","success");
                }

                @Override
                public void onError(int i, String s) {

                }

                @Override
                public void onProgress(int i, String s) {

                }
            });
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
            else {
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
        InitUserInfo();
    }

    public void InitUserInfo()
    {
        if(MethodConfig.userInfo!=null && !TextUtils.isEmpty(MethodConfig.userInfo.getToken()))
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
        }
        else {
            binding.dvHead.setImageURI(Uri.parse(""));
            binding.tvName.setVisibility(View.GONE);
            binding.tvLogin.setVisibility(View.VISIBLE);
            binding.linearBeDesigner.setVisibility(View.GONE);

            if(MethodConfig.userInfo==null || TextUtils.isEmpty(MethodConfig.userInfo.getToken()))
            {
                if(fragments!=null) {
                    for (int i = 0; i < fragments.size(); i++) {
                        ((UserItemFragment) fragments.get(i)).AddListData(new ArrayList<BaseItemBean>());
                    }
                }
            }
        }

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
