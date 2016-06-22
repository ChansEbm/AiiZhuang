package com.appbaba.platform.entity.User;

import com.appbaba.platform.AppKeyMap;
import com.appbaba.platform.entity.Base.BaseBean;

import java.util.List;

/**
 * Created by ruby on 2016/6/8.
 */
public class MyInspirationBean extends BaseBean {

    /**
     * id : 1
     * title : 加西亚瓷砖
     * thumb :
     * status : 2
     */

    private List<MyInspirationEntity> my_inspiration;

    public List<MyInspirationEntity> getMy_inspiration() {
        return my_inspiration;
    }

    public void setMy_inspiration(List<MyInspirationEntity> my_inspiration) {
        this.my_inspiration = my_inspiration;
    }

    public static class MyInspirationEntity {
        private String id;
        private String title;
        private String thumb;
        private String status;
        private String status_color;

        public String getStatus_color() {
            return status_color;
        }

        public void setStatus_color(String status_color) {
            this.status_color = status_color;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getThumb() {
            return AppKeyMap.BASEURL+thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
