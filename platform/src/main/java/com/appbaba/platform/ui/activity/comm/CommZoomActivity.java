package com.appbaba.platform.ui.activity.comm;

import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.appbaba.platform.ActivityZoomBinding;
import com.appbaba.platform.R;
import com.appbaba.platform.base.BaseActivity;
import com.bm.library.PhotoView;
import com.squareup.picasso.Picasso;

/**
 * Created by ruby on 2016/6/16.
 */
public class CommZoomActivity extends BaseActivity {
    private ActivityZoomBinding binding;
    private PhotoView photoView;
    private  String url = "",name="";

    @Override
    protected void InitView() {
        binding = (ActivityZoomBinding)viewDataBinding;


        binding.includeTopTitle.toolBar.setBackgroundColor(Color.WHITE);
        binding.includeTopTitle.toolBar.setNavigationIcon(R.mipmap.icon_back);
        binding.includeTopTitle.title.setTextColor(Color.BLACK);
        photoView = new PhotoView(this);

    }

    @Override
    protected void InitData() {
        url = getIntent().getStringExtra("url");
        name = getIntent().getStringExtra("name");

        binding.includeTopTitle.title.setText(name);

        photoView.enable();
        photoView.setScaleType(ImageView.ScaleType.FIT_XY);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        photoView.setAdjustViewBounds(true);
        Picasso.with(this).load(url)
                .into(photoView);
        Picasso.with(this).load(url).into(photoView);
        binding.linearBg.addView(photoView,layoutParams);
    }

    @Override
    protected void InitEvent() {
          binding.includeTopTitle.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  onBackPressed();
              }
          });
    }

    @Override
    protected void InitListening() {

    }

    @Override
    protected void OnClick(int id, View view) {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_zoom;
    }
}
