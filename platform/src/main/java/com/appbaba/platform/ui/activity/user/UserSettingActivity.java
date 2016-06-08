package com.appbaba.platform.ui.activity.user;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.appbaba.platform.ActivityUserSettingBinding;
import com.appbaba.platform.ActivityUserSettingBinding;
import com.appbaba.platform.AppKeyMap;
import com.appbaba.platform.R;
import com.appbaba.platform.base.BaseActivity;
import com.appbaba.platform.entity.Base.BaseBean;
import com.appbaba.platform.entity.User.UserBean;
import com.appbaba.platform.entity.User.UserInfo;
import com.appbaba.platform.eum.NetworkParams;
import com.appbaba.platform.eum.PhotoPopupOpts;
import com.appbaba.platform.impl.LoginCallBack;
import com.appbaba.platform.impl.OnPhotoOptsSelectListener;
import com.appbaba.platform.method.MethodConfig;
import com.appbaba.platform.tools.AppTools;
import com.appbaba.platform.widget.ChangeValueDialog;
import com.appbaba.platform.widget.LoginDialog;
import com.appbaba.platform.widget.PopupWindow.TakePhotoPopupWindow;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * Created by ruby on 2016/5/10.
 */
public class UserSettingActivity extends BaseActivity {
    private ActivityUserSettingBinding binding;

    private  List<String> files = new ArrayList<>();
    @Override
    protected void InitView() {
        binding = (ActivityUserSettingBinding)viewDataBinding;
        binding.includeTopTitle.toolBar.setNavigationIcon(R.mipmap.icon_back);
        binding.includeTopTitle.title.setText(R.string.activity_me_setting_top_title);
        binding.includeTopTitle.title.setTextColor(Color.BLACK);

        binding.btnChange.setVisibility(View.GONE);
    }

    @Override
    protected void InitData() {
        if(MethodConfig.userInfo==null || TextUtils.isEmpty(MethodConfig.userInfo.getToken()))
        {
            binding.linearLogin.setVisibility(View.GONE);
            binding.tvUpload.setText("登录");
        }
        else {
            if(!TextUtils.isEmpty(MethodConfig.userInfo.getImgUrl()))
                binding.dvHead.setImageURI(Uri.parse(MethodConfig.userInfo.getImgUrl()));
            binding.linearLogin.setVisibility(View.VISIBLE);
            binding.tvUpload.setText("登出当前账号");
        }
    }

    @Override
    protected void InitEvent() {

    }

    @Override
    protected void InitListening() {
        binding.dvHead.setOnClickListener(this);
        binding.btnChange.setOnClickListener(this);
        binding.linearChangeName.setOnClickListener(this);
        binding.tvUpload.setOnClickListener(this);
    }

    @Override
    protected void OnClick(int id, View view) {
        switch (id)
        {
            case R.id.dv_head:
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
                                        binding.dvHead.setImageURI(Uri.parse("file://"+path));
                                        files.clear();
                                        files.add(path);
                                        binding.btnChange.setVisibility(View.VISIBLE);
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
                                        binding.dvHead.setImageURI(Uri.parse("file://"+path));
                                        files.clear();
                                        files.add(path);
                                        binding.btnChange.setVisibility(View.VISIBLE);
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
                break;
            case R.id.btn_change:
            {
                String filePath = pathToCompress(files.get(0));
                if(!TextUtils.isEmpty(filePath)) {
                    files.clear();
                    files.add(filePath);
                    networkModel.UserChangeHead(MethodConfig.userInfo.getToken(), files, "avatar", NetworkParams.DONUT);
                }
            }
                break;
            case R.id.linear_change_name:
            {
                ChangeValueDialog changeValueDialog = new ChangeValueDialog(this,"修改用户名");
                changeValueDialog.Show();
            }
                break;
            case R.id.tv_upload:
            {
                if(MethodConfig.userInfo==null || TextUtils.isEmpty(MethodConfig.userInfo.getToken()))
                {
                    setResult(101);
                    onBackPressed();
                }
                else {
                    AppTools.putStringSharedPreferences("password","");
                    networkModel.LogOut(MethodConfig.userInfo.getToken(),NetworkParams.CUPCAKE);
                }
            }
                break;
        }

    }

    public  String pathToCompress(String path)
    {
        File fileR = new File(path);
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        File file = new File(fileR.getParentFile().getAbsolutePath()+File.separator+"zhuangsehead.png");
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

    @Override
    protected int getContentView() {
        return R.layout.activity_me_setting;
    }

    @Override
    public void onJsonObjectSuccess(BaseBean baseBean, NetworkParams paramsCode) {
        if(baseBean.getErrorcode()==0)
        {
            if(paramsCode==NetworkParams.DONUT)
            {
                MethodConfig.userInfo.setImgUrl(AppKeyMap.BASEURL+baseBean.getPicture_thumb());
                binding.btnChange.setVisibility(View.GONE);
                binding.dvHead.setImageURI(Uri.parse(MethodConfig.userInfo.getImgUrl()));
            }
            else if (paramsCode==NetworkParams.CUPCAKE)
            {
                MethodConfig.userInfo = null;
                InitData();
            }
            else if(paramsCode==NetworkParams.FROYO)
            {
                String username = MethodConfig.userInfo.getUsername();
                String password = MethodConfig.userInfo.getPassword();
                AppTools.putStringSharedPreferences("username",username);
                AppTools.putStringSharedPreferences("password",password);
                UserBean userbean = (UserBean)baseBean;
                MethodConfig.userInfo = new UserInfo();
                MethodConfig.userInfo.setName(userbean.getUser().getUser_infor().getName());
                MethodConfig.userInfo.setToken(userbean.getUser().getUser_infor().getToken());
                MethodConfig.userInfo.setUsername(username);
                MethodConfig.userInfo.setPassword(password);
                MethodConfig.userInfo.setType(userbean.getUser().getUser_infor().getType());
                MethodConfig.userInfo.setImgUrl(AppKeyMap.BASEURL+userbean.getUser().getUser_infor().getPicture_thumb());
                MethodConfig.userInfo.setEusername(userbean.getUser().getUser_infor().getEasemob_username());
                MethodConfig.userInfo.setEpassword(userbean.getUser().getUser_infor().getEasemob_password());
                InitData();
            }
        }
    }
}
