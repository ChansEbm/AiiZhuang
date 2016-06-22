package com.appbaba.platform.entity.product;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.text.TextUtils;

import com.appbaba.platform.AppKeyMap;
import com.appbaba.platform.entity.Base.BaseBean;

import java.util.List;
import java.util.Observable;

/**
 * Created by ruby on 2016/5/31.
 */
public class ProductListBean extends BaseBean {


    /**
     * id : 1
     * title : 瓷砖
     * thumb : /Public/Uploads/goods/2016-05-31/thumb_574ceeaea283c.jpg
     * price : 100.00
     */

    private List<ProductsEntity> products,result;

    public List<ProductsEntity> getProducts() {
        return products;
    }

    public void setProducts(List<ProductsEntity> products) {
        this.products = products;
    }

    public List<ProductsEntity> getResult() {
        return result;
    }

    public void setResult(List<ProductsEntity> result) {
        this.result = result;
    }

    public static class ProductsEntity extends BaseObservable{
        private String id;
        private String title;
        private String image;
        private String price;
        private String buy_link;

        public String getBuy_link() {
            return buy_link;
        }

        public void setBuy_link(String buy_link) {
            this.buy_link = buy_link;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        @Bindable
        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getThumb() {
            if(TextUtils.isEmpty(image)) {
                return image;
            }
            else
            {
                return AppKeyMap.BASEURL + image;
            }
        }

        public void setThumb(String thumb) {
            this.image = thumb;
        }

        @Bindable
        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }
}
