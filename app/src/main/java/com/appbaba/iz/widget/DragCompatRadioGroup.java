package com.appbaba.iz.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChanZeeBm on 2016/4/8.
 */
public class DragCompatRadioGroup<T> extends RadioGroup {

    private List<List<T>> lists = new ArrayList<>();

    public DragCompatRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DragCompatRadioGroup addSource(List<T> tList) {
        this.lists.add(tList);
        return this;
    }

    public void done() {

    }

}
