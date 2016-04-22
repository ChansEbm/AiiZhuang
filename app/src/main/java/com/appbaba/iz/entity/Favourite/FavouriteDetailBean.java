package com.appbaba.iz.entity.Favourite;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.appbaba.iz.entity.Base.BaseBean;

import java.util.List;

/**
 * Created by ruby on 2016/4/15.
 */
public class FavouriteDetailBean extends BaseBean {


    /**
     * subject_id : 7
     * title : 鸿威陶瓷走创新路线，造品质瓷砖
     * desc : 这么多年，鸿威陶瓷坚持做高端产品的理念，把每一篇产品打造成一件艺术品。针对配套陶瓷产品生产的特征，公司引入多种专业的设备和生产模式，如专门生产异性砖的压机，多片印花生产线等。鸿威陶瓷更是行业中首批利用6D喷墨打印机生产浮雕腰线和背景墙的企业，产品色彩丰富，美轮美奂。
     * thumb : http://izhuangse.appbaba.com//Public/Uploads/seller/6/cases/2016-04/5711fea57bbbb.jpg
     * label : ["浮雕腰线","瓷砖"]
     * visits : 0
     * collect : 0
     * detail_list : [{"url":"http://izhuangse.appbaba.com//Public/Uploads/seller/6/cases/2016-04/5711fed6f28a9.jpg","desc":"在宁静无忧的家里颐养性情，抛开柴米油盐，几分从容，几分洒脱，其中真味不可言说。","scale":"0.48083333333333"},{"url":"http://izhuangse.appbaba.com//Public/Uploads/seller/6/cases/2016-04/5711feed2d425.jpg","desc":"丰富的色彩纹理，震撼视觉，营造意境深远、意蕴悠长的灵性空间，其内敛、沉稳的厚重感，完美的勾勒出了居家环境的大气典雅及丰富内涵，尽显主人的审美及生活品味。","scale":"0.48083333333333"},{"url":"http://izhuangse.appbaba.com//Public/Uploads/seller/6/cases/2016-04/5711ff24eda21.jpg","desc":"线条层次清晰，色彩丰富多变，有一种凝重浑厚的原生态石质美感，超高的分辨率，图案清晰，纹理多变，精致细腻。","scale":"0.59484536082474"},{"url":"http://izhuangse.appbaba.com//Public/Uploads/seller/6/cases/2016-04/5711ff2d36427.jpg","desc":"经典色块的搭配，营造了高雅的格调，细细品味，别有一番感受。","scale":"0.67581837381204"},{"url":"http://izhuangse.appbaba.com//Public/Uploads/seller/6/cases/2016-04/5711ff36c9a22.jpg","desc":"鸿威浮雕腰线广泛应用于卫生间、浴室、厨房、卧室、走廊、内墙砖的隔条线、玄关等装饰。","scale":"0.65110565110565"},{"url":"http://izhuangse.appbaba.com//Public/Uploads/seller/6/cases/2016-04/5711ff3f8e611.jpg","desc":"数字化5D超高清喷墨技术，专用水晶耐磨釉，使图案生动富有灵气，纹理自然流畅，色泽艳丽清透。","scale":"0.625"},{"url":"http://izhuangse.appbaba.com//Public/Uploads/seller/6/cases/2016-04/5711ff48ee598.jpg","desc":"灵动的砖面曲线给厨卫带来别具一格的意境，吉祥生活每一寸空间。","scale":"0.48083333333333"},{"url":"http://izhuangse.appbaba.com//Public/Uploads/seller/6/cases/2016-04/5711ff53031c2.jpg","desc":"柔刻的纹理勾勒出设计师对美好生活无限遐想。光洁平整的砖面！选择暖色的您，追求高贵，居家是您的天性！","scale":"0.48083333333333"}]
     */

    private InfoEntity info;

    public InfoEntity getInfo() {
        return info;
    }

    public void setInfo(InfoEntity info) {
        this.info = info;
    }

    public static class InfoEntity extends BaseObservable{
        private String subject_id;
        private String title;
        private String desc;
        private String thumb;
        private String visits;
        private String collect;
        private boolean is_collect;
        private List<String> label;
        private String labelStr;

        @Bindable
        public String getLabelStr() {
            labelStr="";
            for(int i=0;i<label.size();i++)
            {
                labelStr+=( label.get(i)+"  ");
            }
            return labelStr;
        }


        public boolean is_collect() {
            return is_collect;
        }

        public void setIs_collect(boolean is_collect) {
            this.is_collect = is_collect;
        }

        /**
         * url : http://izhuangse.appbaba.com//Public/Uploads/seller/6/cases/2016-04/5711fed6f28a9.jpg
         * desc : 在宁静无忧的家里颐养性情，抛开柴米油盐，几分从容，几分洒脱，其中真味不可言说。
         * scale : 0.48083333333333
         */



        private List<DetailListEntity> detail_list;

        public String getSubject_id() {
            return subject_id;
        }

        public void setSubject_id(String subject_id) {
            this.subject_id = subject_id;
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

        public String getVisits() {
            return visits;
        }

        public void setVisits(String visits) {
            this.visits = visits;
        }

        public String getCollect() {
            return collect;
        }

        public void setCollect(String collect) {
            this.collect = collect;
        }

        public List<String> getLabel() {
            return label;
        }

        public void setLabel(List<String> label) {
            this.label = label;
        }

        public List<DetailListEntity> getDetail_list() {
            return detail_list;
        }

        public void setDetail_list(List<DetailListEntity> detail_list) {
            this.detail_list = detail_list;
        }

        public static class DetailListEntity extends BaseObservable{
            private String url;
            private String desc;
            private String scale;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            @Bindable
            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getScale() {
                return scale;
            }

            public void setScale(String scale) {
                this.scale = scale;
            }
        }
    }
}
