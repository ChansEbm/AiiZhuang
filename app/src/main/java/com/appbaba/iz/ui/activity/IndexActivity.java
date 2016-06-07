package com.appbaba.iz.ui.activity;

import android.graphics.Color;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.appbaba.iz.ActivityIndexBinding;
import com.appbaba.iz.R;
import com.appbaba.iz.base.BaseAty;
import com.appbaba.iz.eum.NetworkParams;
import com.appbaba.iz.tools.AppTools;
import com.appbaba.iz.widget.NavViewPager;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by ruby on 2016/5/26.
 */
public class IndexActivity extends BaseAty {

    private ActivityIndexBinding indexBinding;
    private NavViewPager viewPager;
    private boolean isFirst = false;
    private Handler handler;
    @Override
    protected void initViews() {
        handler = new Handler();
        indexBinding = (ActivityIndexBinding)viewDataBinding;
        viewPager = indexBinding.navViewpager;
        String first = AppTools.getSharePreferences().getString("first","");
        if(TextUtils.isEmpty(first))
        {
            AppTools.putStringSharedPreferences("first","1");
        }
        else
        {
            isFirst = true;
            start(LoginActivity.class);
            finish();
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

    @Override
    protected void initEvents() {
        if(isFirst)
        {
            return;
        }
        final List<ImageView> list = new ArrayList<>();
        for(int i=0;i<4;i++)
        {
            ImageView imageView = new ImageView(getBaseContext());
            imageView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            switch (i)
            {
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
        viewPager.SetPointViewStyle(NavViewPager.PointsView.POINT_STYLE_CIRCLE,Color.TRANSPARENT,Color.TRANSPARENT,Color.TRANSPARENT,0);
        viewPager.AutoScroll(1500);
//        ImageView img;
//        img.setImageURI(Uri.);
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
