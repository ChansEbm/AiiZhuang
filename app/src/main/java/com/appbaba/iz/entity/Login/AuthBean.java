package com.appbaba.iz.entity.Login;

import com.appbaba.iz.entity.Base.BaseBean;

/**
 * Created by ruby on 2016/4/14.
 */
public class AuthBean extends BaseBean {


    /**
     * seller_id : 1
     * phone : 13590592613
     * nickname : member1
     * avatar :
     * area_ids :
     * area_desc :
     * address : 佛山
     * shop_name : 佛山
     * status : 0
     */

    private InfoEntity info;
    /**
     * info : {"seller_id":"1","phone":"13590592613","nickname":"member1","avatar":"","area_ids":"","area_desc":"","address":"佛山","shop_name":"佛山","status":"0"}
     * auth : Z25TanFiSjhlSzZ1ZUhuZGhucWViUQ==
     */

    private String auth;

    public InfoEntity getInfo() {
        return info;
    }

    public void setInfo(InfoEntity info) {
        this.info = info;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public static class InfoEntity {
        private String seller_id;
        private String phone;
        private String nickname;
        private String avatar;
        private String area_ids;
        private String area_desc;
        private String address;
        private String shop_name;
        private String status;

        public String getSeller_id() {
            return seller_id;
        }

        public void setSeller_id(String seller_id) {
            this.seller_id = seller_id;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getArea_ids() {
            return area_ids;
        }

        public void setArea_ids(String area_ids) {
            this.area_ids = area_ids;
        }

        public String getArea_desc() {
            return area_desc;
        }

        public void setArea_desc(String area_desc) {
            this.area_desc = area_desc;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
