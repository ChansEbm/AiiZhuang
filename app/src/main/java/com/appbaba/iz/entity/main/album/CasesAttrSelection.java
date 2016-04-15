package com.appbaba.iz.entity.main.album;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/4/12.
 */
public class CasesAttrSelection implements Parcelable {

    private String styleId = "0";
    private String spaceId = "0";
    private String cateId = "0";
    private String sizeId = "0";

    public String getSizeId() {
        return sizeId;
    }

    public void setSizeId(String sizeId) {
        this.sizeId = sizeId;
    }

    public String getStyleId() {
        return styleId;
    }

    public void setStyleId(String styleId) {
        this.styleId = styleId;
    }

    public String getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
    }

    public String getCateId() {
        return cateId;
    }

    public void setCateId(String cateId) {
        this.cateId = cateId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.styleId);
        dest.writeString(this.spaceId);
        dest.writeString(this.cateId);
        dest.writeString(this.sizeId);
    }

    public CasesAttrSelection() {
    }

    protected CasesAttrSelection(Parcel in) {
        this.styleId = in.readString();
        this.spaceId = in.readString();
        this.cateId = in.readString();
        this.sizeId = in.readString();
    }

    public static final Parcelable.Creator<CasesAttrSelection> CREATOR = new Parcelable
            .Creator<CasesAttrSelection>() {
        @Override
        public CasesAttrSelection createFromParcel(Parcel source) {
            return new CasesAttrSelection(source);
        }

        @Override
        public CasesAttrSelection[] newArray(int size) {
            return new CasesAttrSelection[size];
        }
    };
}
