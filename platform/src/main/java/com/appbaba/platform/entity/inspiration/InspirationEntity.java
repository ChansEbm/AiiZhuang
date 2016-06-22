package com.appbaba.platform.entity.inspiration;

import android.text.TextUtils;

import com.appbaba.platform.AppKeyMap;

/**
 * Created by ruby on 2016/6/8.
 */
public class InspirationEntity {
    private String id;
    private String title;
    private String label;
    private String image;
    private String thumb;
    private String visit;
    private String love;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getThumb() {
        if(TextUtils.isEmpty(image)) {
            return image;
        }
        else
        {
            return AppKeyMap.BASEURL + image;
        }
    }

    public void setThumb(String thumb) {
        this.image = thumb;
    }

    public String getVisit() {
        return visit;
    }

    public void setVisit(String visit) {
        this.visit = visit;
    }

    public String getLove() {
        return love;
    }

    public void setLove(String love) {
        this.love = love;
    }

    public String getImage() {
        return AppKeyMap.BASEURL+thumb;
    }

    public void setImage(String image) {
        this.thumb = image;
    }
}
