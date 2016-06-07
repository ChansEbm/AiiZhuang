package com.appbaba.platform.entity.User;

import com.appbaba.platform.entity.Base.BaseBean;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ruby on 2016/6/3.
 */
public class UserConcernListBean extends BaseBean {


    /**
     * title : 地砖
     * thumb : /Public/Uploads/goods/2016-05-31/thumb_574d41fe201c1.jpg
     */

    private List<BaseItemBean> concern_list;

    public List<BaseItemBean> getConcern_list() {
        return concern_list;
    }

    public void setConcern_list(List<BaseItemBean> concern_list) {
        this.concern_list = concern_list;
    }


}
