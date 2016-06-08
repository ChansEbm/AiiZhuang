package com.appbaba.platform.ui.activity.user;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.appbaba.platform.R;
import com.appbaba.platform.base.BaseActivity;
import com.appbaba.platform.databinding.ActivityBeDesignerBinding;
import com.appbaba.platform.entity.Base.BaseBean;
import com.appbaba.platform.eum.NetworkParams;
import com.appbaba.platform.eum.PhotoPopupOpts;
import com.appbaba.platform.impl.OnPhotoOptsSelectListener;
import com.appbaba.platform.method.MethodConfig;
import com.appbaba.platform.ui.activity.comm.CommLocationActivity;
import com.appbaba.platform.widget.PopupWindow.TakePhotoPopupWindow;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * Created by ruby on 2016/6/6.
 */
public class UserBeDesignerActivity extends BaseActivity {

    private ActivityBeDesignerBinding binding;

    private String correct,opposite;
    private int which = 0;

    @Override
    protected void InitView() {
        binding = (ActivityBeDesignerBinding)viewDataBinding;
        binding.includeTopTitle.title.setText("成为设计师");
        binding.includeTopTitle.toolBar.setNavigationIcon(R.mipmap.icon_back);
        binding.includeTopTitle.title.setTextColor(Color.BLACK);
    }

    @Override
    protected void InitData() {

    }

    @Override
    protected void InitEvent() {
       binding.includeTopTitle.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               onBackPressed();
           }
       });
    }

    @Override
    protected void InitListening() {
       binding.tvCitySelect.setOnClickListener(this);
        binding.linearPhoto.setOnClickListener(this);
        binding.tvUpload.setOnClickListener(this);
    }

    @Override
    protected void OnClick(int id, View view) {

        switch (id)
        {
            case R.id.tv_city_select:
                Intent intent = new Intent(this, CommLocationActivity.class);
                startActivityForResult(intent,101);
                break;
            case R.id.linear_photo:
                TakePhoto();
                break;
            case R.id.tv_upload:
                Upload();
                break;
        }
    }

    public void TakePhoto()
    {
        TakePhotoPopupWindow window = new TakePhotoPopupWindow(this);
        window.setOnPhotoOptsSelectListener(new OnPhotoOptsSelectListener() {
            @Override
            public void onOptsSelect(PhotoPopupOpts opts) {
                if(opts==PhotoPopupOpts.TAKE_PHOTO)
                {
                    GalleryFinal.openCamera(101, new GalleryFinal.OnHanlderResultCallback() {
                        @Override
                        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                            if(reqeustCode==101 && resultList.size()>0)
                            {
                                String path = resultList.get(0).getPhotoPath();
                                if(TextUtils.isEmpty(correct) || which%2==0) {
                                    binding.ivId1.setImageURI(Uri.parse("file://" + path));
                                    correct = path;
                                }
                                else if(TextUtils.isEmpty(opposite) || which%2==1)
                                {
                                    binding.ivId2.setImageURI(Uri.parse("file://" + path));
                                    opposite = path;
                                }
                                which++;
                            }
                        }

                        @Override
                        public void onHanlderFailure(int requestCode, String errorMsg) {

                        }
                    });
                }
                else
                {
                    GalleryFinal.openGallerySingle(100, new GalleryFinal.OnHanlderResultCallback() {
                        @Override
                        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                            if(reqeustCode==100 && resultList.size()>0)
                            {
                                String path = resultList.get(0).getPhotoPath();
                                if(TextUtils.isEmpty(correct) || which%2==0) {
                                    binding.ivId1.setImageURI(Uri.parse("file://" + path));
                                    correct = path;
                                }
                                else if(TextUtils.isEmpty(opposite) || which%2==1)
                                {
                                    binding.ivId2.setImageURI(Uri.parse("file://" + path));
                                    opposite = path;
                                }
                                which++;
                            }
                        }

                        @Override
                        public void onHanlderFailure(int requestCode, String errorMsg) {

                        }
                    });
                }
            }
        });

        window.showAtDefaultLocation();
    }

    public void Upload()
    {
         if(!MethodConfig.EmptyEditText(binding.edtCarID,binding.edtEmail,binding.edtMyself,binding.edtName,binding.edtWechat) && !TextUtils.isEmpty(binding.tvCitySelect.getText()) )
         {
             if(TextUtils.isEmpty(correct))
             {
                 Toast.makeText(this,"请上传身份证照",Toast.LENGTH_LONG).show();
                 return;
             }
             networkModel.UserBeDesigner(MethodConfig.userInfo.getToken(),binding.edtName.getText().toString().trim(),
                     binding.edtEmail.getText().toString().trim(),binding.edtWechat.getText().toString().trim(),
                     binding.edtCarID.getText().toString().trim(),binding.tvCitySelect.getText().toString().trim(),
                     binding.edtMyself.getText().toString().trim(),correct,opposite,NetworkParams.DONUT);
         }
        else {
             Toast.makeText(this,"请先填写完整",Toast.LENGTH_LONG).show();
         }
    }
    @Override
    protected int getContentView() {
        return R.layout.activity_me_be_designer;
    }

    @Override
    public void onJsonObjectSuccess(BaseBean baseBean, NetworkParams paramsCode) {
        Toast.makeText(this,baseBean.getMsg(),Toast.LENGTH_LONG).show();
       if(baseBean.getErrorcode()==0)
       {
           MethodConfig.userInfo.setType(2);
           onBackPressed();
       }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode)
        {
            case 101:
                String city =  data.getStringExtra("city");
                binding.tvCitySelect.setText(city);
                break;
        }
    }
}
