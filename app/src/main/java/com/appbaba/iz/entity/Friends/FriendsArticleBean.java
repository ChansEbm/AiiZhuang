package com.appbaba.iz.entity.Friends;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.appbaba.iz.entity.Base.BaseBean;

import java.util.List;

/**
 * Created by ruby on 2016/4/15.
 */
public class FriendsArticleBean extends BaseBean {

    /**
     * article_id : 1
     * title : asdasdasd
     * desc : 123123123123
     * thumb : http://192.168.200.123/appbaba2016/Public/Uploads/seller/1/article/2016-04-13/570db1b75d147.jpg
     * updatetime : 2016-04-13 10:43
     */

    private List<ListEntity> list;

    public List<ListEntity> getList() {
        return list;
    }

    public void setList(List<ListEntity> list) {
        this.list = list;
    }

    public static class ListEntity extends BaseObservable{
        private String article_id;
        private String title;
        private String desc;
        private String thumb;
        private String updatetime;

        public String getArticle_id() {
            return article_id;
        }

        public void setArticle_id(String article_id) {
            this.article_id = article_id;
        }

        @Bindable
        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        @Bindable
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

        @Bindable
        public String getUpdatetime() {
            return updatetime;
        }

        public void setUpdatetime(String updatetime) {
            this.updatetime = updatetime;
        }
    }
}
