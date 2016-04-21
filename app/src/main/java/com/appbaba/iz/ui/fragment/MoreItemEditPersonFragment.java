package com.appbaba.iz.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;

import com.appbaba.iz.FragmentMoreEditPersonBinding;
import com.appbaba.iz.FragmentMorePersonBinding;
import com.appbaba.iz.R;
import com.appbaba.iz.base.BaseFgm;
import com.appbaba.iz.entity.Base.BaseBean;
import com.appbaba.iz.entity.Login.AuthBean;
import com.appbaba.iz.entity.Login.UpdatePersonBean;
import com.appbaba.iz.eum.NetworkParams;
import com.appbaba.iz.eum.PhotoPopupOpts;
import com.appbaba.iz.impl.OnPhotoOptsSelectListener;
import com.appbaba.iz.method.MethodConfig;
import com.appbaba.iz.model.AddClientModel;
import com.appbaba.iz.tools.AppTools;
import com.appbaba.iz.tools.BitmapCompressTool;
import com.appbaba.iz.tools.LogTools;
import com.appbaba.iz.ui.activity.TransferActivity;
import com.appbaba.iz.widget.PopupWindow.TakePhotoPopupWindow;
import com.squareup.okhttp.internal.http.RetryableSink;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * Created by ruby on 2016/4/18.
 */
public class MoreItemEditPersonFragment extends BaseFgm {
    private FragmentMoreEditPersonBinding editPersonBinding;

    private  List<String> files = new ArrayList<>();

    private AddClientModel model = new AddClientModel();

    @Override
    protected void initViews() {
        editPersonBinding = (FragmentMoreEditPersonBinding)viewDataBinding;
        editPersonBinding.includeTopTitle.title.setText(R.string.more_fragment_person);
        editPersonBinding.includeTopTitle.title.setTextColor(Color.BLACK);
        editPersonBinding.includeTopTitle.toolBar.setBackgroundColor(Color.WHITE);
        editPersonBinding.includeTopTitle.toolBar.setNavigationIcon(R.mipmap.more_arrow_dark_left);
        if(MethodConfig.localUser!=null)
        {
            if(!TextUtils.isEmpty(MethodConfig.localUser.getInfo().getAvatar()));
            {
                editPersonBinding.dvHead.setImageURI(Uri.parse(MethodConfig.localUser.getInfo().getAvatar()));
            }
           // Picasso.with(getContext()).load(MethodConfig.localUser.getInfo().getAvatar()).into(editPersonBinding.dvHead);

            editPersonBinding.setItem(MethodConfig.localUser.getInfo());
        }
        editPersonBinding.btnChange.setVisibility(View.GONE);
    }

    @Override
    protected void initEvents() {

        editPersonBinding.dvHead.setOnClickListener(this);
        editPersonBinding.btnChange.setOnClickListener(this);
        editPersonBinding.btnUpload.setOnClickListener(this);
        editPersonBinding.includeTopTitle.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)getContext()).finish();
            }
        });
        editPersonBinding.tvArea.setOnClickListener(this);
    }

    @Override
    protected void noNetworkStatus() {

    }

    @Override
    protected void onClick(int id, View view) {

        switch (id)
        {
            case R.id.dv_head:
                TakePhotoPopupWindow window = new TakePhotoPopupWindow(getContext());
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
                                        editPersonBinding.dvHead.setImageURI(Uri.parse("file://"+path));
                                        files.clear();
                                        files.add(path);
                                        editPersonBinding.btnChange.setVisibility(View.VISIBLE);
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
                                        editPersonBinding.dvHead.setImageURI(Uri.parse("file://"+path));
                                        files.clear();
                                        files.add(path);
                                        editPersonBinding.btnChange.setVisibility(View.VISIBLE);
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
                break;
            case R.id.tv_area:
            {
                Intent intent = new Intent(getContext(), TransferActivity.class);
                intent.putExtra("fragment",17);
                startActivityForResult(intent,100);
            }
                break;
            case R.id.btn_change:
            {
                String filePath = pathToCompress(files.get(0));
                if(!TextUtils.isEmpty(filePath)) {
                    files.clear();
                    files.add(filePath);
                    networkModel.HomeMoreChangeHead(MethodConfig.localUser.getAuth(), files, "thumb", NetworkParams.CHANGEHEAD);
                }
            }
                break;
            case R.id.btn_upload:
            {
                  if(CheckInput())
                  {
                      model.setNetworkParams(NetworkParams.CHANGEPERSON);
                      networkModel.HomeMoreChangePerson(MethodConfig.localUser.getAuth(),model);
                  }
            }
                break;
        }
    }

    public  String pathToCompress(String path)
    {
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        File file = new File("/sdcard/temp.jpeg");
        int size = bitmap.getByteCount();
        if(size>2*1024*1024*8)
        {
            float x = size*1.0f/(2*1024*1024*8);
            Bitmap bitmap1 = BitmapCompressTool.getRadioBitmap(path,(int) (bitmap.getWidth()/ Math.sqrt(x)),(int) (bitmap.getHeight()/ Math.sqrt(x)));
           try {
               FileOutputStream fos = new FileOutputStream(file);
               bitmap1.compress(Bitmap.CompressFormat.JPEG,100,fos);
               return file.getAbsolutePath();
           }
           catch (Exception ex)
           {
               return "";
           }

        }
        else
        {
            return path;
        }

    }
    public boolean CheckInput()
    {
        if(TextUtils.isEmpty(editPersonBinding.edtName.getText()))
        {
            return  false;
        }
        model.setName(editPersonBinding.edtName.getText().toString().trim());
        if(TextUtils.isEmpty(editPersonBinding.edtShop.getText()))
        {
            return  false;
        }
        model.setShop(editPersonBinding.edtShop.getText().toString().trim());
        if(TextUtils.isEmpty((String)editPersonBinding.tvArea.getTag(R.string.tag_value)))
        {
            return  false;
        }
        model.setArea_ids((String) editPersonBinding.tvArea.getTag(R.string.tag_value));
        if(TextUtils.isEmpty(editPersonBinding.edtAddress.getText()))
        {
            return  false;
        }
        model.setAddress(editPersonBinding.edtAddress.getText().toString().trim());
        return  true;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_more_item_edit_person;
    }

    @Override
    public void onJsonObjectSuccess(Object t, NetworkParams paramsCode) {
        if(paramsCode==NetworkParams.CHANGEHEAD)
        {
            UpdatePersonBean bean = (UpdatePersonBean)t;
            MethodConfig.localUser.getInfo().setAvatar(bean.getAvatar());
            editPersonBinding.dvHead.setImageURI(Uri.parse(MethodConfig.localUser.getInfo().getAvatar()));
            editPersonBinding.btnChange.setVisibility(View.GONE);
        }
        if(paramsCode==NetworkParams.CHANGEPERSON)
        {
            MethodConfig.localUser.getInfo().setAddress(model.getAddress());
            MethodConfig.localUser.getInfo().setArea_ids(model.getArea_ids());
            MethodConfig.localUser.getInfo().setArea_desc(editPersonBinding.tvArea.getText().toString());
            MethodConfig.localUser.getInfo().setNickname(model.getName());
            MethodConfig.localUser.getInfo().setShop_name(model.getShop());
            ((Activity)getContext()).finish();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode)
        {
            case  100:
                String area_id = data.getStringExtra("area_id");
                String area_name = data.getStringExtra("area_name");
                editPersonBinding.tvArea.setText(area_name);
                editPersonBinding.tvArea.setTag(R.string.tag_value,area_id);
                break;
        }
    }
}
