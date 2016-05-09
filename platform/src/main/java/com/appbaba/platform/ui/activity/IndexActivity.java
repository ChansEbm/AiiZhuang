package com.appbaba.platform.ui.activity;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.appbaba.platform.ActivityIndexBinding;
import com.appbaba.platform.R;
import com.appbaba.platform.base.BaseActivity;

/**
 * Created by ruby on 2016/5/4.
 */
public class IndexActivity extends BaseActivity {

    ActivityIndexBinding binding;

    @Override
    protected void InitView() {
        binding = (ActivityIndexBinding)viewDataBinding;
    }

    @Override
    protected void InitData() {

    }

    @Override
    protected void InitEvent() {
        CountDownTimer countDownTimer = new CountDownTimer(3000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                 binding.tvCount.setText((millisUntilFinished/1000-1)+" s");
            }

            @Override
            public void onFinish() {
                 StartActivity(MainActivity.class);
                finish();
            }
        };
        countDownTimer.start();
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
