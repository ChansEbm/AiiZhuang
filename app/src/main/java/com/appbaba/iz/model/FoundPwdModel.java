package com.appbaba.iz.model;

import com.appbaba.iz.eum.NetworkParams;

/**
 * Created by ruby on 2016/4/18.
 */
public class FoundPwdModel {
    private  String phone,code,password,re_password;
    private NetworkParams networkParams;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRe_password() {
        return re_password;
    }

    public void setRe_password(String re_password) {
        this.re_password = re_password;
    }

    public NetworkParams getNetworkParams() {
        return networkParams;
    }

    public void setNetworkParams(NetworkParams networkParams) {
        this.networkParams = networkParams;
    }
}
