package com.appbaba.iz.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuWrapperFactory;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.appbaba.iz.FragmentMorePersonBinding;
import com.appbaba.iz.R;
import com.appbaba.iz.base.BaseFgm;
import com.appbaba.iz.eum.PhotoPopupOpts;
import com.appbaba.iz.impl.OnPhotoOptsSelectListener;
import com.appbaba.iz.method.MethodConfig;
import com.appbaba.iz.tools.CameraTools;
import com.appbaba.iz.ui.activity.TransferActivity;
import com.appbaba.iz.widget.PopupWindow.TakePhotoPopupWindow;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.net.URL;
import java.util.List;

import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * Created by ruby on 2016/4/8.
 */
public class MoreItemPersonFragment extends BaseFgm implements Toolbar.OnMenuItemClickListener{
    private FragmentMorePersonBinding personBinding;

    @Override
    protected void initViews() {
//        Fresco.initialize(getContext());
        personBinding = (FragmentMorePersonBinding)viewDataBinding;
        personBinding.includeTopTitle.title.setText(R.string.more_fragment_person);
        personBinding.includeTopTitle.title.setTextColor(Color.BLACK);
        personBinding.includeTopTitle.toolBar.setBackgroundColor(Color.WHITE);
        personBinding.includeTopTitle.toolBar.setNavigationIcon(R.mipmap.more_arrow_dark_left);
        personBinding.includeTopTitle.toolBar.getMenu().add(0,R.id.menu_edit,0,"编辑").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

    }

    @Override
    public void onResume() {
        super.onResume();
        if(MethodConfig.localUser!=null)
        {
            if(!TextUtils.isEmpty(MethodConfig.localUser.getInfo().getAvatar()));
            personBinding.ivHead.setImageURI(Uri.parse(MethodConfig.localUser.getInfo().getAvatar()));

            personBinding.setItem(MethodConfig.localUser.getInfo());
        }
    }

    @Override
    protected void initEvents() {
         personBinding.includeTopTitle.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 ((Activity)getContext()).finish();
             }
         });
//        personBinding.ivHead.setOnClickListener(this);
        personBinding.includeTopTitle.toolBar.setOnMenuItemClickListener(this);
    }

    @Override
    protected void noNetworkStatus() {

    }

    @Override
    protected void onClick(int id, View view) {

        switch (id)
        {

            case R.id.iv_head:

                break;
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_more_item_person;
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menu_edit:
                Intent intent = new Intent(getContext(),TransferActivity.class);
                intent.putExtra("fragment",15);
                startActivity(intent);
                break;
        }
        return false;
    }
}
