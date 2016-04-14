package com.appbaba.iz.entity.Friends;

import android.databinding.Bindable;

import com.appbaba.iz.entity.Base.BaseBean;

/**
 * Created by ruby on 2016/4/8.
 */
public class FriendsBean extends BaseBean {
    private  String title,detail,date,url;

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Bindable
    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Bindable
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Bindable
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
