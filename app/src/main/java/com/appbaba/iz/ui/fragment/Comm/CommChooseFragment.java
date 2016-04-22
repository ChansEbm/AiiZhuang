package com.appbaba.iz.ui.fragment.Comm;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.appbaba.iz.AppKeyMap;
import com.appbaba.iz.FragmentCommChooseBinding;
import com.appbaba.iz.FragmentCommSellerBinding;
import com.appbaba.iz.R;
import com.appbaba.iz.adapters.CommonAdapter;
import com.appbaba.iz.adapters.CommonBinderAdapter;
import com.appbaba.iz.adapters.ViewHolder;
import com.appbaba.iz.base.BaseFgm;
import com.appbaba.iz.entity.Base.BaseBean;
import com.appbaba.iz.entity.Friends.FriendsClientBean;
import com.appbaba.iz.eum.NetworkParams;
import com.appbaba.iz.method.MethodConfig;
import com.appbaba.iz.tools.AppTools;
import com.appbaba.iz.ui.activity.TransferActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruby on 2016/4/16.
 */
public class CommChooseFragment extends BaseFgm implements Toolbar.OnMenuItemClickListener{
    private FragmentCommChooseBinding chooseBinding;

    private CommonAdapter<Object> adapter;
    private EditText editText;
    private TextView tv_search;

    private List<Object> list,list_temp;

    private  boolean status = false;

    //软件盘弹起后所占高度阀值
    private int keyHeight = 0;

    private  String title;
    private  int which;

    @Override
    protected void initViews() {

        title = getArguments().getString("title","");
        which = getArguments().getInt("which",-1);

        chooseBinding = (FragmentCommChooseBinding)viewDataBinding;

        chooseBinding.includeTopTitle.toolBar.setNavigationIcon(R.mipmap.more_arrow_dark_left);
        chooseBinding.includeTopTitle.title.setText(title);
        chooseBinding.includeTopTitle.toolBar.setBackgroundColor(Color.WHITE);
        chooseBinding.includeTopTitle.title.setTextColor(Color.BLACK);
        if(which==1)
        {
              chooseBinding.includeTopTitle.toolBar.getMenu().add(0,R.id.menu_add,0,"").setIcon(R.mipmap.icon_friend_add).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        }

        editText = chooseBinding.edtSearch;
        tv_search = chooseBinding.tvSearch;

        editText.clearFocus();
        keyHeight = MethodConfig.metrics.heightPixels/3;


        if(which==-1)
        {
            ((Activity)getContext()).finish();
        }

        list = new ArrayList<>();
        list_temp= new ArrayList<>();
        adapter = new CommonAdapter<Object>(list,getContext(),R.layout.item_comm_ltv_view) {
            @Override
            public void convert(int position, ViewHolder holder, Object o) {
                if(which==1)
                {
                    FriendsClientBean.ListEntity entity = (FriendsClientBean.ListEntity)o;
                    holder.setText(R.id.tv_item_view,entity.getName());
                    holder.setText(R.id.tv_item_detail_view,entity.getPhone());
                    holder.getViews(R.id.tv_item_detail_view).setVisibility(View.VISIBLE);
                    holder.getConvertView().setTag(R.string.tag_value,entity);
                }

            }
        };
        chooseBinding.ltvData.setAdapter(adapter);

    }

    @Override
    protected void initEvents() {

        chooseBinding.includeTopTitle.toolBar.setOnMenuItemClickListener(this);
        chooseBinding.includeTopTitle.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((Activity)getContext()).finish();
            }
        });

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (status != hasFocus && editText.getText().toString().trim().length() == 0) {
                    status = hasFocus;
                    StartAnimation();
                }

            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                 list.clear();
                for(int i=0;i<list_temp.size();i++)
                {
                    switch (which)
                    {
                        case 1:
                        {
                            FriendsClientBean.ListEntity entity = (FriendsClientBean.ListEntity)list_temp.get(i);
                            if(entity.getName().contains(s.toString()))
                            {
                                list.add(list_temp.get(i));
                            }
                        }
                        break;
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });

        chooseBinding.getRoot().addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {

                } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
                    editText.clearFocus();

                }
            }
        });

        chooseBinding.ltvData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(which==1) {
                    MethodConfig.chooseClient = (FriendsClientBean.ListEntity)view.getTag(R.string.tag_value);
                    AppTools.putStringSharedPreferences(AppKeyMap.CUSTOMERID,MethodConfig.chooseClient.getCustomer_id());
                }
                Intent intent = new Intent();
                ((Activity)getContext()).setResult(100,intent);
                ((Activity)getContext()).finish();
            }
        });
        switch (which)
        {
            case 1:
                networkModel.HomeMarketingCustomerList(MethodConfig.localUser.getAuth(), NetworkParams.CUSTOMERLIST);
                break;
        }
    }

    public void StartAnimation()
    {
        float x1 =0, x2 =0,x3 = 0,x4 = 0;
        x1 = tv_search.getX();
        x2 = editText.getX();
        if(status)
        {

            x3 = 0;
            x4 = x2-x1;
        }
        else
        {
            x3 = x2-x1;
            x4 = 0;
        }
        TranslateAnimation animation = new TranslateAnimation(x3,x4,0,0);
        animation.setDuration(500);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                tv_search.setText("");
                tv_search.setVisibility(View.VISIBLE);
                editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (status) {
                    editText.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_friend_search, 0, 0, 0);
                    editText.requestFocus();
                    tv_search.setVisibility(View.INVISIBLE);
                } else {
                    editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    editText.clearFocus();
                    tv_search.setText("搜索");
                    tv_search.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        tv_search.startAnimation(animation);
    }


    @Override
    protected void noNetworkStatus() {

    }

    @Override
    protected void onClick(int id, View view) {

    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_comm_choose;
    }

    @Override
    public void onJsonObjectSuccess(Object t, NetworkParams paramsCode) {
        if(paramsCode==NetworkParams.CUSTOMERLIST)
        {
            FriendsClientBean bean = (FriendsClientBean)t;
            list.addAll(bean.getList());
            list_temp.addAll(bean.getList());
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menu_add:
                if(which==1) {
                    Intent intent = new Intent(getContext(), TransferActivity.class);
                    intent.putExtra("fragment", 9);
                    startActivity(intent);
                }
                break;
        }
        return  false;
    }
}
