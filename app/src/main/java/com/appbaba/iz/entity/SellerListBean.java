package com.appbaba.iz.entity;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.appbaba.iz.entity.Base.BaseBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruby on 2016/4/13.
 */
public class SellerListBean extends BaseBean {

    private List<SellerEntity> list;

    public List<SellerEntity> getList() {
        return list;
    }

    public void setList(List<SellerEntity> list) {
        this.list = list;
    }

    public static class  SellerEntity extends BaseObservable{
        private  String seller_id;
        private  String brand;

        public String getSeller_id() {
            return seller_id;
        }

        public void setSeller_id(String seller_id) {
            this.seller_id = seller_id;
        }

        @Bindable
        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }
    }
}
