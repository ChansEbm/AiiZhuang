package com.appbaba.iz.ui.activity.album;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.appbaba.iz.R;
import com.appbaba.iz.ZoomEffectLayout;
import com.appbaba.iz.base.BaseAty;
import com.bm.library.PhotoView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ZoomEffectActivity extends BaseAty {
    private ArrayList<String> photoPaths = new ArrayList<>();
    private ViewPager viewPager;
    private int index = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        photoPaths.clear();
        photoPaths.addAll(getIntent().getStringArrayListExtra("photoPaths"));
        index = getIntent().getIntExtra("index", -1);
    }

    @Override
    protected void initViews() {
        defaultTitleBar(this);
        ZoomEffectLayout effectLayout = (ZoomEffectLayout) viewDataBinding;
        viewPager = effectLayout.viewPager;
    }

    @Override
    protected void initEvents() {
        viewPager.setAdapter(new EffectAdapter());
        if (index != -1)
            viewPager.setCurrentItem(index, false);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_zoom_effect;
    }

    @Override
    protected void onClick(int id, View view) {

    }


    class EffectAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return photoPaths.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            PhotoView photoView = new PhotoView(ZoomEffectActivity.this);
            photoView.enable();
            photoView.setScaleType(ImageView.ScaleType.FIT_XY);
            ViewPager.LayoutParams layoutParams = new ViewPager.LayoutParams();
            layoutParams.height = ViewPager.LayoutParams.MATCH_PARENT;
            layoutParams.width = ViewPager.LayoutParams.MATCH_PARENT;
            photoView.setAdjustViewBounds(true);
            Picasso.with(ZoomEffectActivity.this).load(photoPaths.get(position))
                    .into(photoView);
            container.addView(photoView, layoutParams);
            return photoView;
        }
    }
}
