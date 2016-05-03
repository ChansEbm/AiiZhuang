package com.appbaba.iz.entity.Index;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.appbaba.iz.entity.Base.BaseBean;

import java.util.List;

/**
 * Created by ruby on 2016/4/14.
 */
public class HomeBean extends BaseBean{

    /**
     * seller_id : 1
     * brand : 加西亚陶瓷
     * full_name : 你好
     * logo : http://192.168.200.123/appbaba2016/Public/Uploads/seller/1/set/2016-04-11/570b1debaba1f.jpg
     * banner : http://192.168.200.123/appbaba2016/Public/Uploads/seller/1/set/2016-04-11/570b1debaba1f.jpg
     */

    private SellerInfoEntity seller_info;
    /**
     * cate_id : 18
     * title : 我的分类二
     * en_title : my cate 2
     * desc : 描述描述二
     * index_id : 1
     * index_thumb : http://192.168.200.123/appbaba2016/Public/Uploads/seller/1/cases/2016-04-11/570b5f53ecbfd.jpg
     */

    private List<CateListEntity> cate_list;
    /**
     * subject_id : 1
     * title : 啊实打实大
     * thumb : http://192.168.200.123/appbaba2016/Public/Uploads/seller/1/cases/2016-04-08/570758c07aaca.jpg
     */

    private List<SubjectListEntity> subject_list;

    public SellerInfoEntity getSeller_info() {
        return seller_info;
    }

    public void setSeller_info(SellerInfoEntity seller_info) {
        this.seller_info = seller_info;
    }

    public List<CateListEntity> getCate_list() {
        return cate_list;
    }

    public void setCate_list(List<CateListEntity> cate_list) {
        this.cate_list = cate_list;
    }

    public List<SubjectListEntity> getSubject_list() {
        return subject_list;
    }

    public void setSubject_list(List<SubjectListEntity> subject_list) {
        this.subject_list = subject_list;
    }

    public static class SellerInfoEntity {
        private String seller_id;
        private String brand;
        private String full_name;
        private String logo;
        private String banner;
        private List<String> hot_label;

        public String getSeller_id() {
            return seller_id;
        }

        public void setSeller_id(String seller_id) {
            this.seller_id = seller_id;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getFull_name() {
            return full_name;
        }

        public void setFull_name(String full_name) {
            this.full_name = full_name;
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

        public List<String> getHot_label() {
            return hot_label;
        }

        public void setHot_label(List<String> hot_label) {
            this.hot_label = hot_label;
        }
    }

    public static class CateListEntity extends BaseObservable {
        private String cate_id;
        private String title;
        private String en_title;
        private String desc;
        private String index_id;
        private String index_thumb;

        public String getCate_id() {
            return cate_id;
        }

        public void setCate_id(String cate_id) {
            this.cate_id = cate_id;
        }

        @Bindable
        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        @Bindable
        public String getEn_title() {
            return en_title;
        }

        public void setEn_title(String en_title) {
            this.en_title = en_title;
        }

        @Bindable
        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getIndex_id() {
            return index_id;
        }

        public void setIndex_id(String index_id) {
            this.index_id = index_id;
        }

        public String getIndex_thumb() {
            return index_thumb;
        }

        public void setIndex_thumb(String index_thumb) {
            this.index_thumb = index_thumb;
        }
    }

    public static class SubjectListEntity extends  BaseObservable{
        private String subject_id;
        private String title;
        private String thumb;

        public String getSubject_id() {
            return subject_id;
        }

        public void setSubject_id(String subject_id) {
            this.subject_id = subject_id;
        }

        @Bindable
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
    }
}
