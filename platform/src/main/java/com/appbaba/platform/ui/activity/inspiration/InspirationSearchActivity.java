package com.appbaba.platform.ui.activity.inspiration;

import android.view.View;

import com.appbaba.platform.ActivityInspirationSearchBinding;
import com.appbaba.platform.R;
import com.appbaba.platform.base.BaseActivity;
import com.appbaba.platform.widget.TagGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruby on 2016/5/10.
 */
public class InspirationSearchActivity extends BaseActivity {
    private ActivityInspirationSearchBinding binding;
    private TagGroup tagGroup;

    @Override
    protected void InitView() {
       binding = (ActivityInspirationSearchBinding)viewDataBinding;
        tagGroup = binding.tagGroup;
    }

    @Override
    protected void InitData() {
        List<String> tags = new ArrayList<>();
        for(int i=0;i<200;i++)
        {
            tags.add(""+System.currentTimeMillis()/10000);
        }
        tagGroup.setTags(tags);

    }

    @Override
    protected void InitEvent() {

    }

    @Override
    protected void InitListening() {

    }

    @Override
    protected void OnClick(int id, View view) {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_inspiration_search;
    }
}
