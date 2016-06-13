package com.appbaba.platform.entity.User;

import com.appbaba.platform.entity.Base.BaseBean;

/**
 * Created by ruby on 2016/6/13.
 */
public class DesignerEMBean extends BaseBean {

    /**
     * easemob_username : b95e3894fae4844b8277c2edeabb0096
     * easemob_password : 14e1b600b1fd579f47433b88e8d85291
     */

    private DesignInforEntity design_infor;

    public DesignInforEntity getDesign_infor() {
        return design_infor;
    }

    public void setDesign_infor(DesignInforEntity design_infor) {
        this.design_infor = design_infor;
    }

    public static class DesignInforEntity {
        private String easemob_username;
        private String easemob_password;

        public String getEasemob_username() {
            return easemob_username;
        }

        public void setEasemob_username(String easemob_username) {
            this.easemob_username = easemob_username;
        }

        public String getEasemob_password() {
            return easemob_password;
        }

        public void setEasemob_password(String easemob_password) {
            this.easemob_password = easemob_password;
        }
    }
}
