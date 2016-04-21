package com.appbaba.iz.entity;

import java.util.List;

/**
 * Created by ruby on 2016/4/18.
 */
public class LocationBean {


    /**
     * id : 2
     * name : 北京
     * pid : 0
     * city : [{"id":"3411","name":"北京市","pid":"2","zone":[[{"id":"500","name":"东城区","pid":"3411"},{"id":"501","name":"西城区","pid":"3411"},{"id":"502","name":"海淀区","pid":"3411"},{"id":"503","name":"朝阳区","pid":"3411"},{"id":"504","name":"崇文区","pid":"3411"},{"id":"505","name":"宣武区","pid":"3411"},{"id":"506","name":"丰台区","pid":"3411"},{"id":"507","name":"石景山区","pid":"3411"},{"id":"508","name":"房山区","pid":"3411"},{"id":"509","name":"门头沟区","pid":"3411"},{"id":"510","name":"通州区","pid":"3411"},{"id":"511","name":"顺义区","pid":"3411"},{"id":"512","name":"昌平区","pid":"3411"},{"id":"513","name":"怀柔区","pid":"3411"},{"id":"514","name":"平谷区","pid":"3411"},{"id":"515","name":"大兴区","pid":"3411"},{"id":"516","name":"密云县","pid":"3411"},{"id":"517","name":"延庆县","pid":"3411"}]]}]
     */

    private List<DataEntity> data;

    public List<DataEntity> getData() {
        return data;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public static class DataEntity {
        private String id;
        private String name;
        private String pid;
        /**
         * id : 3411
         * name : 北京市
         * pid : 2
         * zone : [[{"id":"500","name":"东城区","pid":"3411"},{"id":"501","name":"西城区","pid":"3411"},{"id":"502","name":"海淀区","pid":"3411"},{"id":"503","name":"朝阳区","pid":"3411"},{"id":"504","name":"崇文区","pid":"3411"},{"id":"505","name":"宣武区","pid":"3411"},{"id":"506","name":"丰台区","pid":"3411"},{"id":"507","name":"石景山区","pid":"3411"},{"id":"508","name":"房山区","pid":"3411"},{"id":"509","name":"门头沟区","pid":"3411"},{"id":"510","name":"通州区","pid":"3411"},{"id":"511","name":"顺义区","pid":"3411"},{"id":"512","name":"昌平区","pid":"3411"},{"id":"513","name":"怀柔区","pid":"3411"},{"id":"514","name":"平谷区","pid":"3411"},{"id":"515","name":"大兴区","pid":"3411"},{"id":"516","name":"密云县","pid":"3411"},{"id":"517","name":"延庆县","pid":"3411"}]]
         */

        private List<CityEntity> city;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public List<CityEntity> getCity() {
            return city;
        }

        public void setCity(List<CityEntity> city) {
            this.city = city;
        }

        public static class CityEntity {
            private String id;
            private String name;
            private String pid;
            /**
             * id : 500
             * name : 东城区
             * pid : 3411
             */

            private List<ZoneEntity> zone;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPid() {
                return pid;
            }

            public void setPid(String pid) {
                this.pid = pid;
            }

            public List<ZoneEntity> getZone() {
                return zone;
            }

            public void setZone(List<ZoneEntity> zone) {
                this.zone = zone;
            }

            public static class ZoneEntity {
                private String id;
                private String name;
                private String pid;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getPid() {
                    return pid;
                }

                public void setPid(String pid) {
                    this.pid = pid;
                }
            }
        }
    }
}
