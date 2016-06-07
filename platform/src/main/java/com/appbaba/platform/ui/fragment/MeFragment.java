package com.appbaba.platform.ui.fragment;

import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appbaba.platform.AppKeyMap;
import com.appbaba.platform.FragmentMeBinding;
import com.appbaba.platform.R;
import com.appbaba.platform.adapters.CommonBinderAdapter;
import com.appbaba.platform.adapters.CommonBinderHolder;
import com.appbaba.platform.base.BaseFragment;
import com.appbaba.platform.entity.Base.BaseBean;
import com.appbaba.platform.entity.User.UserBean;
import com.appbaba.platform.entity.User.UserInfo;
import com.appbaba.platform.eum.NetworkParams;
import com.appbaba.platform.impl.BinderOnItemClickListener;
import com.appbaba.platform.impl.LoginCallBack;
import com.appbaba.platform.method.MethodConfig;
import com.appbaba.platform.method.SpaceItemDecoration;
import com.appbaba.platform.tools.AppTools;
import com.appbaba.platform.ui.activity.MeSettingActivity;
import com.appbaba.platform.widget.LoginDialog;
import com.appbaba.platform.widget.MyTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruby on 2016/5/4.
 */
public class MeFragment extends BaseFragment implements ViewPager.OnPageChangeListener{
    private FragmentMeBinding binding;
    private ViewPager viewPager;
    private LinearLayout linear_move;

    private List<Fragment> fragments;
    private int moveWidth = 0;

    @Override
    protected void InitView() {
        binding = (FragmentMeBinding)viewDataBinding;
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
        for(int i=0;i<3;i++)
        {
            MeItemFragment itemFragment = new MeItemFragment();
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
    }

    @Override
    protected void OnClick(int id, View view) {
        switch (id)
        {
            case R.id.iv_setting:
                StartActivity(MeSettingActivity.class);
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
        }
    }


    @Override
    protected int getContentView() {
        return R.layout.fragment_me;
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
        if(baseBean.getErrorcode()==0)
        {
            if(paramsCode==NetworkParams.DONUT)
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
                MethodConfig.userInfo.setImgUrl(AppKeyMap.BASEURL+userbean.getUser().getUser_infor().getPicture_thumb());
                MethodConfig.userInfo.setEusername(userbean.getUser().getUser_infor().getEasemob_username());
                MethodConfig.userInfo.setEpassword(userbean.getUser().getUser_infor().getEasemob_password());
                InitUserInfo();
                for(int i = 0 ;i <fragments.size();i++)
                {
                    switch (i)
                    {
                        case 0:
                            ((MeItemFragment)fragments.get(i)).AddListData(userbean.getUser().getInspiration_list());
                            break;
                        case 1:
                            ((MeItemFragment)fragments.get(i)).AddListData(userbean.getUser().getFavorite_list());
                            break;
                        case 2:
                            ((MeItemFragment)fragments.get(i)).AddListData(userbean.getUser().getConcern_list());
                            break;
                    }

                }
            }
        }
    }

    public void InitUserInfo()
    {
        if(MethodConfig.userInfo!=null)
        {
            binding.tvName.setVisibility(View.VISIBLE);
            binding.tvLogin.setVisibility(View.GONE);
            binding.tvName.setText(MethodConfig.userInfo.getName());
            if(!TextUtils.isEmpty(MethodConfig.userInfo.getImgUrl()))
            {
                binding.dvHead.setImageURI(Uri.parse(MethodConfig.userInfo.getImgUrl()));
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
