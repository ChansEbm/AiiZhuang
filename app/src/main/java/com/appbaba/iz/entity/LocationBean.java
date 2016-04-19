package com.appbaba.iz.entity;

import java.util.List;

/**
 * Created by ruby on 2016/4/18.
 */
public class LocationBean {


    /**
     * id : 2
     * name : 北京
     * pid : 0
     * child : [{"id":"3411","name":"北京市","pid":"2","child":[[{"id":"500","name":"东城区","pid":"3411"},{"id":"501","name":"西城区","pid":"3411"},{"id":"502","name":"海淀区","pid":"3411"},{"id":"503","name":"朝阳区","pid":"3411"},{"id":"504","name":"崇文区","pid":"3411"},{"id":"505","name":"宣武区","pid":"3411"},{"id":"506","name":"丰台区","pid":"3411"},{"id":"507","name":"石景山区","pid":"3411"},{"id":"508","name":"房山区","pid":"3411"},{"id":"509","name":"门头沟区","pid":"3411"},{"id":"510","name":"通州区","pid":"3411"},{"id":"511","name":"顺义区","pid":"3411"},{"id":"512","name":"昌平区","pid":"3411"},{"id":"513","name":"怀柔区","pid":"3411"},{"id":"514","name":"平谷区","pid":"3411"},{"id":"515","name":"大兴区","pid":"3411"},{"id":"516","name":"密云县","pid":"3411"},{"id":"517","name":"延庆县","pid":"3411"}]]}]
     */

    private String id;
    private String name;
    private String pid;
    /**
     * id : 3411
     * name : 北京市
     * pid : 2
     * child : [[{"id":"500","name":"东城区","pid":"3411"},{"id":"501","name":"西城区","pid":"3411"},{"id":"502","name":"海淀区","pid":"3411"},{"id":"503","name":"朝阳区","pid":"3411"},{"id":"504","name":"崇文区","pid":"3411"},{"id":"505","name":"宣武区","pid":"3411"},{"id":"506","name":"丰台区","pid":"3411"},{"id":"507","name":"石景山区","pid":"3411"},{"id":"508","name":"房山区","pid":"3411"},{"id":"509","name":"门头沟区","pid":"3411"},{"id":"510","name":"通州区","pid":"3411"},{"id":"511","name":"顺义区","pid":"3411"},{"id":"512","name":"昌平区","pid":"3411"},{"id":"513","name":"怀柔区","pid":"3411"},{"id":"514","name":"平谷区","pid":"3411"},{"id":"515","name":"大兴区","pid":"3411"},{"id":"516","name":"密云县","pid":"3411"},{"id":"517","name":"延庆县","pid":"3411"}]]
     */

    private List<LocationBean> child;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public List<LocationBean> getChild() {
        return child;
    }

    public void setChild(List<LocationBean> child) {
        this.child = child;
    }

}
