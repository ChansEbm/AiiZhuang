package com.appbaba.iz.ui.fragment.Comm;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.appbaba.iz.FragmentCommSellerBinding;
import com.appbaba.iz.ItemListBinding;
import com.appbaba.iz.R;
import com.appbaba.iz.adapters.CommonAdapter;
import com.appbaba.iz.adapters.ViewHolder;
import com.appbaba.iz.base.BaseFgm;
import com.appbaba.iz.entity.SellerListBean;
import com.appbaba.iz.eum.NetworkParams;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruby on 2016/4/13.
 */
public class CommSellerListFragment extends BaseFgm {

    private FragmentCommSellerBinding sellerBinding;
    private ListView ltv_data;
    private EditText editText;
    private TextView tv_search;

    private  boolean status = false;

    //软件盘弹起后所占高度阀值
    private int keyHeight = 0;

    private  CommonAdapter<SellerListBean.SellerEntity> adapter;
    private List<SellerListBean.SellerEntity> list,list_temp;

    @Override
    protected void initViews() {
        sellerBinding = (FragmentCommSellerBinding)viewDataBinding;
        sellerBinding.includeTopTitle.toolBar.setNavigationIcon(R.mipmap.more_arrow_dark_left);
        sellerBinding.includeTopTitle.title.setText("选择品牌");
        sellerBinding.includeTopTitle.toolBar.setBackgroundColor(Color.WHITE);
        sellerBinding.includeTopTitle.title.setTextColor(Color.BLACK);

        ltv_data = sellerBinding.ltvData;
        editText = sellerBinding.edtSearch;
        tv_search = sellerBinding.tvSearch;

        list =new ArrayList<>();
        list_temp = new ArrayList<>();

        adapter = new CommonAdapter<SellerListBean.SellerEntity>(list,getContext(),R.layout.item_seller_list_view) {
            @Override
            public void convert(int position, ViewHolder holder, SellerListBean.SellerEntity sellerEntity) {
               holder.setText(R.id.tv_item_view,sellerEntity.getBrand());
            }
        };
        ltv_data.setAdapter(adapter);

        networkModel.getSellerList(NetworkParams.SELLERLIST);

    }

    @Override
    protected void initEvents() {

        sellerBinding.includeTopTitle.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
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
                     if(list_temp.get(i).getBrand().contains(s.toString()))
                     {
                         list.add(list_temp.get(i));
                     }
                }
                adapter.notifyDataSetChanged();
            }
        });

        sellerBinding.getRoot().addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {

                } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
                    editText.clearFocus();

                }
            }
        });

        ltv_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent();
                intent.putExtra("id",list.get(position).getSeller_id());
                intent.putExtra("name",list.get(position).getBrand());
                ((Activity)getContext()).setResult(100,intent);
                ((Activity)getContext()).finish();
            }
        });
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
        return R.layout.fragment_comm_seller_list;
    }


    @Override
    public void onJsonObjectSuccess(Object t, NetworkParams paramsCode) {
        if(paramsCode==NetworkParams.SELLERLIST) {
            SellerListBean bean = (SellerListBean) t;
            list.addAll(bean.getList());
            list_temp.addAll(bean.getList());
            adapter.notifyDataSetChanged();
        }
    }
}
