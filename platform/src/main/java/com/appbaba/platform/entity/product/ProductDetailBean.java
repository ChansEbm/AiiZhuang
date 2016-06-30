package com.appbaba.platform.entity.product;

import com.appbaba.platform.AppKeyMap;
import com.appbaba.platform.entity.Base.BaseBean;

/**
 * Created by ruby on 2016/6/2.
 */
public class ProductDetailBean extends BaseBean {

    /**
     * id : 1
     * title : 瓷砖
     * image : /Public/Uploads/goods/2016-05-31/574ceeaea283c.jpg
     * thumb : /Public/Uploads/goods/2016-05-31/thumb_574ceeaea283c.jpg
     * price : 100.00
     * desc : 这是一块持砖
     * content :
     */

    private GoodsEntity goods;

    public GoodsEntity getGoods() {
        return goods;
    }

    public void setGoods(GoodsEntity goods) {
        this.goods = goods;
    }

    public static class GoodsEntity {
        private String id;
        private String title;
        private String image;
        private String thumb;
        private String price;
        private String desc;
        private String content;
        private String buy_link;
        private String pv;

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

        public String getImage() {
            return AppKeyMap.BASEURL+image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getThumb() {
            return AppKeyMap.BASEURL+thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getBuy_link() {
            return buy_link;
        }

        public void setBuy_link(String buy_link) {
            this.buy_link = buy_link;
        }

        public String getPv() {
            return pv;
        }

        public void setPv(String pv) {
            this.pv = pv;
        }
    }
}
