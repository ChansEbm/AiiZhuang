package com.appbaba.iz.ui.fragment;

import android.app.Activity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.appbaba.iz.FragmentHomeTopNearbyBinding;
import com.appbaba.iz.R;
import com.appbaba.iz.base.BaseFgm;
import com.appbaba.iz.databinding.FragmentFavouriteItemDetailBinding;

/**
 * Created by ruby on 2016/4/7.
 */
public class FavouriteItemDetailFragment extends BaseFgm implements Toolbar.OnMenuItemClickListener{

    FragmentFavouriteItemDetailBinding detailBinding;
    @Override
    protected void initViews() {

        detailBinding = (FragmentFavouriteItemDetailBinding)viewDataBinding;


        detailBinding.includeTopTitle.toolBar.setNavigationIcon(R.mipmap.icon_top_back);

        detailBinding.includeTopTitle.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)getContext()).finish();
            }
        });
        detailBinding.includeTopTitle.toolBar.getMenu().add(Menu.NONE,R.id.menu_unlike,0,"").setIcon(R.mipmap.icon_unlike).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        detailBinding.includeTopTitle.toolBar.getMenu().add(Menu.NONE, R.id.menu_share, 1, "").setIcon(R.mipmap.icon_share).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        detailBinding.includeTopTitle.toolBar.setOnMenuItemClickListener(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_favourite_detail, menu);
    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected void noNetworkStatus() {

    }

    @Override
    protected void onClick(int id, View view) {

    }


    @Override
    protected int getContentView() {
        return R.layout.fragment_favourite_item_detail;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId())
        {
            case  R.id.menu_unlike:
                Toast.makeText(getContext(),"unlike",Toast.LENGTH_LONG).show();
                break;
            case R.id.menu_share:
                Toast.makeText(getContext(),"share",Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }
}
