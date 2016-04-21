package com.appbaba.iz.entity.Friends;

import com.appbaba.iz.entity.Base.BaseBean;

import java.util.List;

/**
 * Created by ruby on 2016/4/20.
 */
public class FriendsClientCollectionBean extends BaseBean {

    /**
     * cases_id : 24
     * title : 意大利·雅士白 1CM803034F
     * desc :
     * thumb : http://izhuangse.appbaba.com//Public/Uploads/seller/1/cases/2016-04/thumb_5713b2f2736b8.png
     * image : http://izhuangse.appbaba.com//Public/Uploads/seller/1/cases/2016-04/5713b2f2736b8.png
     */

    private List<CasesListEntity> cases_list;
    /**
     * product_id : 1
     * title : 西班牙·黑白根 3CM803035F
     * model : 3CM803035F
     * thumb : http://izhuangse.appbaba.com//Public/Uploads/seller/1/cases/2016-04-14/thumb_570f03a5f3a21.jpg
     * image : http://izhuangse.appbaba.com//Public/Uploads/seller/1/cases/2016-04-14/570f03a5f3a21.jpg
     */

    private List<ProductListEntity> product_list;

    public List<CasesListEntity> getCases_list() {
        return cases_list;
    }

    public void setCases_list(List<CasesListEntity> cases_list) {
        this.cases_list = cases_list;
    }

    public List<ProductListEntity> getProduct_list() {
        return product_list;
    }

    public void setProduct_list(List<ProductListEntity> product_list) {
        this.product_list = product_list;
    }

    public static class CasesListEntity {
        private String cases_id;
        private String title;
        private String desc;
        private String thumb;
        private String image;

        public String getCases_id() {
            return cases_id;
        }

        public void setCases_id(String cases_id) {
            this.cases_id = cases_id;
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

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }

    public static class ProductListEntity {
        private String product_id;
        private String title;
        private String model;
        private String thumb;
        private String image;

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
