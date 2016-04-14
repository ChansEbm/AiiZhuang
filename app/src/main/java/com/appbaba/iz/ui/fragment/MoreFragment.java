package com.appbaba.iz.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;

import com.appbaba.iz.FragmentMoreBinding;
import com.appbaba.iz.R;
import com.appbaba.iz.base.BaseFgm;
import com.appbaba.iz.ui.activity.TransferActivity;

/**
 * Created by ruby on 2016/4/1.
 */
public class MoreFragment extends BaseFgm{
    FragmentMoreBinding moreBinding;
    @Override
    protected void initViews() {
       moreBinding = (FragmentMoreBinding)viewDataBinding;
        moreBinding.includeTopTitle.title.setText(R.string.main_activity_bottom_more);
        moreBinding.includeTopTitle.title.setTextColor(Color.BLACK);
        moreBinding.includeTopTitle.toolBar.setBackgroundColor(Color.WHITE);
    }

    @Override
    protected void initEvents() {

        moreBinding.linearPerson.setOnClickListener(this);
        moreBinding.linearChangePwd.setOnClickListener(this);
        moreBinding.linearFeedback.setOnClickListener(this);
    }

    @Override
    protected void noNetworkStatus() {

    }

    @Override
    protected void onClick(int id, View view) {

        switch (id)
        {
            case R.id.linear_person: {
                Intent intent = new Intent(getContext(), TransferActivity.class);
                intent.putExtra("fragment", 5);
                startActivity(intent);
            }
                break;
            case R.id.linear_change_pwd: {
                Intent intent = new Intent(getContext(), TransferActivity.class);
                intent.putExtra("fragment", 6);
                startActivity(intent);
            }
                break;
            case R.id.linear_feedback:
            {
                Intent intent = new Intent(getContext(), TransferActivity.class);
                intent.putExtra("fragment", 10);
                startActivity(intent);
            }
            break;
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_more;
    }
}
