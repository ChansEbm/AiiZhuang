package com.appbaba.platform.entity.User;

import java.util.Observable;

/**
 * Created by ruby on 2016/6/6.
 */
public class BaseItemBean extends Observable{
    private String title;
    private String thumb;
    private String status;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
