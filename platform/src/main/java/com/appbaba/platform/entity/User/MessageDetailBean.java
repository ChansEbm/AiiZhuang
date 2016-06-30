package com.appbaba.platform.entity.User;

import com.appbaba.platform.entity.Base.BaseBean;

/**
 * Created by ruby on 2016/6/24.
 */
public class MessageDetailBean extends BaseBean{

    /**
     * title : 21321
     * content : 12321
     * addtime : 2016-06-21 15:44
     */

    private DetailEntity detail;

    public DetailEntity getDetail() {
        return detail;
    }

    public void setDetail(DetailEntity detail) {
        this.detail = detail;
    }

    public static class DetailEntity {
        private String title;
        private String content;
        private String addtime;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }
    }
}
