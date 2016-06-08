package com.appbaba.platform.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.appbaba.platform.AppKeyMap;
import com.appbaba.platform.entity.Base.BaseBean;
import com.appbaba.platform.entity.User.DesignerDetailBean;
import com.appbaba.platform.entity.User.MyInspirationBean;
import com.appbaba.platform.entity.User.MyProductBean;
import com.appbaba.platform.entity.User.UserBean;
import com.appbaba.platform.entity.inspiration.InspirationDetailBean;
import com.appbaba.platform.entity.inspiration.InspirationListBean;
import com.appbaba.platform.entity.product.ProductDetailBean;
import com.appbaba.platform.entity.product.ProductListBean;
import com.appbaba.platform.eum.NetworkParams;
import com.appbaba.platform.impl.OkHttpResponseListener;
import com.appbaba.platform.tools.AppTools;
import com.appbaba.platform.tools.LogTools;
import com.appbaba.platform.tools.OkHttpBuilder;
import com.github.pwittchen.prefser.library.Prefser;
import com.google.repacked.antlr.v4.runtime.misc.NotNull;
import com.squareup.okhttp.internal.Network;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ChanZeeBm on 2015/12/26.
 */
public class NetworkModel<E> {
    private AppCompatActivity appCompatActivity;
    private Context context;
    private OkHttpResponseListener<E> tOkHttpResponseListener;
    private Map<String, String> params = new HashMap<>();

    private Map<String, String> fileMaps = new HashMap<>();//file params and each key
    // carry one filePath
    private List<List<String>> fileLists = new ArrayList<>();// file params and each key could be
    // carry multi filePaths

    public NetworkModel(@NonNull AppCompatActivity appCompatActivity) {
        this.appCompatActivity = appCompatActivity;
    }

    public NetworkModel(@NonNull Context context) {
        this.context = context;
        if (context instanceof AppCompatActivity) {
            this.appCompatActivity = (AppCompatActivity) context;
        }
    }

    public void setResultCallBack(OkHttpResponseListener<E> tOkHttpResponseListener) {
        this.tOkHttpResponseListener = tOkHttpResponseListener;
    }

    private boolean isNecessaryFieldEmpty(String... strings) {
        for (String string : strings) {
            LogTools.w(string);
            if (TextUtils.isEmpty(string)) {
                logError();
                return true;
            }
        }
        return false;
    }

    private boolean isNecessaryListEmpty(List<String>... lists) {
        for (List<String> list : lists) {
            if (list.isEmpty()) {
                logError();
                return true;
            }
        }
        return false;
    }


    private void logError() {
        LogTools.e("necessary field is empty");
    }


    private NetworkModel<E> clearAllParams() {
        params.clear();
        return this;
    }

    /**
     * @return the user's auth
     */
    private String getAuth() {
        return AppTools.getStringSharedPreferences(AppKeyMap.AUTH,"");
    }

    private NetworkModel addAuth() {
        if (!TextUtils.isEmpty(getAuth()))
            params.put("auth", getAuth());
        return this;
    }

    public void InspirationList(int page, NetworkParams networkParams)
    {
        params.clear();
        params.put("page",""+page);
        new OkHttpBuilder.POST(appCompatActivity).params(params).urlInspiration("inspirationList").entityClass(InspirationListBean.class).enqueue(networkParams,tOkHttpResponseListener);
    }

    public void InspirationDetail(String inspiration_id, NetworkParams networkParams)
    {
        params.clear();
        params.put("inspiration_id",inspiration_id);
        new OkHttpBuilder.POST(appCompatActivity).params(params).urlInspiration("inspirationDetail").entityClass(InspirationDetailBean.class).enqueue(networkParams,tOkHttpResponseListener);
    }

    public void ProductList(int page,NetworkParams networkParams)
    {
        params.clear();
        params.put("page",""+page);
        new OkHttpBuilder.POST(appCompatActivity).params(params).urlGoods("goodsList").entityClass(ProductListBean.class).enqueue(networkParams,tOkHttpResponseListener);
    }

    public void ProductDetail(String id,NetworkParams networkParams)
    {
        params.clear();
        params.put("goods_id",id);
        new OkHttpBuilder.POST(appCompatActivity).params(params).urlGoods("goodsDetail").entityClass(ProductDetailBean.class).
                enqueue(networkParams,tOkHttpResponseListener);
    }

    public void CheckReg(String phoneNum,NetworkParams networkParams)
    {
        params.clear();
        params.put("tell",phoneNum);
        new OkHttpBuilder.POST(appCompatActivity).params(params).urlLogin("checkReg").entityClass(BaseBean.class).
                enqueue(networkParams,tOkHttpResponseListener);
    }

