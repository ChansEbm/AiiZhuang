package com.appbaba.platform.entity.User;

import com.appbaba.platform.AppKeyMap;
import com.appbaba.platform.entity.Base.BaseBean;

import java.util.List;

/**
 * Created by ruby on 2016/6/17.
 */
public class FriendsBean extends BaseBean {

    /**
     * friend_id : 16
     * picture : /Public/Uploads/User/16/2016-06/5757dfc124d87.jpg
     * name : 15015555352
     * easemob_username : 13f210125fb9df9a3f339d1696b85d00
     */

    private List<FriendsListEntity> friends_list;

    public List<FriendsListEntity> getFriends_list() {
        return friends_list;
    }

    public void setFriends_list(List<FriendsListEntity> friends_list) {
        this.friends_list = friends_list;
    }

    public static class FriendsListEntity {
        private int friend_id;
        private String picture;
        private String name;
        private String easemob_username;

        public int getFriend_id() {
            return friend_id;
        }

        public void setFriend_id(int friend_id) {
            this.friend_id = friend_id;
        }

        public String getPicture() {
            return AppKeyMap.BASEURL+picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEasemob_username() {
            return easemob_username;
        }

        public void setEasemob_username(String easemob_username) {
            this.easemob_username = easemob_username;
        }
    }
}
