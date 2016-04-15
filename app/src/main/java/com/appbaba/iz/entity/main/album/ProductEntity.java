package com.appbaba.iz.entity.main.album;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.appbaba.iz.entity.Base.BaseBean;

import java.util.List;

/**
 * Created by Administrator on 2016/4/15.
 */
public class ProductEntity extends BaseBean {

    /**
     * list : [{"product_id":"1","title":"西班牙·黑白根 3CM803035F","model":"3CM803035F",
     * "thumb":"http://192.168.200.123/appbaba2016/Public/Uploads/seller/1/cases/2016-04-14
     * /thumb_570f03a5f3a21.jpg","image":"http://192.168.200
     * .123/appbaba2016/Public/Uploads/seller/1/cases/2016-04-14/570f03a5f3a21.jpg",
     * "image_list":["http://192.168.200
     * .123/appbaba2016/Public/Uploads/seller/1/cases/2016-04-14/570f03a5f3a21.jpg","http://192
     * .168.200.123/appbaba2016/Public/Uploads/seller/1/cases/2016-04-15/57105f7f5f61d.jpg",
     * "http://192.168.200.123/appbaba2016/Public/Uploads/seller/1/cases/2016-04-15/57105f811a5f4
     * .jpg"],"is_collect":"0","cate_name":"金刚釉\u2022魔石","width":"600","height":"600",
     * "cases_list":[{"cases_id":"1","title":"西班牙·黑白根 3CM803035F","desc":"","thumb":"http://192
     * .168.200.123/appbaba2016/Public/Uploads/seller/1/cases/2016-04-14/thumb_570f0319dc604
     * .png","image":"http://192.168.200
     * .123/appbaba2016/Public/Uploads/seller/1/cases/2016-04-14/570f0319dc604.png"},
     * {"cases_id":"2","title":"意大利·布朗灰 1CM803033F","desc":"","thumb":"http://192.168.200
     * .123/appbaba2016/Public/Uploads/seller/1/cases/2016-04-14/thumb_570f0346e1605.png",
     * "image":"http://192.168.200.123/appbaba2016/Public/Uploads/seller/1/cases/2016-04-14
     * /570f0346e1605.png"}]}]
     * has_next : 0
     * cond : {"page":1,"page_size":10,"product_id":1,"style_id":0,"size_id":0,"cate_id":0,
     * "keyword":""}
     */

    private int has_next;
    /**
     * page : 1
     * page_size : 10
     * product_id : 1
     * style_id : 0
     * size_id : 0
     * cate_id : 0
     * keyword :
     */

    private CondBean cond;
    /**
     * product_id : 1
     * title : 西班牙·黑白根 3CM803035F
     * model : 3CM803035F
     * thumb : http://192.168.200.123/appbaba2016/Public/Uploads/seller/1/cases/2016-04-14
     * /thumb_570f03a5f3a21.jpg
     * image : http://192.168.200.123/appbaba2016/Public/Uploads/seller/1/cases/2016-04-14
     * /570f03a5f3a21.jpg
     * image_list : ["http://192.168.200
     * .123/appbaba2016/Public/Uploads/seller/1/cases/2016-04-14/570f03a5f3a21.jpg","http://192
     * .168.200.123/appbaba2016/Public/Uploads/seller/1/cases/2016-04-15/57105f7f5f61d.jpg",
     * "http://192.168.200.123/appbaba2016/Public/Uploads/seller/1/cases/2016-04-15/57105f811a5f4
     * .jpg"]
     * is_collect : 0
     * cate_name : 金刚釉•魔石
     * width : 600
     * height : 600
     * cases_list : [{"cases_id":"1","title":"西班牙·黑白根 3CM803035F","desc":"","thumb":"http://192
     * .168.200.123/appbaba2016/Public/Uploads/seller/1/cases/2016-04-14/thumb_570f0319dc604
     * .png","image":"http://192.168.200
     * .123/appbaba2016/Public/Uploads/seller/1/cases/2016-04-14/570f0319dc604.png"},
     * {"cases_id":"2","title":"意大利·布朗灰 1CM803033F","desc":"","thumb":"http://192.168.200
     * .123/appbaba2016/Public/Uploads/seller/1/cases/2016-04-14/thumb_570f0346e1605.png",
     * "image":"http://192.168.200.123/appbaba2016/Public/Uploads/seller/1/cases/2016-04-14
     * /570f0346e1605.png"}]
     */

    private List<ListBean> list;

    public int getHas_next() {
        return has_next;
    }

    public void setHas_next(int has_next) {
        this.has_next = has_next;
    }

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

    public static class CondBean {
        private int page;
        private int page_size;
        private int product_id;
        private int style_id;
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

        public int getSize_id() {
            return size_id;
        }

        public void setSize_id(int size_id) {
            this.size_id = size_id;
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
    }

    public static class ListBean extends BaseObservable {
        private String product_id;
        private String title;
        private String model;
        private String thumb;
        private String image;
        private String is_collect;
        private String cate_name;

        @Bindable
        public String getCateNameDesc() {
            this.cateNameDesc = "所属类别 : " + getCate_name();
            return cateNameDesc;
        }

        @Bindable
        public String getModelDesc() {
            this.modelDesc = "产品名称 : " + getModel();
            return modelDesc;
        }

        @Bindable
        public String getSizeDesc() {
            this.sizeDesc = "产品尺寸 : " + getWidth() + " X " + getHeight() + "mm";
            return sizeDesc;
        }


        private String cateNameDesc;
        private String modelDesc;
        private String sizeDesc;
        private String width;
        private String height;
        private List<String> image_list;
        /**
         * cases_id : 1
         * title : 西班牙·黑白根 3CM803035F
         * desc :
         * thumb : http://192.168.200.123/appbaba2016/Public/Uploads/seller/1/cases/2016-04-14
         * /thumb_570f0319dc604.png
         * image : http://192.168.200.123/appbaba2016/Public/Uploads/seller/1/cases/2016-04-14
         * /570f0319dc604.png
         */

        private List<CasesListBean> cases_list;

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

        public String getIs_collect() {
            return is_collect;
        }

        public void setIs_collect(String is_collect) {
            this.is_collect = is_collect;
        }

        @Bindable
        public String getCate_name() {
            return cate_name;
        }

        public void setCate_name(String cate_name) {
            this.cate_name = cate_name;
        }

        @Bindable
        public String getWidth() {
            return width;
        }

        public void setWidth(String width) {
            this.width = width;
        }

        @Bindable
        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public List<String> getImage_list() {
            return image_list;
        }

        public void setImage_list(List<String> image_list) {
            this.image_list = image_list;
        }

        public List<CasesListBean> getCases_list() {
            return cases_list;
        }

        public void setCases_list(List<CasesListBean> cases_list) {
            this.cases_list = cases_list;
        }

        public static class CasesListBean extends BaseObservable {
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

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }
        }
    }
}
