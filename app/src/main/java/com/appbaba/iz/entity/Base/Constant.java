package com.appbaba.iz.entity.Base;

import android.databinding.BindingAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.appbaba.iz.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Administrator on 2016/4/14.
 */
public class Constant {

    @BindingAdapter(value = {"app:caseAttrTextColor"})
    public static void
    caseAttrTextColor(TextView textView, boolean isCheck) {
        if (isCheck) {
            textView.setTextColor(textView.getResources().getColor(R.color.color_orange_light));
        } else {
            textView.setTextColor(textView.getResources().getColor(R.color.color_text_dark));
        }
    }

    @BindingAdapter(value = {"app:loadImage"})
    public static void loadImage(ImageView imageView, String url) {
        if (imageView == null)
            return;
        Picasso.with(imageView.getContext()).load(url).resize(300, 300).into(imageView);
    }

}
