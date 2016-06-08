package com.appbaba.platform.entity.User;

import com.appbaba.platform.AppKeyMap;
import com.appbaba.platform.entity.Base.BaseBean;
import com.appbaba.platform.entity.inspiration.InspirationDetailBean;
import com.appbaba.platform.entity.inspiration.InspirationEntity;

import java.util.List;

/**
 * Created by ruby on 2016/6/8.
 */
public class DesignerDetailBean extends BaseBean {

    /**
     * name :
     * introduce :
     * background : /Public/images/background.png
     * inspiration_count : 2
     */

    private InforEntity infor;
    /**
     * id : 25
     * title : qweqwe
     * label : sdfsdfsdfsdfsdsdfdssdfsd
     * thumb :
     * visit : 0
     * love : 0
     */

    private List<InspirationEntity> inspiration_list;

    public InforEntity getInfor() {
        return infor;
    }

    public void setInfor(InforEntity infor) {
        this.infor = infor;
    }

    public List<InspirationEntity> getInspiration_list() {
        return inspiration_list;
    }

    public void setInspiration_list(List<InspirationEntity> inspiration_list) {
        this.inspiration_list = inspiration_list;
    }

    public static class InforEntity {
        private String name;
        private String introduce;
        private String background;
        private String inspiration_count;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public String getBackground() {
            return AppKeyMap.BASEURL+ background;
        }

        public void setBackground(String background) {
            this.background = background;
        }

        public String getInspiration_count() {
            return inspiration_count;
        }

        public void setInspiration_count(String inspiration_count) {
            this.inspiration_count = inspiration_count;
        }
    }

}
