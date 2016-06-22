package com.appbaba.platform.ui.activity.user;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Handler;
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
import com.appbaba.platform.dialog.LoadingDialog;
import com.appbaba.platform.entity.Base.BaseBean;
import com.appbaba.platform.entity.User.UserBean;
import com.appbaba.platform.entity.User.UserInfo;
import com.appbaba.platform.eum.NetworkParams;
import com.appbaba.platform.eum.PhotoPopupOpts;
import com.appbaba.platform.impl.LoginCallBack;
import com.appbaba.platform.impl.OnPhotoOptsSelectListener;
import com.appbaba.platform.method.MethodConfig;
import com.appbaba.platform.tools.AppTools;
import com.appbaba.platform.ui.activity.comm.CommWebActivity;
import com.appbaba.platform.widget.ChangeValueDialog;
import com.appbaba.platform.widget.LoginDialog;
import com.appbaba.platform.widget.PopupWindow.TakePhotoPopupWindow;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * Created by ruby on 2016/5/10.
 */
public class UserSettingActivity extends BaseActivity {
    private ActivityUserSettingBinding binding;

    private  List<String> files = new ArrayList<>();

    private Handler handler;
    private File cacheFile;
    private LoadingDialog dialog;

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
        handler = new Handler();
        dialog = new LoadingDialog(this);
        cacheFile = new File(this.getCacheDir().getPath()+File.separator+"picasso-cache");
        StartGet();
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
        binding.dvHead.setOnClickListener(this);
        binding.btnChange.setOnClickListener(this);
        binding.linearChangeName.setOnClickListener(this);
        binding.tvUpload.setOnClickListener(this);
        binding.linearAboutUs.setOnClickListener(this);
        binding.linearPrivate.setOnClickListener(this);
        binding.linearService.setOnClickListener(this);
        binding.linearFeedback.setOnClickListener(this);
        binding.linearCache.setOnClickListener(this);
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
                                        binding.btnChange.performClick();
                                        //binding.btnChange.setVisibility(View.VISIBLE);
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
                                        binding.btnChange.performClick();
                                       // binding.btnChange.setVisibility(View.VISIBLE);
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
            case R.id.linear_feedback:
            {
                StartActivity(UserFeedBackActivity.class);
            }
                break;
            case R.id.linear_about_us: {
                String url = AppKeyMap.HEAD_API_PAGE + "see_us";
                Intent intent = new Intent(this, CommWebActivity.class);
                intent.putExtra("url", url);
                startActivity(intent);
            }
                break;
            case R.id.linear_service: {
                String url = AppKeyMap.HEAD_API_PAGE + "tos";
                Intent intent = new Intent(this, CommWebActivity.class);
                intent.putExtra("url", url);
                startActivity(intent);
            }
                break;
            case R.id.linear_private: {
                String url = AppKeyMap.HEAD_API_PAGE + "privacy";
                Intent intent = new Intent(this, CommWebActivity.class);
                intent.putExtra("url", url);
                startActivity(intent);
            }
                break;
            case R.id.linear_cache:
            {
                dialog.show();
                StartDel();
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

    public  float  GetSize()
    {
        if(cacheFile!=null) {
            File[] files = cacheFile.listFiles();
            if(files!=null) {
                float ll = 0;
                for (int i = 0; i < files.length; i++) {
                    float x = files[i].length();
                    ll += x;
                }
                return ll / 1024 / 1024 / 8;
            }
            else
            {
                return 0;
            }
        }
        else
        {
            return 0;
        }
    }

    public void StartGet()
    {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                String message = String.format(Locale.CHINA, "%.1fMB", GetSize());
                HandleSetTextForSize(message);
            }
        };
        timer.schedule(timerTask,0);
    }
    public  void StartDel()
    {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if(cacheFile!=null) {
                    File[] files = cacheFile.listFiles();
                    if (files != null) {
                        for(int i=0;i<files.length;i++)
                            files[i].delete();
                    }
                }
                StartGet();
            }
        };
        timer.schedule(timerTask,0);
    }

    public void  HandleSetTextForSize(final  String message)
    {
        if(handler!=null) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (binding.tvCache != null)
                        binding.tvCache.setText(message);
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            });
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
                MethodConfig.userInfo.setImgUrl(baseBean.getPicture());
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
                MethodConfig.userInfo.setImgUrl(userbean.getUser().getUser_infor().getPicture_thumb());
                MethodConfig.userInfo.setEusername(userbean.getUser().getUser_infor().getEasemob_username());
                MethodConfig.userInfo.setEpassword(userbean.getUser().getUser_infor().getEasemob_password());
                InitData();
            }
        }
    }
}
