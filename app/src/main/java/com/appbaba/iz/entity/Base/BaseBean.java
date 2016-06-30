package com.appbaba.iz.entity.Base;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;

import java.io.Serializable;

/**
 * Created by ChanZeeBm on 2015/12/25.
 */
public class BaseBean extends BaseObservable implements Serializable {

    private int errorcode;
    private String msg;
    private int isNext;
    private int page;
    private String total;
    private View.OnClickListener onClickListener;
    private String tag;

    //消息通知键值对
    private String turn_to;
    private String jump_to;
    private String id;
    private int data;

    public String getTurn_to() {
        return turn_to;
    }

    public void setTurn_to(String turn_to) {
        this.turn_to = turn_to;
    }

    public String getJump_to() {
        return jump_to;
    }

    public void setJump_to(String jump_to) {
        this.jump_to = jump_to;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public int getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(int errorcode) {
        this.errorcode = errorcode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getIsNext() {
        return isNext;
    }

    public void setIsNext(int isNext) {
        this.isNext = isNext;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    @Bindable
    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Bindable
    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
