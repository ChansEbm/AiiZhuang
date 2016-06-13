package com.appbaba.platform.entity.inspiration;

import com.appbaba.platform.entity.Base.BaseBean;
import com.appbaba.platform.entity.User.MyInspirationBean;

import java.util.List;

/**
 * Created by ruby on 2016/6/8.
 */
public class AllInspirationBean extends BaseBean {

    /**
     * id : 25
     * thumb :
     * title : qweqwe
     * label : sdfsdfsdfsdfsdsdfdssdfsd
     * visit : 0
     * love : 0
     */

    private List<InspirationEntity> my_inspiration;

    public List<InspirationEntity> getMy_inspiration() {
        return my_inspiration;
    }

    public void setMy_inspiration(List<InspirationEntity> my_inspiration) {
        this.my_inspiration = my_inspiration;
    }


}
