package com.appbaba.platform.widget.PopupWindow;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.appbaba.platform.R;
import com.appbaba.platform.databinding.PopupTakePhotoBinding;
import com.appbaba.platform.eum.PhotoPopupOpts;
import com.appbaba.platform.impl.OnPhotoOptsSelectListener;

/**
 * Created by ChanZeeBm on 2015/11/18.
 */
public class TakePhotoPopupWindow extends BasePopupWindow {
    private PopupTakePhotoBinding popupTakePhotoBinding;
    private OnPhotoOptsSelectListener onPhotoOptsSelectListener;

    public TakePhotoPopupWindow(@NonNull Context context) {
        super(context);
        popupTakePhotoBinding = (PopupTakePhotoBinding) viewDataBinding;
        popupTakePhotoBinding.btnCancel.setOnClickListener(this);
        popupTakePhotoBinding.btnSelectFromAlbum.setOnClickListener(this);
        popupTakePhotoBinding.btnTakePhoto.setOnClickListener(this);
    }

    @Override
    public int getPopupLayout() {
        return R.layout.popup_take_photo;
    }

    public void setOnPhotoOptsSelectListener(OnPhotoOptsSelectListener onPhotoOptsSelectListener) {
        this.onPhotoOptsSelectListener = onPhotoOptsSelectListener;
    }

    @Override
    public void onClick(View v) {
        if (onPhotoOptsSelectListener != null) {
            switch (v.getId()) {
                case R.id.btn_select_from_album:
                    onPhotoOptsSelectListener.onOptsSelect(PhotoPopupOpts.ALBUM);
                    break;
                case R.id.btn_take_photo:
                    onPhotoOptsSelectListener.onOptsSelect(PhotoPopupOpts.TAKE_PHOTO);
                    break;
            }
        }
        dismiss();
    }


}
