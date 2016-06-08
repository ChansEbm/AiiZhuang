package com.appbaba.platform.entity.User;

import com.appbaba.platform.AppKeyMap;
import com.appbaba.platform.entity.Base.BaseBean;

import java.util.List;

/**
 * Created by ruby on 2016/6/8.
 */
public class MyProductBean extends BaseBean {

    /**
     * id : 1
     * title : 瓷砖
     * thumb : /Public/Uploads/goods/2016-05-31/thumb_574ceeaea283c.jpg
     */

    private List<MyGoodsEntity> my_goods;

    public List<MyGoodsEntity> getMy_goods() {
        return my_goods;
    }

    public void setMy_goods(List<MyGoodsEntity> my_goods) {
        this.my_goods = my_goods;
    }

    public static class MyGoodsEntity {
        private String id;
        private String title;
        private String thumb;

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
    }
}
