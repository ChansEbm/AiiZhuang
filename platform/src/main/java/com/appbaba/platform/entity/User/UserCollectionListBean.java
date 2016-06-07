package com.appbaba.platform.entity.User;

import com.appbaba.platform.entity.Base.BaseBean;

import java.util.List;

/**
 * Created by ruby on 2016/6/3.
 */
public class UserCollectionListBean extends BaseBean {

    /**
     * title : 地砖
     * thumb : /Public/Uploads/goods/2016-05-31/thumb_574d41fe201c1.jpg
     */

    private List<BaseItemBean> favorite_list;

    public List<BaseItemBean> getFavorite_list() {
        return favorite_list;
    }

    public void setFavorite_list(List<BaseItemBean> favorite_list) {
        this.favorite_list = favorite_list;
    }


}
