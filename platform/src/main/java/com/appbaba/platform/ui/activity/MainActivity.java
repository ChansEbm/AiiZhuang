package com.appbaba.platform.ui.activity;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.appbaba.platform.R;
import com.appbaba.platform.base.BaseActivity;
import com.appbaba.platform.databinding.ActivityMainBinding;
import com.appbaba.platform.entity.Base.BaseBean;
import com.appbaba.platform.entity.User.UserBean;
import com.appbaba.platform.entity.User.UserInfo;
import com.appbaba.platform.eum.NetworkParams;
import com.appbaba.platform.impl.AnimationCallBack;
import com.appbaba.platform.impl.LoginCallBack;
import com.appbaba.platform.method.HYMethod;
import com.appbaba.platform.method.MethodConfig;
import com.appbaba.platform.tools.AppTools;
import com.appbaba.platform.ui.fragment.InspirationFragment;
import com.appbaba.platform.ui.fragment.UserFragment;
import com.appbaba.platform.ui.fragment.ProductFragment;
import com.appbaba.platform.widget.LoginDialog;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;

import java.util.List;


public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener,EMMessageListener{

    private ActivityMainBinding binding;

    private FragmentManager manager;
    private Fragment fragment_temp;
    private InspirationFragment inspirationFragment;
    private ProductFragment productFragment;
    private UserFragment meFragment;
    private ImageView iv_temp;
    private ViewPager viewPager;

    private FragmentAdapter adapter;
    private AnimationCallBack callBack;

    @Override
    protected void InitView() {
         binding = (ActivityMainBinding)viewDataBinding;
        viewPager = binding.viewpager;
        viewPager.setOffscreenPageLimit(3);
    }

    @Override
    protected void InitData() {
        manager = getSupportFragmentManager();
        adapter = new FragmentAdapter(manager);

        Login();
    }

    @Override
    protected void InitEvent() {
       callBack = new AnimationCallBack() {
           @Override
           public void StartAnimation() {
               MainActivity.this.StartAnimation();
           }

           @Override
           public void EndAnimation() {
               MainActivity.this.EndAnimation();
           }
       };
    }

    @Override
    protected void InitListening() {
        binding.ivInspiration.setOnClickListener(this);
        binding.ivProduct.setOnClickListener(this);
        binding.ivMe.setOnClickListener(this);
        binding.ivInspiration.performClick();
        viewPager.addOnPageChangeListener(this);
    }

    @Override
    protected void OnClick(int id, View view) {
        if(iv_temp!=null)
        {
            iv_temp.setSelected(false);
        }
        switch (id)
        {
            case R.id.iv_inspiration: {
                viewPager.setCurrentItem(0);
                iv_temp = binding.ivInspiration;
            }
                break;
            case R.id.iv_product: {
                viewPager.setCurrentItem(1);
                iv_temp = binding.ivProduct;
            }
                break;
            case R.id.iv_me: {
                viewPager.setCurrentItem(2);
                iv_temp = binding.ivMe;
            }
                break;
        }
        iv_temp.setSelected(true);
    }

    @Override
    public void onBackPressed() {
        if(MethodConfig.GetTicks()>2*1000) {
            MethodConfig.ShowToast("再按一次退出软件");
        }
        else
        {
            MethodConfig.userInfo = null;
//            EMClient.getInstance().logout(true);
            super.onBackPressed();
        }
    }

    public void Login()
    {
        String username = AppTools.getSharePreferences().getString("username","");
        String password = AppTools.getSharePreferences().getString("password","");

        if(!(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)))
        {
            //直接调用登录
            MethodConfig.userInfo = new UserInfo();
            MethodConfig.userInfo.setUsername(username);
            MethodConfig.userInfo.setPassword(password);
            networkModel.Login(username,password,"", NetworkParams.DONUT);
        }
        else
        {
            viewPager.setAdapter(adapter);
        }

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onJsonObjectSuccess(BaseBean baseBean, NetworkParams paramsCode) {
       if(paramsCode==NetworkParams.DONUT)
       {
           if(baseBean.getErrorcode()==0)
           {
               if(MethodConfig.userInfo==null)
                   return;
               UserBean userbean = (UserBean) baseBean;
               MethodConfig.userBean = userbean;
               MethodConfig.userInfo.setName(userbean.getUser().getUser_infor().getName());
               MethodConfig.userInfo.setToken(userbean.getUser().getUser_infor().getToken());
               MethodConfig.userInfo.setType(userbean.getUser().getUser_infor().getType());
               MethodConfig.userInfo.setImgUrl(userbean.getUser().getUser_infor().getPicture_thumb());
               MethodConfig.userInfo.setEusername(userbean.getUser().getUser_infor().getEasemob_username());
               MethodConfig.userInfo.setEpassword(userbean.getUser().getUser_infor().getEasemob_password());
               HYMethod hyMethod = new HYMethod();
               hyMethod.Login(MethodConfig.userInfo.getEusername(), MethodConfig.userInfo.getEpassword(), new EMCallBack() {
                   @Override
                   public void onSuccess() {
                       //EMClient.getInstance().chatManager().addMessageListener(MainActivity.this);
                   }

                   @Override
                   public void onError(int i, String s) {

                   }

                   @Override
                   public void onProgress(int i, String s) {

                   }
               });
               viewPager.setAdapter(adapter);
           }
       }
    }

    @Override
    public void onPageSelected(int position) {
                iv_temp.setSelected(false);
        switch (position)
        {
            case 0:
                binding.ivInspiration.setSelected(true);
                iv_temp = binding.ivInspiration;
                break;
            case 1:
                binding.ivProduct.setSelected(true);
                iv_temp = binding.ivProduct;
                break;
            case 2:
                binding.ivMe.setSelected(true);
                iv_temp = binding.ivMe;
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    int which = 0;
    public void StartAnimation() {
        if(which==0) {
            TranslateAnimation animation = new TranslateAnimation(0, 0, 0, 500);
            animation.setDuration(200);
            animation.setInterpolator(new AccelerateInterpolator());
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    binding.ivInspiration.clearAnimation();
                    binding.ivInspiration.setVisibility(View.INVISIBLE);
                    binding.ivProduct.clearAnimation();
                    binding.ivProduct.setVisibility(View.INVISIBLE);
                    binding.ivMe.clearAnimation();
                    binding.ivMe.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            binding.ivInspiration.startAnimation(animation);
            animation = new TranslateAnimation(0, 0, 0, 1000);
            animation.setDuration(500);
            animation.setInterpolator(new AccelerateInterpolator());
            binding.ivProduct.startAnimation(animation);
            animation = new TranslateAnimation(0, 0, 0, 1500);
            animation.setDuration(1000);
            animation.setInterpolator(new AccelerateInterpolator());
            binding.ivMe.startAnimation(animation);
            which = 1;
        }
    }

    public void EndAnimation() {
        if(which==1) {
            TranslateAnimation animation = new TranslateAnimation(0, 0, 1000, 0);
            animation.setDuration(400);
            animation.setInterpolator(new OvershootInterpolator());
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    which = 0;
                    binding.ivInspiration.setVisibility(View.VISIBLE);
                    binding.ivProduct.setVisibility(View.VISIBLE);
                    binding.ivMe.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            binding.ivInspiration.startAnimation(animation);
            animation = new TranslateAnimation(0, 0, 1000, 0);
            animation.setDuration(700);
            animation.setInterpolator(new OvershootInterpolator());
            binding.ivProduct.startAnimation(animation);
            animation = new TranslateAnimation(0, 0, 1000, 0);
            animation.setDuration(1000);
            animation.setInterpolator(new OvershootInterpolator());
            binding.ivMe.startAnimation(animation);
            which = 0;
        }
    }

    @Override
    public void onMessageReceived(List<EMMessage> list) {
       for(int i=0;i<list.size();i++)
       {
           Log.e("received",list.get(i).getBody().toString());
       }
    }

    @Override
    public void onCmdMessageReceived(List<EMMessage> list) {
        for(int i=0;i<list.size();i++)
        {
            Log.e("received",list.get(i).getMsgId());
        }
    }

    @Override
    public void onMessageReadAckReceived(List<EMMessage> list) {
        for(int i=0;i<list.size();i++)
        {
            Log.e("received",list.get(i).getMsgId());
        }
    }

    @Override
    public void onMessageDeliveryAckReceived(List<EMMessage> list) {
        for(int i=0;i<list.size();i++)
        {
            Log.e("received",list.get(i).getMsgId());
        }
    }

    @Override
    public void onMessageChanged(EMMessage emMessage, Object o) {
            Log.e("change",emMessage.getBody().toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().chatManager().removeMessageListener(this);
    }

    public class FragmentAdapter extends FragmentPagerAdapter
    {

        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position)
            {
                case 0: {
                    if (inspirationFragment == null) {
                        inspirationFragment = new InspirationFragment();
                        inspirationFragment.setCallBack(callBack);
                    }
                    return inspirationFragment;
                }
                case 1:{
                    if (productFragment == null) {
                        productFragment = new ProductFragment();
                        productFragment.setCallBack(callBack);
                    }
                    return productFragment;
                }
                case 2:{
                    if (meFragment == null) {
                        meFragment = new UserFragment();
                        meFragment.setCallBack(callBack);
                    }
                    return meFragment;
                }
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }
    }
}
