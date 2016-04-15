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
     * subject_id : 1
     * title : 啊实打实大
     * desc : 123123
     * thumb : http://192.168.200.123/appbaba2016/Public/Uploads/seller/1/cases/2016-04-08/570758c07aaca.jpg
     * label : ["清晰","好看"]
     * visits : 0
     * collect : 0
     * detail_list : [{"url":"http://192.168.200.123/appbaba2016/Public/Uploads/seller/1/cases/2016-04-08/570758c506ecd.jpg","desc":"asdasdasdasd"},{"url":"http://192.168.200.123/appbaba2016/Public/Uploads/seller/1/cases/2016-04-08/570758cabff53.png","desc":"123asdasdasd"},{"url":"http://192.168.200.123/appbaba2016/Public/Uploads/seller/1/cases/2016-04-08/57075b6da18be.jpg","desc":"dfsjadfksadljf"}]
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
        private List<String> label;
        private  String lableStr;

        @Bindable
        public String getLableStr() {
            lableStr="";
            for(int i=0;i<label.size();i++)
            {
                lableStr+=(label.get(i)+" ");
            }
            return lableStr;
        }



        /**
         * url : http://192.168.200.123/appbaba2016/Public/Uploads/seller/1/cases/2016-04-08/570758c506ecd.jpg
         * desc : asdasdasdasd
         */

        private List<DetailListEntity> detail_list;

        public String getSubject_id() {
            return subject_id;
        }

        public void setSubject_id(String subject_id) {
            this.subject_id = subject_id;
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
        public String getVisits() {
            return visits;
        }

        public void setVisits(String visits) {
            this.visits = visits;
        }

        @Bindable
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

        public static class DetailListEntity extends  BaseObservable{
            private String url;
            private String desc;

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
        }
    }
}
