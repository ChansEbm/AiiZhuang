package com.appbaba.platform.entity.comm;

import com.appbaba.platform.entity.Base.BaseBean;

import java.util.List;

/**
 * Created by ruby on 2016/6/14.
 */
public class BaseHotWordsBean extends BaseBean {


    /**
     * id : 1
     * title : 简约现代
     */

    private List<SearchListBean.Item> style_list;
    /**
     * id : 1
     * title : 卫生间
     */

    private List<SearchListBean.Item> space_list;

    private List<SearchListBean.Item> sort_list;
    private List<String> keywords_list;

    public List<SearchListBean.Item> getStyle_list() {
        return style_list;
    }

    public void setStyle_list(List<SearchListBean.Item> style_list) {
        this.style_list = style_list;
    }

    public List<SearchListBean.Item> getSpace_list() {
        return space_list;
    }

    public void setSpace_list(List<SearchListBean.Item> space_list) {
        this.space_list = space_list;
    }

    public List<String> getKeywords_list() {
        return keywords_list;
    }

    public void setKeywords_list(List<String> keywords_list) {
        this.keywords_list = keywords_list;
    }


    public List<SearchListBean.Item> getSort_list() {
        return sort_list;
    }

    public void setSort_list(List<SearchListBean.Item> sort_list) {
        this.sort_list = sort_list;
    }
}
