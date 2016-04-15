package com.appbaba.iz.entity.Favourite;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.appbaba.iz.entity.Base.BaseBean;

import java.util.List;

/**
 * Created by ruby on 2016/4/7.
 */
public class FavouriteBean extends BaseBean{


    /**
     * subject_id : 2
     * title : 我的专题二
     * desc : asdasd
     * thumb : http://192.168.200.123/appbaba2016/Public/Uploads/seller/1/cases/2016-04-08/570758fe2908a.jpg
     * label : ["肉麻","好看"]
     * visits : 0
     * collect : 0
     */

    private List<ListEntity> list;

    public List<ListEntity> getList() {
        return list;
    }

    public void setList(List<ListEntity> list) {
        this.list = list;
    }

    public static class ListEntity extends BaseObservable{
        private String subject_id;
        private String title;
        private String desc;
        private String thumb;
        private String visits;
        private String collect;
        private List<String> label;

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
    }
}
