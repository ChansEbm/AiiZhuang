package com.appbaba.iz.entity.Friends;

import com.appbaba.iz.entity.Base.BaseBean;

import java.util.List;

/**
 * Created by ruby on 2016/4/8.
 */
public class FriendsBean extends BaseBean {

    /**
     * article_cate_id : 2
     * title : 文章分类一
     * desc : 123123
     * thumb : http://192.168.200.123/appbaba2016/Public/Uploads/seller/1/article/2016-04-13/570da9205125a.png
     * updatetime : 2016-04-13 10:18
     */

    private List<ListEntity> list;

    public List<ListEntity> getList() {
        return list;
    }

    public void setList(List<ListEntity> list) {
        this.list = list;
    }

    public static class ListEntity {
        private String article_cate_id;
        private String title;
        private String desc;
        private String thumb;
        private String updatetime;

        public String getArticle_cate_id() {
            return article_cate_id;
        }

        public void setArticle_cate_id(String article_cate_id) {
            this.article_cate_id = article_cate_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getUpdatetime() {
            return updatetime;
        }

        public void setUpdatetime(String updatetime) {
            this.updatetime = updatetime;
        }
    }
}
