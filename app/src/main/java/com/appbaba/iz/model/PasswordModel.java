package com.appbaba.iz.model;

import com.appbaba.iz.eum.NetworkParams;

/**
 * Created by ruby on 2016/4/14.
 */
public class PasswordModel {
    private  String pwd,nPwd,rnPwd;
    private NetworkParams networkParams;

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getnPwd() {
        return nPwd;
    }

    public void setnPwd(String nPwd) {
        this.nPwd = nPwd;
    }

    public String getRnPwd() {
        return rnPwd;
    }

    public void setRnPwd(String rnPwd) {
        this.rnPwd = rnPwd;
    }

    public NetworkParams getNetworkParams() {
        return networkParams;
    }

    public void setNetworkParams(NetworkParams networkParams) {
        this.networkParams = networkParams;
    }
}
