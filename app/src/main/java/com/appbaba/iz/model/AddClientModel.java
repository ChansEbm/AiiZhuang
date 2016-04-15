package com.appbaba.iz.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;

import com.appbaba.iz.eum.NetworkParams;

/**
 * Created by ruby on 2016/4/15.
 */
public class AddClientModel extends BaseObservable implements Parcelable{
    private  String name,phone,area_ids,address,id;

    private NetworkParams networkParams;

    public  AddClientModel()
    {}
    public AddClientModel(Parcel parcel)
    {
        name = parcel.readString();
        phone = parcel.readString();
        area_ids = parcel.readString();
        address = parcel.readString();
        id = parcel.readString();
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Bindable
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Bindable
    public String getArea_ids() {
        return area_ids;
    }

    public void setArea_ids(String area_ids) {
        this.area_ids = area_ids;
    }

    @Bindable
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
         dest.writeString(name);
        dest.writeString(phone);
        dest.writeString(area_ids);
        dest.writeString(address);
        dest.writeString(id);
    }

    public static final Parcelable.Creator<AddClientModel> CREATOR = new Creator<AddClientModel>() {

        @Override
        public AddClientModel[] newArray(int size) {
            // TODO Auto-generated method stub
            return new AddClientModel[size];
        }

        @Override
        public AddClientModel createFromParcel(Parcel source) {
            // TODO Auto-generated method stub
            return new AddClientModel(source);
        }
    };
}
