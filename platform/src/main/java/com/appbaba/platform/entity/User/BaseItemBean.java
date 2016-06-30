package com.appbaba.platform.entity.User;

import android.text.TextUtils;

import com.appbaba.platform.AppKeyMap;

import java.util.Observable;

/**
 * Created by ruby on 2016/6/6.
 */
public class BaseItemBean extends Observable{
    private String title;
    private String name;
    private String image;
    private String status;
    private String picture;
    private String thumb;
    private String picture_thumb;
    private String id;
    private String inspiration_id;
    private String subscribe_id;

    public String getPicture() {
        if(TextUtils.isEmpty(picture))
        {
            return "";
        }
        return AppKeyMap.BASEURL+picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInspiration_id() {
        return inspiration_id;
    }

    public void setInspiration_id(String inspiration_id) {
        this.inspiration_id = inspiration_id;
    }

    public String getSubscribe_id() {
        return subscribe_id;
    }

    public void setSubscribe_id(String subscribe_id) {
        this.subscribe_id = subscribe_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        if(TextUtils.isEmpty(image))
        {
            return "";
        }
        return AppKeyMap.BASEURL+ image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPicture_thumb() {
        return AppKeyMap.BASEURL + picture_thumb;
    }

    public void setPicture_thumb(String picture_thumb) {
        this.picture_thumb = picture_thumb;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
