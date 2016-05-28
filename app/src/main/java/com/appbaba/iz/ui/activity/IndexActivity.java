package com.appbaba.iz.ui.activity;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.appbaba.iz.ActivityIndexBinding;
import com.appbaba.iz.R;
import com.appbaba.iz.base.BaseAty;
import com.appbaba.iz.eum.NetworkParams;
import com.appbaba.iz.widget.NavViewPager;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by ruby on 2016/5/26.
 */
public class IndexActivity extends BaseAty {

    private ActivityIndexBinding indexBinding;
    private NavViewPager viewPager;
    private boolean isFirst = true;
    @Override
    protected void initViews() {
          indexBinding = (ActivityIndexBinding)viewDataBinding;
          viewPager = indexBinding.navViewpager;
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

    @Override
    protected void initEvents() {
        final List<ImageView> list = new ArrayList<>();
        for(int i=0;i<3;i++)
        {
            ImageView imageView = new ImageView(getBaseContext());
            imageView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
            imageView.setBackgroundColor(i % 2 == 0 ? Color.GREEN : Color.BLACK);
            list.add(imageView);
        }
         viewPager.SetAdapter(new PagerAdapter() {

             @Override
             public int getCount() {
                 return list.size();
             }

             @Override
             public boolean isViewFromObject(View view, Object object) {
                 return view==object;
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
        viewPager.setCallAction(new NavViewPager.CallAction() {
            @Override
            public void OnEnd() {
                start(LoginActivity.class);
                finish();
            }
        });
        CountDownTimer timer = new CountDownTimer(1500*viewPager.GetVPCount(),1500) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(!isFirst) {
                    viewPager.Next();
                }
                else
                {
                    isFirst = false;
                }
            }

            @Override
            public void onFinish() {
                if(viewPager.GetVPCurrentIndex()==list.size()-1) {
                    viewPager.ScrollEnd();
                }
                else
                {
                    this.start();
                }
            }
        };
        timer.start();
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
