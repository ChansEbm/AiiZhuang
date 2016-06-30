package com.appbaba.platform.entity.User;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.appbaba.platform.entity.Base.BaseBean;

import java.util.List;

/**
 * Created by ruby on 2016/6/24.
 */
public class MessageListBean extends BaseBean {

    /**
     * user_push_id : 25
     * title : 加西亚周年派对-全民撕名牌
     * content : 地中海乡村馆-浓情暖调
     * addtime : 2016-06-21 15:44
     * is_read : 0
     * url : www.baidu.com
     */

    private List<PushInformationEntity> push_information;

    public List<PushInformationEntity> getPush_information() {
        return push_information;
    }

    public void setPush_information(List<PushInformationEntity> push_information) {
        this.push_information = push_information;
    }

    public static class PushInformationEntity extends BaseObservable{
        private int user_push_id;
        private String title;
        private String content;
        private String addtime;
        private int is_read;
        private String url;

        public int getUser_push_id() {
            return user_push_id;
        }

        public void setUser_push_id(int user_push_id) {
            this.user_push_id = user_push_id;
        }

        @Bindable
        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        @Bindable
        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        @Bindable
        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public int getIs_read() {
            return is_read;
        }

        public void setIs_read(int is_read) {
            this.is_read = is_read;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
