package com.appbaba.iz.ui.fragment;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.appbaba.iz.FragmentFriendClientBinding;
import com.appbaba.iz.ItemFriendClientBinding;
import com.appbaba.iz.R;
import com.appbaba.iz.adapters.CommonAdapter;
import com.appbaba.iz.adapters.CommonBinderAdapter;
import com.appbaba.iz.adapters.CommonBinderHolder;
import com.appbaba.iz.adapters.ViewHolder;
import com.appbaba.iz.base.BaseFgm;
import com.appbaba.iz.entity.Friends.FriendsBean;
import com.appbaba.iz.entity.Friends.FriendsClientBean;
import com.appbaba.iz.eum.NetworkParams;
import com.appbaba.iz.impl.InputCallBack;
import com.appbaba.iz.method.MethodConfig;
import com.appbaba.iz.method.SpaceItemDecoration;
import com.appbaba.iz.model.AddClientModel;
import com.appbaba.iz.tools.AppTools;
import com.appbaba.iz.tools.LogTools;
import com.appbaba.iz.ui.activity.TransferActivity;
import com.appbaba.iz.widget.ScrollView.SlideView;

import org.solovyev.android.views.llm.LinearLayoutManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ruby on 2016/4/8.
 */
public class FriendsItemClientFragment extends BaseFgm implements  Toolbar.OnMenuItemClickListener{
    private FragmentFriendClientBinding clientBinding;
    private EditText editText;
    private ListView ltv_data;
    private TextView tv_search;

    private  boolean status = false;
    //屏幕高度
    private int screenHeight = 0;
    //软件盘弹起后所占高度阀值
    private int keyHeight = 0;
    private CommonAdapter<FriendsClientBean.ListEntity> adapter;

    private List<FriendsClientBean.ListEntity> list;
    private SlideView sliderViewTemp;

    private HashMap<String,SlideView> hashMap = new HashMap<>();


    @Override
    protected void initViews() {

        clientBinding = (FragmentFriendClientBinding)viewDataBinding;
        clientBinding.includeTopTitle.toolBar.setBackgroundColor(Color.WHITE);
        clientBinding.includeTopTitle.toolBar.setNavigationIcon(R.mipmap.more_arrow_dark_left);
        clientBinding.includeTopTitle.toolBar.getMenu().add(0,R.id.menu_add,0,"").setIcon(R.mipmap.icon_friend_add).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        clientBinding.includeTopTitle.title.setText("我的客户");
        clientBinding.includeTopTitle.title.setTextColor(Color.BLACK);

        editText = clientBinding.edtSearch;
        tv_search = clientBinding.tvSearch;
        ltv_data = clientBinding.ltvData;
        editText.clearFocus();

        keyHeight = MethodConfig.metrics.heightPixels/3;
        list = new ArrayList<>();



       adapter = new CommonAdapter<FriendsClientBean.ListEntity>(list,getContext(),R.layout.item_friend_client_view) {
           @Override
           public void convert(int position, ViewHolder holder, FriendsClientBean.ListEntity client) {
                   holder.setText(R.id.tv_item_name,client.getName());
                   holder.setText(R.id.tv_item_phone,client.getPhone());
                   holder.setText(R.id.tv_item_collects,client.getCollects());
               AddClientModel model = new AddClientModel();
               model.setId(client.getCustomer_id());
               model.setName(client.getName());
               model.setArea_ids(client.getArea_ids());
               model.setPhone(client.getPhone());
               model.setAddress(client.getAddress());

               holder.getViews(R.id.btn_item_edit).setTag(model);
               holder.getViews(R.id.btn_item_del).setTag(model);
               holder.getViews(R.id.btn_item_edit).setOnClickListener(FriendsItemClientFragment.this);
               holder.getViews(R.id.btn_item_del).setOnClickListener(FriendsItemClientFragment.this);

               hashMap.put(""+position,(SlideView)holder.getConvertView());

           }
       };
        

        ltv_data.setAdapter(adapter);


    }

    @Override
    public void onResume() {
        super.onResume();
        networkModel.HomeMarketingCustomerList(MethodConfig.localUser.getAuth(),NetworkParams.CUSTOMERLIST);
    }

    @Override
    protected void initEvents() {

        clientBinding.includeTopTitle.toolBar.setOnMenuItemClickListener(this);

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (status != hasFocus && editText.getText().toString().trim().length() == 0) {
                    status = hasFocus;
                    StartAnimation();
                }

            }
        });

        clientBinding.getRoot().addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {

                } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
                    editText.clearFocus();

                }
            }
        });

        ltv_data.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (sliderViewTemp != null && sliderViewTemp.isShow == false) {
                            sliderViewTemp = null;
                        }
                        if (sliderViewTemp == null) {

                            int position = ltv_data.pointToPosition((int) event.getX(), (int) event.getY());
                            if (position >= 0) {
                                sliderViewTemp = hashMap.get(""+position);
                            }
                        } else {
                            sliderViewTemp.back();
                            sliderViewTemp = null;
                        }

                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                if (sliderViewTemp != null ) {
                  return   sliderViewTemp.onRequireTouchEvent(event);
                }
                return false;
            }
        });

    }

    //搜索框动画
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

        switch (id)
        {
            case R.id.btn_item_edit:
                Intent intent = new Intent(getContext(), TransferActivity.class);
                intent.putExtra("fragment",9);
                intent.putExtra("data",(AddClientModel)view.getTag());
                startActivity(intent);
                break;
            case R.id.btn_item_del:
                AddClientModel model = (AddClientModel)view.getTag();
                networkModel.HomeMarketingDelCustomer(MethodConfig.localUser.getAuth(),model.getId(),NetworkParams.CUSTOMERDEL);
                break;
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_friend_item_client;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menu_add:
                Intent intent = new Intent(getContext(), TransferActivity.class);
                intent.putExtra("fragment",9);
                startActivity(intent);
                break;
        }
        return false;
    }

    @Override
    public void onJsonObjectSuccess(Object t, NetworkParams paramsCode) {
        if(paramsCode==NetworkParams.CUSTOMERLIST)
        {
             FriendsClientBean bean = (FriendsClientBean)t;
            if(bean.getErrorcode()==0)
            {
                Log.e("nn",""+bean.getList().size());
                list.clear();
                list.addAll(bean.getList());
                adapter.notifyDataSetChanged();
            }
            else
            {
                AppTools.showNormalSnackBar(getView(),bean.getMsg());
            }
        }
        if(paramsCode==NetworkParams.CUSTOMERDEL)
        {
            networkModel.HomeMarketingCustomerList(MethodConfig.localUser.getAuth(),NetworkParams.CUSTOMERLIST);
        }
    }
}
