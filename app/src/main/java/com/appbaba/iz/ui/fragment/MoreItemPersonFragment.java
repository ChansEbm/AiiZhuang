package com.appbaba.iz.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuWrapperFactory;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.appbaba.iz.FragmentMorePersonBinding;
import com.appbaba.iz.R;
import com.appbaba.iz.base.BaseFgm;
import com.appbaba.iz.entity.Base.BaseBean;
import com.appbaba.iz.entity.Login.UpdatePersonBean;
import com.appbaba.iz.eum.NetworkParams;
import com.appbaba.iz.eum.PhotoPopupOpts;
import com.appbaba.iz.impl.OnPhotoOptsSelectListener;
import com.appbaba.iz.method.MethodConfig;
import com.appbaba.iz.model.AddClientModel;
import com.appbaba.iz.tools.BitmapCompressTool;
import com.appbaba.iz.tools.CameraTools;
import com.appbaba.iz.ui.activity.TransferActivity;
import com.appbaba.iz.widget.PopupWindow.TakePhotoPopupWindow;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * Created by ruby on 2016/4/8.
 */
public class MoreItemPersonFragment extends BaseFgm implements Toolbar.OnMenuItemClickListener{
    private FragmentMorePersonBinding personBinding;

    private  List<String> files = new ArrayList<>();

    private AddClientModel model = new AddClientModel();
    @Override
    protected void initViews() {
//        Fresco.initialize(getContext());
        personBinding = (FragmentMorePersonBinding)viewDataBinding;
        personBinding.includeTopTitle.title.setText(R.string.more_fragment_person);
        personBinding.includeTopTitle.title.setTextColor(Color.BLACK);
        personBinding.includeTopTitle.toolBar.setBackgroundColor(Color.WHITE);
        personBinding.includeTopTitle.toolBar.setNavigationIcon(R.mipmap.more_arrow_dark_left);
        personBinding.includeTopTitle.toolBar.getMenu().add(0,R.id.menu_edit,0,"编辑").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        personBinding.btnChange.setVisibility(View.GONE);
        InitData();
        SetEditEnable(false);
    }

    public  void  InitData()
    {
        if(MethodConfig.localUser!=null)
        {
            if(!TextUtils.isEmpty(MethodConfig.localUser.getInfo().getAvatar()));
            {
                personBinding.dvHead.setImageURI(Uri.parse(MethodConfig.localUser.getInfo().getAvatar()));
            }
            personBinding.setItem(MethodConfig.localUser.getInfo());
            personBinding.tvArea.setTag(R.string.tag_value,MethodConfig.localUser.getInfo().getArea_ids());
        }
    }

    @Override
    protected void initEvents() {

        personBinding.dvHead.setOnClickListener(this);
        personBinding.btnChange.setOnClickListener(this);
        personBinding.btnUpload.setOnClickListener(this);
        personBinding.includeTopTitle.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)getContext()).finish();
            }
        });
        personBinding.includeTopTitle.toolBar.setOnMenuItemClickListener(this);
        personBinding.tvArea.setOnClickListener(this);
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
                                        personBinding.dvHead.setImageURI(Uri.parse("file://"+path));
                                        files.clear();
                                        files.add(path);
                                        personBinding.btnChange.setVisibility(View.VISIBLE);
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
                                        personBinding.dvHead.setImageURI(Uri.parse("file://"+path));
                                        files.clear();
                                        files.add(path);
                                        personBinding.btnChange.setVisibility(View.VISIBLE);
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
        File fileR = new File(path);
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        File file = new File(fileR.getParentFile().getAbsolutePath()+File.separator+"temp.png");
        if(file.exists())
        {
            file.delete();
        }
        int size = bitmap.getByteCount();
        if(size>2*1024*1024)
        {
            float x = (2*1024*1024)/(size*1.0f);
            Matrix matrix = new Matrix();
            float scale = (float) Math.sqrt(x);
            matrix.postScale(scale,scale);
            Bitmap bitmap1 = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
            try {
                FileOutputStream fos = new FileOutputStream(file);
                bitmap1.compress(Bitmap.CompressFormat.PNG,100,fos);
                fos.close();
                bitmap1.recycle();
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
        if(TextUtils.isEmpty(personBinding.edtName.getText()))
        {
            return  false;
        }
        model.setName(personBinding.edtName.getText().toString().trim());
        if(TextUtils.isEmpty(personBinding.edtShop.getText()))
        {
            return  false;
        }
        model.setShop(personBinding.edtShop.getText().toString().trim());
        if(TextUtils.isEmpty((String)personBinding.tvArea.getTag(R.string.tag_value)))
        {
            return  false;
        }
        model.setArea_ids((String) personBinding.tvArea.getTag(R.string.tag_value));
        if(TextUtils.isEmpty(personBinding.edtAddress.getText()))
        {
            return  false;
        }
        model.setAddress(personBinding.edtAddress.getText().toString().trim());
        return  true;
    }


    @Override
    public void onJsonObjectSuccess(Object t, NetworkParams paramsCode) {
        if(paramsCode==NetworkParams.CHANGEHEAD)
        {
            UpdatePersonBean bean = (UpdatePersonBean)t;
            MethodConfig.localUser.getInfo().setAvatar(bean.getAvatar());
            personBinding.dvHead.setImageURI(Uri.parse(MethodConfig.localUser.getInfo().getAvatar()));
            personBinding.btnChange.setVisibility(View.GONE);
        }
        if(paramsCode==NetworkParams.CHANGEPERSON)
        {
            BaseBean bean = (BaseBean)t;
            Toast.makeText(getContext(),bean.getMsg(),Toast.LENGTH_LONG).show();
            if(bean.getErrorcode()==0) {
                MethodConfig.localUser.getInfo().setAddress(model.getAddress());
                MethodConfig.localUser.getInfo().setArea_ids(model.getArea_ids());
                MethodConfig.localUser.getInfo().setArea_desc(personBinding.tvArea.getText().toString());
                MethodConfig.localUser.getInfo().setNickname(model.getName());
                MethodConfig.localUser.getInfo().setShop_name(model.getShop());

                InitData();
                SetEditEnable(false);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode)
        {
            case  100:
                String area_id = data.getStringExtra("area_id");
                String area_name = data.getStringExtra("area_name");
                personBinding.tvArea.setText(area_name);
                personBinding.tvArea.setTag(R.string.tag_value,area_id);
                break;
        }
    }



    @Override
    protected int getContentView() {
        return R.layout.fragment_more_item_person;
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menu_edit:
               if(item.getTitle()=="编辑")
               {
                   SetEditEnable(true);
               }
               else
               {
                   SetEditEnable(false);
               }
                break;
        }
        return false;
    }

    public  void  SetEditEnable(boolean isEdit)
    {
        personBinding.edtName.setEnabled(isEdit);
        personBinding.edtShop.setEnabled(isEdit);
        personBinding.edtAddress.setEnabled(isEdit);
        personBinding.tvArea.setEnabled(isEdit);
        if(isEdit)
        {
            personBinding.includeTopTitle.toolBar.getMenu().getItem(0).setTitle("取消");
            personBinding.btnUpload.setVisibility(View.VISIBLE);
        }
        else
        {
            personBinding.includeTopTitle.toolBar.getMenu().getItem(0).setTitle("编辑");
            personBinding.btnUpload.setVisibility(View.GONE);
        }
    }
}
