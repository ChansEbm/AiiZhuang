package com.appbaba.platform.entity.inspiration;

import android.text.TextUtils;

import com.appbaba.platform.AppKeyMap;
import com.appbaba.platform.entity.Base.BaseBean;

import java.util.List;

/**
 * Created by ruby on 2016/5/31.
 */
public class InspirationListBean extends BaseBean {

    /**
     * id : 2
     * title : 地板
     * label : 厨房
     * thumb :
     * visit : 0
     * love : 0
     */

    private List<InspirationEntity> inspiration;

    public List<InspirationEntity> getInspiration() {
        return inspiration;
    }

    public void setInspiration(List<InspirationEntity> inspiration) {
        this.inspiration = inspiration;
    }

    public static class InspirationEntity {
        private String id;
        private String title;
        private String label;
        private String thumb;
        private String visit;
        private String love;

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

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
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

        public String getVisit() {
            return visit;
        }

        public void setVisit(String visit) {
            this.visit = visit;
        }

        public String getLove() {
            return love;
        }

        public void setLove(String love) {
            this.love = love;
        }
    }
}
