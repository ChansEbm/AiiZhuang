package com.appbaba.iz.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.appbaba.iz.AppKeyMap;
import com.appbaba.iz.FragmentMoreBinding;
import com.appbaba.iz.R;
import com.appbaba.iz.base.BaseFgm;
import com.appbaba.iz.method.MethodConfig;
import com.appbaba.iz.tools.AppTools;
import com.appbaba.iz.ui.activity.LoginActivity;
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

         if(MethodConfig.localUser==null)
         {
             moreBinding.btnLogin.setText("登录");
         }

    }

    @Override
    protected void initEvents() {

        moreBinding.linearPerson.setOnClickListener(this);
        moreBinding.linearChangePwd.setOnClickListener(this);
        moreBinding.linearFeedback.setOnClickListener(this);
        moreBinding.btnLogin.setOnClickListener(this);
        moreBinding.linearPrivate.setOnClickListener(this);
        moreBinding.linearService.setOnClickListener(this);
        moreBinding.linearAboutUs.setOnClickListener(this);
        moreBinding.linearGuide.setOnClickListener(this);
    }

    @Override
    protected void noNetworkStatus() {

    }

    @Override
    protected void onClick(int id, View view) {

        switch (id)
        {
            case R.id.linear_person: {
                if(MethodConfig.localUser==null)
                {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                    ((Activity)getContext()).finish();
                }
                else {
                    Intent intent = new Intent(getContext(), TransferActivity.class);
                    intent.putExtra("fragment", 5);
                    startActivity(intent);
                }
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
            case R.id.btn_login:
            {
                startActivity(new Intent(getContext(), LoginActivity.class));
                ((Activity)getContext()).finish();
            }
                break;
            case R.id.linear_about_us: {
                Intent intent = new Intent(getContext(), TransferActivity.class);
                intent.putExtra("fragment", 14);
                intent.putExtra("title", getString(R.string.more_fragment_about_us));
                intent.putExtra("which", 8);
                intent.putExtra("value", "");
                startActivity(intent);
            }
                break;
            case R.id.linear_service: {
                Intent intent = new Intent(getContext(), TransferActivity.class);
                intent.putExtra("fragment", 14);
                intent.putExtra("title", getString(R.string.more_fragment_service));
                intent.putExtra("which", 9);
                intent.putExtra("value", "");
                startActivity(intent);
            }
                break;
            case R.id.linear_private:
            {
                Intent intent = new Intent(getContext(), TransferActivity.class);
                intent.putExtra("fragment", 14);
                intent.putExtra("title",getString(R.string.more_fragment_private));
                intent.putExtra("which",10);
                intent.putExtra("value","");
                startActivity(intent);
            }
                break;
            case R.id.linear_guide:
            {
                Intent intent = new Intent(getContext(), TransferActivity.class);
                intent.putExtra("fragment", 14);
                intent.putExtra("title",getString(R.string.more_fragment_use_guide));
                intent.putExtra("which",7);
                intent.putExtra("value","");
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
