package com.appbaba.platform.entity.User;

import com.appbaba.platform.AppKeyMap;
import com.appbaba.platform.entity.Base.BaseBean;

import java.util.List;

/**
 * Created by ruby on 2016/6/3.
 */
public class UserBean extends BaseBean {

    /**
     * user_infor : {"easemob_password":"14e1b600b1fd579f47433b88e8d85291","easemob_username":"b95e3894fae4844b8277c2edeabb0096","picture_thumb":"","name":"","token":"415bc4c7c49fbf67145a8bbd204b959f"}
     * inspiration_list : [{"title":"地板","thumb":"","status":"0"},{"title":"加西亚瓷砖","thumb":"","status":"2"}]
     * favorite_list : [{"title":"地砖","thumb":"/Public/Uploads/goods/2016-05-31/thumb_574d41fe201c1.jpg"},{"title":"瓷砖","thumb":"/Public/Uploads/goods/2016-05-31/thumb_574ceeaea283c.jpg"}]
     * concern_list : [{"name":"cjq","picture_thumb":"/Public/Uploads/owner/2016-05-30/thumb_574ba79f4f9e1.png"},{"name":"mrcal","picture_thumb":"/Public/Uploads/owner/2016-05-30/thumb_574bb0437da02.jpg"}]
     */

    private UserEntity user;

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public static class UserEntity {
        /**
         * easemob_password : 14e1b600b1fd579f47433b88e8d85291
         * easemob_username : b95e3894fae4844b8277c2edeabb0096
         * picture_thumb :
         * name :
         * token : 415bc4c7c49fbf67145a8bbd204b959f
         */

        private UserInforEntity user_infor;
        /**
         * title : 地板
         * thumb :
         * status : 0
         */

        private List<BaseItemBean> inspiration_list;
        /**
         * title : 地砖
         * thumb : /Public/Uploads/goods/2016-05-31/thumb_574d41fe201c1.jpg
         */

        private List<BaseItemBean> favorite_list;
        /**
         * name : cjq
         * picture_thumb : /Public/Uploads/owner/2016-05-30/thumb_574ba79f4f9e1.png
         */

        private List<BaseItemBean> concern_list;

        public UserInforEntity getUser_infor() {
            return user_infor;
        }

        public void setUser_infor(UserInforEntity user_infor) {
            this.user_infor = user_infor;
        }

        public List<BaseItemBean> getInspiration_list() {
            return inspiration_list;
        }

        public void setInspiration_list(List<BaseItemBean> inspiration_list) {
            this.inspiration_list = inspiration_list;
        }

        public List<BaseItemBean> getFavorite_list() {
            return favorite_list;
        }

        public void setFavorite_list(List<BaseItemBean> favorite_list) {
            this.favorite_list = favorite_list;
        }

        public List<BaseItemBean> getConcern_list() {
            return concern_list;
        }

        public void setConcern_list(List<BaseItemBean> concern_list) {
            this.concern_list = concern_list;
        }

        public static class UserInforEntity {
            private String easemob_password;
            private String easemob_username;
            private String picture_thumb;
            private String name;
            private String token;
            private int type;

            public String getEasemob_password() {
                return easemob_password;
            }

            public void setEasemob_password(String easemob_password) {
                this.easemob_password = easemob_password;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getEasemob_username() {
                return easemob_username;
            }

            public void setEasemob_username(String easemob_username) {
                this.easemob_username = easemob_username;
            }

            public String getPicture_thumb() {
                return AppKeyMap.BASEURL+picture_thumb;
            }

            public void setPicture_thumb(String picture_thumb) {
                this.picture_thumb = picture_thumb;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getToken() {
                return token;
            }

            public void setToken(String token) {
                this.token = token;
            }
        }


    }
}
