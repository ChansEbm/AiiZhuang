package com.appbaba.platform.entity.inspiration;

import android.text.TextUtils;

import com.appbaba.platform.AppKeyMap;
import com.appbaba.platform.entity.Base.BaseBean;

import java.util.List;

/**
 * Created by ruby on 2016/5/31.
 */
public class InspirationListBean extends BaseBean {

    /**
     * id : 2
     * title : 地板
     * label : 厨房
     * thumb :
     * visit : 0
     * love : 0
     */

    private List<InspirationEntity> inspiration,result;

    public List<InspirationEntity> getResult() {
        return result;
    }

    public void setResult(List<InspirationEntity> result) {
        this.result = result;
    }

    public List<InspirationEntity> getInspiration() {
        return inspiration;
    }

    public void setInspiration(List<InspirationEntity> inspiration) {
        this.inspiration = inspiration;
    }

}
