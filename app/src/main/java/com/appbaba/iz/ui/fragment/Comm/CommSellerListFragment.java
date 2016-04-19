package com.appbaba.iz.ui.fragment.Comm;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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

    FragmentCommSellerBinding sellerBinding;
    ListView ltv_data;

    CommonAdapter<SellerListBean.SellerEntity> adapter;
    List<SellerListBean.SellerEntity> list;

    @Override
    protected void initViews() {
        sellerBinding = (FragmentCommSellerBinding)viewDataBinding;
        sellerBinding.includeTopTitle.toolBar.setNavigationIcon(R.mipmap.more_arrow_dark_left);
        sellerBinding.includeTopTitle.title.setText("选择品牌");
        sellerBinding.includeTopTitle.toolBar.setBackgroundColor(Color.WHITE);
        sellerBinding.includeTopTitle.title.setTextColor(Color.BLACK);

        ltv_data = sellerBinding.ltvData;

        list =new ArrayList<>();


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
            adapter.notifyDataSetChanged();
        }
    }
}
