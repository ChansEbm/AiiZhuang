package com.appbaba.iz.entity.Favourite;

import android.databinding.Bindable;

import com.appbaba.iz.entity.Base.BaseBean;

/**
 * Created by ruby on 2016/4/7.
 */
public class FavouriteBean extends BaseBean{
    private String title,url,detail;
    private int like,seen;

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Bindable
    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Bindable
    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    @Bindable
    public int getSeen() {
        return seen;
    }

    public void setSeen(int seen) {
        this.seen = seen;
    }
}
