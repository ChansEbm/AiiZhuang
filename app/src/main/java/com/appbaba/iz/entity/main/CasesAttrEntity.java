package com.appbaba.iz.entity.main;

import android.databinding.Bindable;

import com.appbaba.iz.entity.Base.BaseBean;

import java.util.List;

/**
 * Created by Administrator on 2016/4/12.
 */
public class CasesAttrEntity extends BaseBean {

    /**
     * style_id : 1
     * title : 简约现代
     * flag : 1
     */

    private List<StyleListBean> style_list;

    /**
     * space_id : 1
     * title : 卫生间
     * flag : 2
     */

    private List<SpaceListBean> space_list;

    /**
     * cate_id : 17
     * title : 我的分类一
     * flag : 3
     */

    private List<CateListBean> cate_list;
    /**
     * size_id : 1
     * title : 600*600
     * flag : 4
     */

    private List<SizeListBean> size_list;


    public List<StyleListBean> getStyle_list() {
        return style_list;
    }

    public void setStyle_list(List<StyleListBean> style_list) {
        this.style_list = style_list;
    }

    public List<SpaceListBean> getSpace_list() {
        return space_list;
    }

    public void setSpace_list(List<SpaceListBean> space_list) {
        this.space_list = space_list;
    }

    public List<CateListBean> getCate_list() {
        return cate_list;
    }

    public void setCate_list(List<CateListBean> cate_list) {
        this.cate_list = cate_list;
    }

    public List<SizeListBean> getSize_list() {
        return size_list;
    }

    public void setSize_list(List<SizeListBean> size_list) {
        this.size_list = size_list;
    }

    public static class StyleListBean extends AttrParent {

    }

    public static class SpaceListBean extends AttrParent {

    }

    public static class CateListBean extends AttrParent {

    }

    public static class SizeListBean extends AttrParent {

    }

    public static class AttrParent extends BaseBean {
        private boolean isCheck = false;
        private String title;
        private String flag;
        private String cate_id;
        private String space_id;
        private String style_id;
        private String size_id;

        public String getSize_id() {
            return size_id;
        }

        public void setSize_id(String size_id) {
            this.size_id = size_id;
        }

        public String getStyle_id() {
            return style_id;
        }

        public void setStyle_id(String style_id) {
            this.style_id = style_id;
        }

        public String getSpace_id() {
            return space_id;
        }

        public void setSpace_id(String space_id) {
            this.space_id = space_id;
        }

        public String getCate_id() {
            return cate_id;
        }

        public void setCate_id(String cate_id) {
            this.cate_id = cate_id;
        }

        @Bindable
        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }

        @Bindable
        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }
    }

}
