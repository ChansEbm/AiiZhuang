package com.appbaba.iz.entity.Friends;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;

import com.appbaba.iz.entity.Base.BaseBean;
import com.appbaba.iz.model.AddClientModel;
import com.appbaba.iz.widget.ScrollView.SlideView;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ruby on 2016/4/12.
 */
public class FriendsClientBean extends BaseBean {



    /**
     * customer_id : 1
     * name : 张三
     * phone : 13590592613
     * area_ids : 123,123
     * address : 江湾一路十八号
     * collects : 3
     */

    private List<ListEntity> list;

    public List<ListEntity> getList() {
        return list;
    }

    public void setList(List<ListEntity> list) {
        this.list = list;
    }

    public static class ListEntity extends BaseObservable{
        private String customer_id;
        private String name;
        private String phone;
        private String area_ids;
        private String address;
        private String collects;

        public String getCustomer_id() {
            return customer_id;
        }

        public void setCustomer_id(String customer_id) {
            this.customer_id = customer_id;
        }

        @Bindable
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Bindable
        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getArea_ids() {
            return area_ids;
        }

        public void setArea_ids(String area_ids) {
            this.area_ids = area_ids;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        @Bindable
        public String getCollects() {
            return collects;
        }

        public void setCollects(String collects) {
            this.collects = collects;
        }






    }



}