    public void SendSmsCode(String phoneNum,NetworkParams networkParams)
    {
        params.clear();
        params.put("phone",phoneNum);
        new OkHttpBuilder.POST(appCompatActivity).params(params).urlLogin("sendSmsCode").entityClass(BaseBean.class).
                enqueue(networkParams,tOkHttpResponseListener);
    }

    public void Register(String phoneNum,String password,String code,NetworkParams networkParams)
    {
        params.clear();
        params.put("tell",phoneNum);
        params.put("password",password);
        params.put("code",code);
        new OkHttpBuilder.POST(appCompatActivity).params(params).urlLogin("register").entityClass(BaseBean.class).
                enqueue(networkParams,tOkHttpResponseListener);
    }

    public void Login(String phoneNum,String password,String token,NetworkParams networkParams)
    {
        params.clear();
        params.put("tell",phoneNum);
        params.put("password",password);
        params.put("token",token);
        params.put("num","12");
        new OkHttpBuilder.POST(appCompatActivity).params(params).urlLogin("login").entityClass(UserBean.class).
                enqueue(networkParams,tOkHttpResponseListener);
    }

    public void LogOut(String token,NetworkParams networkParams)
    {
        params.clear();
        params.put("token",token);
        new OkHttpBuilder.POST(appCompatActivity).params(params).urlLogin("logout").entityClass(BaseBean.class).
                enqueue(networkParams,tOkHttpResponseListener);
    }

    public  void  UserChangeHead(String token,List<String> files,String key,NetworkParams networkParams)
    {
        clearAllParams();
        params.put("token",token);
        new OkHttpBuilder.POST(appCompatActivity).urlUser("uploadAvatar").entityClass(BaseBean.class).params(params,files,key)
                .enqueue(networkParams,tOkHttpResponseListener);
    }

    public void UserChangeName(String token,String new_name,NetworkParams networkParams)
    {
        clearAllParams();
        params.put("token",token);
        params.put("new_name",new_name);
        new OkHttpBuilder.POST(appCompatActivity).urlUser("modUserName").entityClass(BaseBean.class).params(params)
                .enqueue(networkParams,tOkHttpResponseListener);
    }

    public void UserBeDesigner(String token,String name,String email,String wechat,String cardID,String city,String myself,String file1,String file2,NetworkParams networkParams)
    {
        clearAllParams();
        params.put("token",token);
        params.put("name",name);
        params.put("email",email);
        params.put("wechat",wechat);
        params.put("city",city);
        params.put("id_card",cardID);
        params.put("introduce",myself);
        params.put("name",name);
        List<String> list = new ArrayList<>();
        list.add(file1);
        if(!TextUtils.isEmpty(file2))
        list.add(file2);
        List<List<String>> lists = new ArrayList<>();
        lists.add(list);

        if(list.size()==2) {
            new OkHttpBuilder.POST(appCompatActivity).urlDesign("become").entityClass(BaseBean.class).params(params, lists, "correct", "opposite")
                    .enqueue(networkParams, tOkHttpResponseListener);
        }
        else {
            new OkHttpBuilder.POST(appCompatActivity).urlDesign("become").entityClass(BaseBean.class).params(params, lists, "correct")
                    .enqueue(networkParams, tOkHttpResponseListener);
        }
    }

    public void DesignerProfile(String userID,NetworkParams networkParams)
    {
        clearAllParams();
        params.put("user_id",userID);
        new OkHttpBuilder.POST(appCompatActivity).urlDesign("profile").entityClass(DesignerDetailBean.class).params(params)
                .enqueue(networkParams, tOkHttpResponseListener);
    }


    public void MyInspiration(String token,int page,int num,NetworkParams networkParams)
    {
        clearAllParams();
        params.put("token",token);
        params.put("page",""+page);
        params.put("num",""+num);
        new OkHttpBuilder.POST(appCompatActivity).urlInspiration("myInspiration").entityClass(MyInspirationBean.class).params(params)
                .enqueue(networkParams, tOkHttpResponseListener);
    }

    public void MyProduct(String token,int page,int num,NetworkParams networkParams)
    {
        clearAllParams();
        params.put("token",token);
        params.put("page",""+page);
        params.put("num",""+num);
        new OkHttpBuilder.POST(appCompatActivity).urlGoods("myGoods").entityClass(MyProductBean.class).params(params)
                .enqueue(networkParams, tOkHttpResponseListener);
    }


}
