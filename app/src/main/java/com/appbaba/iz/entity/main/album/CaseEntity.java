package com.appbaba.iz.entity.main.album;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.appbaba.iz.entity.Base.BaseBean;

import java.util.List;

/**
 * Created by Administrator on 2016/4/14.
 */
public class CaseEntity extends BaseBean {

    /**
     * page : 0
     * page_size : 0
     * product_id : 1
     * style_id : 0
     * space_id : 0
     * cate_id : 0
     * keyword :
     */

    private CondBean cond;
    /**
     * cases_id : 1
     * title : 你好1111
     * desc :
     * thumb : http://192.168.200.123/appbaba2016/Public/Uploads/seller/1/cases/2016-04-08
     * /thumb_5707153db292d.jpg
     * image : http://192.168.200.123/appbaba2016/Public/Uploads/seller/1/cases/2016-04-08
     * /5707153db292d.jpg
     * is_collect : 0
     * product_list : [{"product_id":"1","title":"产品一","model":"21323","thumb":"http://192.168
     * .200.123/appbaba2016/Public/Uploads/seller/1/cases/2016-04-12/thumb_570c8c0fb78e0.jpg",
     * "image":"http://192.168.200.123/appbaba2016/Public/Uploads/seller/1/cases/2016-04-12
     * /570c8c0fb78e0.jpg"},{"product_id":"4","title":"产品一","model":"asdasd","thumb":"http://192
     * .168.200.123/appbaba2016/Public/Uploads/seller/1/cases/2016-04-07/thumb_5705f6423a89a
     * .jpg","image":"http://192.168.200
     * .123/appbaba2016/Public/Uploads/seller/1/cases/2016-04-07/5705f6423a89a.jpg"},
     * {"product_id":"5","title":"产品一","model":"asdasd","thumb":"http://192.168.200
     * .123/appbaba2016/Public/Uploads/seller/1/cases/2016-04-07/thumb_5705f6423a89a.jpg",
     * "image":"http://192.168.200.123/appbaba2016/Public/Uploads/seller/1/cases/2016-04-07
     * /5705f6423a89a.jpg"}]
     */

    private List<ListBean> list;
    /**
     * has_next : 0
     */

    private int has_next;

    public CondBean getCond() {
        return cond;
    }

    public void setCond(CondBean cond) {
        this.cond = cond;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public int getHas_next() {
        return has_next;
    }

    public void setHas_next(int has_next) {
        this.has_next = has_next;
    }

    public static class CondBean {
        private int page;
        private int page_size;
        private int product_id;
        private int style_id;
        private int space_id;
        private int size_id;
        private int cate_id;
        private String keyword;

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getPage_size() {
            return page_size;
        }

        public void setPage_size(int page_size) {
            this.page_size = page_size;
        }

        public int getProduct_id() {
            return product_id;
        }

        public void setProduct_id(int product_id) {
            this.product_id = product_id;
        }

        public int getStyle_id() {
            return style_id;
        }

        public void setStyle_id(int style_id) {
            this.style_id = style_id;
        }

        public int getSpace_id() {
            return space_id;
        }

        public void setSpace_id(int space_id) {
            this.space_id = space_id;
        }

        public int getCate_id() {
            return cate_id;
        }

        public void setCate_id(int cate_id) {
            this.cate_id = cate_id;
        }

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        public int getSize_id() {
            return size_id;
        }

        public void setSize_id(int size_id) {
            this.size_id = size_id;
        }
    }

    public static class ListBean extends BaseObservable {
        private String cases_id;
        private String title;
        private String desc;
        private String thumb;
        private String image;
        private String is_collect;

        /**
         * product_id : 1
         * title : 产品一
         * model : 21323
         * thumb : http://192.168.200.123/appbaba2016/Public/Uploads/seller/1/cases/2016-04-12
         * /thumb_570c8c0fb78e0.jpg
         * image : http://192.168.200.123/appbaba2016/Public/Uploads/seller/1/cases/2016-04-12
         * /570c8c0fb78e0.jpg
         */

        private List<ProductListBean> product_list;

        public String getCases_id() {
            return cases_id;
        }

        public void setCases_id(String cases_id) {
            this.cases_id = cases_id;
        }

        @Bindable
        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        @Bindable
        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        @Bindable
        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        @Bindable
        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        @Bindable
        public String getIs_collect() {
            return is_collect;
        }

        public void setIs_collect(String is_collect) {
            this.is_collect = is_collect;
        }

        @Bindable
        public List<ProductListBean> getProduct_list() {
            return product_list;
        }

        public void setProduct_list(List<ProductListBean> product_list) {
            this.product_list = product_list;
        }

        public static class ProductListBean extends BaseObservable {
            private String product_id;
            private String title;
            private String model;
            private String thumb;
            private String image;
            private int location_x;
            private int location_y;

            public String getProduct_id() {
                return product_id;
            }

            public void setProduct_id(String product_id) {
                this.product_id = product_id;
            }

            @Bindable
            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            @Bindable
            public String getModel() {
                return model;
            }

            public void setModel(String model) {
                this.model = model;
            }

            @Bindable
            public String getThumb() {
                return thumb;
            }

            public void setThumb(String thumb) {
                this.thumb = thumb;
            }

            @Bindable
            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }
            public int getLocation_x() {
                return location_x;
            }

            public void setLocation_x(int location_x) {
                this.location_x = location_x;
            }

            public int getLocation_y() {
                return location_y;
            }

            public void setLocation_y(int location_y) {
                this.location_y = location_y;
            }
        }
    }
}
