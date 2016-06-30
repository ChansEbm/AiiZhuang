package com.appbaba.iz.ui.activity;

import android.os.CountDownTimer;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.appbaba.iz.ActivityIndexBinding;
import com.appbaba.iz.R;
import com.appbaba.iz.base.BaseAty;
import com.appbaba.iz.eum.NetworkParams;
import com.appbaba.iz.tools.AppTools;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by ruby on 2016/5/26.
 */
public class IndexActivity extends BaseAty {

    private ActivityIndexBinding indexBinding;
    private ViewPager viewPager;
    private boolean isFirst = false;

    @Override
    protected void initViews() {
        indexBinding = (ActivityIndexBinding)viewDataBinding;
        viewPager = indexBinding.viewpager;
        String first = AppTools.getSharePreferences().getString("first","");
        if(TextUtils.isEmpty(first))
        {
            indexBinding.linearIndex.setVisibility(View.GONE);
            indexBinding.viewpager.setVisibility(View.VISIBLE);
            AppTools.putStringSharedPreferences("first","1");
        }
        else
        {
            isFirst = true;
            indexBinding.linearIndex.setVisibility(View.VISIBLE);
            indexBinding.viewpager.setVisibility(View.GONE);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }

    boolean isRun = false;
    @Override
    protected void initEvents() {

        if(isFirst)
        {
            CountDownTimer countDownTimer = new CountDownTimer(3000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    IndexActivity.this.start(HomeActivity.class);
                    finish();
                }
            };
            countDownTimer.start();
            StartAnimation();
        }
        else {
            final List<ImageView> list = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                ImageView imageView = new ImageView(getBaseContext());
                imageView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                switch (i) {
                    case 0:
                        imageView.setImageResource(R.mipmap.icon_index_first);
                        break;
                    case 1:
                        imageView.setImageResource(R.mipmap.icon_index_second);
                        break;
                    case 2:
                        imageView.setImageResource(R.mipmap.icon_index_third);
                        break;
                    case 3:
                        imageView.setImageResource(R.mipmap.icon_index_forth);
                        break;
                }

                list.add(imageView);
            }
            viewPager.setAdapter(new PagerAdapter() {

                @Override
                public int getCount() {
                    return list.size();
                }

                @Override
                public boolean isViewFromObject(View view, Object object) {
                    return view == object;
                }

                @Override
                public Object instantiateItem(ViewGroup container, int position) {

                    container.addView(list.get(position));

                    return list.get(position);
                }

                @Override
                public void destroyItem(ViewGroup container, int position, Object object) {
                    container.removeView(list.get(position));
                }
            });

            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    if (position == list.size() - 1) {
                        CountDownTimer countDownTimer = new CountDownTimer(2000, 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {

                            }

                            @Override
                            public void onFinish() {
                                IndexActivity.this.start(HomeActivity.class);
                                finish();
                            }
                        };
                        countDownTimer.start();
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }

    }

    TranslateAnimation translateAnimation;
    public void StartAnimation()
    {
         translateAnimation = new TranslateAnimation(0,0,500,0);
        translateAnimation.setDuration(2000);
        translateAnimation.setInterpolator(new LinearInterpolator());
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                indexBinding.ivIndexBottom.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        indexBinding.ivIndexBottom.setAnimation(translateAnimation);
        translateAnimation.startNow();
    }


    @Override
    protected int getContentView() {
        return R.layout.activity_index;
    }

    @Override
    protected void onClick(int id, View view) {

    }

    @Override
    public void onJsonObjectResponse(Object o, NetworkParams paramsCode) {

    }
}
