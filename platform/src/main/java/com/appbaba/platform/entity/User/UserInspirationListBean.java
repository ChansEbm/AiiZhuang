package com.appbaba.platform.entity.User;

import com.appbaba.platform.entity.Base.BaseBean;

import java.util.List;

/**
 * Created by ruby on 2016/6/3.
 */
public class UserInspirationListBean extends BaseBean {

    /**
     * title : 地板
     * thumb :
     * status : 0
     */

    private List<BaseItemBean> inspiration_list;

    public List<BaseItemBean> getInspiration_list() {
        return inspiration_list;
    }

    public void setInspiration_list(List<BaseItemBean> inspiration_list) {
        this.inspiration_list = inspiration_list;
    }


}
