package com.appbaba.iz.entity.Index;

import com.appbaba.iz.AppKeyMap;
import com.appbaba.iz.entity.Base.BaseBean;

import java.util.List;

/**
 * Created by ruby on 2016/6/28.
 */
public class HomeGuideBean extends BaseBean {

    /**
     * id : 1
     * brand : 加西亚陶瓷
     * seller_id : 1
     * logo : /Public/Uploads/seller/1/set/2016-04-14/570f018a25d66.jpg
     * banner : /Public/Uploads/seller/1/set/2016-04-13/570df490c0229.jpg
     * introduce :
     */

    private List<ListEntity> list;
    private List<PictureEntity> picture;

    public List<ListEntity> getList() {
        return list;
    }

    public void setList(List<ListEntity> list) {
        this.list = list;
    }

    public List<PictureEntity> getPicture() {
        return picture;
    }

    public void setPicture(List<PictureEntity> picture) {
        this.picture = picture;
    }

    public static class ListEntity {
        private int id;
        private String brand;
        private int seller_id;
        private String logo;
        private String banner;
        private String introduce;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public int getSeller_id() {
            return seller_id;
        }

        public void setSeller_id(int seller_id) {
            this.seller_id = seller_id;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getBanner() {
            return banner;
        }

        public void setBanner(String banner) {
            this.banner = banner;
        }

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }
    }

    public static class PictureEntity
    {
        private String img;
        private String url;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
