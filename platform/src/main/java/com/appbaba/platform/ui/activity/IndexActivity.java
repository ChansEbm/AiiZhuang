package com.appbaba.platform.ui.activity;

import android.os.CountDownTimer;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.appbaba.platform.ActivityIndexBinding;
import com.appbaba.platform.R;
import com.appbaba.platform.base.BaseActivity;
import com.appbaba.platform.tools.AppTools;
import com.appbaba.platform.tools.SharedPreferencesTools;
import com.appbaba.platform.widget.NavViewPager;
import com.appbaba.platform.widget.SlowViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruby on 2016/5/4.
 */
public class IndexActivity extends BaseActivity {

    private ActivityIndexBinding binding;
    private NavViewPager viewPager;
    private List<ImageView> list = new ArrayList<>();
    private String isFirst = "0";

    @Override
    protected void InitView() {
        binding = (ActivityIndexBinding)viewDataBinding;
        viewPager = binding.viewpager;
    }

    @Override
    protected void InitData() {
        isFirst = AppTools.getSharePreferences().getString("first","0");
        if(isFirst.equals("0")) {
            for (int i = 0; i < 4; i++) {
                ImageView imageView = new ImageView(this);
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
                imageView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                list.add(imageView);
            }
            viewPager.SetAdapter(new PagerAdapter() {
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
            viewPager.setVisibility(View.VISIBLE);
        }
        else
        {
            binding.getRoot().setBackgroundResource(R.mipmap.index);
            viewPager.setVisibility(View.GONE);
        }
    }

    @Override
    protected void InitEvent() {
        if(isFirst.equals("1")) {
            CountDownTimer countDownTimer = new CountDownTimer(3000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    binding.tvCount.setText((millisUntilFinished / 1000 - 1) + " s");
                }

                @Override
                public void onFinish() {
                    StartActivity(MainActivity.class);
                    finish();
                }
            };
            countDownTimer.start();
        }
        else
        {
            viewPager.setCallAction(new NavViewPager.CallAction() {
                @Override
                public void OnEnd() {
                    StartActivity(MainActivity.class);
                    finish();
                }
            });
            viewPager.AutoScroll(1500);
            AppTools.putStringSharedPreferences("first","1");
        }
    }

    @Override
    protected void InitListening() {

    }

    @Override
    protected void OnClick(int id, View view) {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_index;
    }
}
