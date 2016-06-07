package com.appbaba.platform.entity.inspiration;

import android.text.TextUtils;

import com.appbaba.platform.AppKeyMap;
import com.appbaba.platform.entity.Base.BaseBean;

import java.util.List;

/**
 * Created by ruby on 2016/5/31.
 */
public class InspirationDetailBean extends BaseBean {


    /**
     * inspiration_top : {"id":"1","title":"加西亚瓷砖","thumb":"","label":"卫生间,浴室","desc":"加西亚瓷砖","name":"mrcal"}
     * inspiration_bottom : [{"id":"1","title":"瓷砖","desc":"这是一块瓷砖","thumb":"/Public/Uploads/design/2016-05-31/thumb_574d37b1ed3dd.jpg","goods":[{"location_x":"32","location_y":"33","model":"AA-E3l12","goods_id":"1"},{"location_x":"33","location_y":"45","model":"","goods_id":"2"}]},{"id":"2","title":"地板","desc":"这是一块地板","thumb":"/Public/Uploads/design/2016-05-31/thumb_574d4426328fa.png","goods":[]}]
     */

    private InspirationEntity inspiration;

    public InspirationEntity getInspiration() {
        return inspiration;
    }

    public void setInspiration(InspirationEntity inspiration) {
        this.inspiration = inspiration;
    }

    public static class InspirationEntity {
        /**
         * id : 1
         * title : 加西亚瓷砖
         * thumb :
         * label : 卫生间,浴室
         * desc : 加西亚瓷砖
         * name : mrcal
         */

        private InspirationTopEntity inspiration_top;
        /**
         * id : 1
         * title : 瓷砖
         * desc : 这是一块瓷砖
         * thumb : /Public/Uploads/design/2016-05-31/thumb_574d37b1ed3dd.jpg
         * goods : [{"location_x":"32","location_y":"33","model":"AA-E3l12","goods_id":"1"},{"location_x":"33","location_y":"45","model":"","goods_id":"2"}]
         */

        private List<InspirationBottomEntity> inspiration_bottom;

        public InspirationTopEntity getInspiration_top() {
            return inspiration_top;
        }

        public void setInspiration_top(InspirationTopEntity inspiration_top) {
            this.inspiration_top = inspiration_top;
        }

        public List<InspirationBottomEntity> getInspiration_bottom() {
            return inspiration_bottom;
        }

        public void setInspiration_bottom(List<InspirationBottomEntity> inspiration_bottom) {
            this.inspiration_bottom = inspiration_bottom;
        }

        public static class InspirationTopEntity {
            private String id;
            private String title;
            private String thumb;
            private String label;
            private String desc;
            private String name;

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
                return thumb;
            }

            public void setThumb(String thumb) {
                this.thumb = thumb;
            }

            public String getLabel() {
                return label;
            }

            public void setLabel(String label) {
                this.label = label;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class InspirationBottomEntity {
            private String id;
            private String title;
            private String desc;
            private String thumb;
            /**
             * location_x : 32
             * location_y : 33
             * model : AA-E3l12
             * goods_id : 1
             */

            private List<GoodsEntity> goods;

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

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getThumb() {
                if(TextUtils.isEmpty(thumb)) {
                    return thumb;
                }
                else
                {
                    return AppKeyMap.BASEURL + thumb;
                }
            }

            public void setThumb(String thumb) {
                this.thumb = thumb;
            }

            public List<GoodsEntity> getGoods() {
                return goods;
            }

            public void setGoods(List<GoodsEntity> goods) {
                this.goods = goods;
            }

            public static class GoodsEntity {
                private String location_x;
                private String location_y;
                private String model;
                private String goods_id;

                public String getLocation_x() {
                    return location_x;
                }

                public void setLocation_x(String location_x) {
                    this.location_x = location_x;
                }

                public String getLocation_y() {
                    return location_y;
                }

                public void setLocation_y(String location_y) {
                    this.location_y = location_y;
                }

                public String getModel() {
                    return model;
                }

                public void setModel(String model) {
                    this.model = model;
                }

                public String getGoods_id() {
                    return goods_id;
                }

                public void setGoods_id(String goods_id) {
                    this.goods_id = goods_id;
                }
            }
        }
    }
}
