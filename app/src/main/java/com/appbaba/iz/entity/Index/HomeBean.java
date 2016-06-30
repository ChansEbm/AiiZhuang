package com.appbaba.iz.entity.Index;

import com.appbaba.iz.entity.Base.BaseBean;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ruby on 2016/4/14.
 */
public class HomeBean extends BaseBean{

    /**
     * seller_id : 1
     * brand : 加西亚陶瓷
     * full_name : 加西亚瓷砖
     * logo : http://www.izhuangse.com/Public/Uploads/seller/1/set/2016-04-14/570f018a25d66.jpg
     * banner : [{"url":"https://www.baidu.com","img":"http://www.izhuangse.com/Public/Uploads/seller/1/advertise/2016-06-29/5773703d26e2a.jpg"},{"url":"https://www.iqiyi.com","img":"http://www.izhuangse.com/Public/Uploads/seller/1/advertise/2016-06-29/577370608319a.jpg"}]
     * hot_label : ["GPB","1CG"]
     * return : advertise
     */

    private SellerInfoEntity seller_info;
    /**
     * index_thumb : http://www.izhuangse.com/Public/Uploads/seller/1/phone/2016-06-29/57739663df91f.jpg
     * en_title : HTM1-60478A
     * title : 希尔顿瓷片
     * desc : 希尔顿瓷片
     * style_id : 2
     * cate_id : 0
     * size_id : 0
     * return : product
     * index_id : 1
     */

    private List<CateListEntity> cate_list;
    /**
     * subject_id : 15
     * title : 超白通体砖
     * thumb : http://www.izhuangse.com/Public/Uploads/seller/1/subject/2016-05-04/5729abe7731f3.jpg
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
        @SerializedName("return")
        private String returnX;
        /**
         * url : https://www.baidu.com
         * img : http://www.izhuangse.com/Public/Uploads/seller/1/advertise/2016-06-29/5773703d26e2a.jpg
         */

        private List<BannerEntity> banner;
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

        public String getReturnX() {
            return returnX;
        }

        public void setReturnX(String returnX) {
            this.returnX = returnX;
        }

        public List<BannerEntity> getBanner() {
            return banner;
        }

        public void setBanner(List<BannerEntity> banner) {
            this.banner = banner;
        }

        public List<String> getHot_label() {
            return hot_label;
        }

        public void setHot_label(List<String> hot_label) {
            this.hot_label = hot_label;
        }

        public static class BannerEntity {
            private String url;
            private String img;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }
        }
    }

    public static class CateListEntity {
        private String index_thumb;
        private String en_title;
        private String title;
        private String desc;
        private String style_id;
        private String cate_id;
        private String size_id;
        private String space_id;
        private String cases_id;
        @SerializedName("return")
        private String returnX;
        private String index_id;
        private String seller_article_cate;
        private String subject_id;

        public String getIndex_thumb() {
            return index_thumb;
        }

        public void setIndex_thumb(String index_thumb) {
            this.index_thumb = index_thumb;
        }

        public String getEn_title() {
            return en_title;
        }

        public void setEn_title(String en_title) {
            this.en_title = en_title;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getStyle_id() {
            return style_id;
        }

        public void setStyle_id(String style_id) {
            this.style_id = style_id;
        }

        public String getCate_id() {
            return cate_id;
        }

        public void setCate_id(String cate_id) {
            this.cate_id = cate_id;
        }

        public String getSize_id() {
            return size_id;
        }

        public void setSize_id(String size_id) {
            this.size_id = size_id;
        }

        public String getReturnX() {
            return returnX;
        }

        public void setReturnX(String returnX) {
            this.returnX = returnX;
        }

        public String getIndex_id() {
            return index_id;
        }

        public void setIndex_id(String index_id) {
            this.index_id = index_id;
        }

        public String getSpace_id() {
            return space_id;
        }

        public void setSpace_id(String space_id) {
            this.space_id = space_id;
        }

        public String getSeller_article_cate() {
            return seller_article_cate;
        }

        public void setSeller_article_cate(String seller_article_cate) {
            this.seller_article_cate = seller_article_cate;
        }

        public String getSubject_id() {
            return subject_id;
        }

        public void setSubject_id(String subject_id) {
            this.subject_id = subject_id;
        }

        public String getCases_id() {
            return cases_id;
        }

        public void setCases_id(String cases_id) {
            this.cases_id = cases_id;
        }
    }

    public static class SubjectListEntity {
        private String subject_id;
        private String title;
        private String thumb;

        public String getSubject_id() {
            return subject_id;
        }

        public void setSubject_id(String subject_id) {
            this.subject_id = subject_id;
        }

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
