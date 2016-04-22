package com.appbaba.iz.model;

import com.appbaba.iz.eum.NetworkParams;

/**
 * Created by ruby on 2016/4/22.
 */
public class BrandsModel {
    private  String name,contract,mobile,address;
    private NetworkParams networkParams;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public NetworkParams getNetworkParams() {
        return networkParams;
    }

    public void setNetworkParams(NetworkParams networkParams) {
        this.networkParams = networkParams;
    }
}
