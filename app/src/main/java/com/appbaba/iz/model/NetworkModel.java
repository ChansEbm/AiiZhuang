package com.appbaba.iz.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.appbaba.iz.AppKeyMap;
import com.appbaba.iz.entity.Base.BaseBean;
import com.appbaba.iz.entity.Login.AuthBean;
import com.appbaba.iz.entity.SellerListBean;
import com.appbaba.iz.eum.NetworkParams;
import com.appbaba.iz.impl.OkHttpResponseListener;
import com.appbaba.iz.tools.AppTools;
import com.appbaba.iz.tools.LogTools;
import com.appbaba.iz.tools.OkHttpBuilder;

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


    private void clearAllParams() {
        params.clear();
    }

    /**
     * @return the user's auth
     */
    private String getAuth() {
        if (AppKeyMap.IS_DEBUG) {
            return "ADUPMwxrV2VRYgBnB2BQZVRrUWUFMwdnVWxQZgZlA2EBYA4zBjNTYFcyUmNXYgZlBTULPQBkAmADPlZnAjADZAAODzcMPFdh==";
        } else {
            if (AppKeyMap.isAuthEmpty()) {
                LogTools.e("Auth is null,plz check");
                return "";
            } else {
                return AppTools.getStringSharedPreferences(AppKeyMap.AUTH,
                        "ADAPMAw2V2RRMQAyB2VQYVRlUWUFYwcwVT1QYwZmA2MBPg5kBmFTMFdoUjBXbQY4BTcLOAA1AmcDOFZhAmADZAAODz4MPQ==");
            }
        }
    }

    /**
     * new api
     */
    public void  getSellerList(NetworkParams networkParams){
        clearAllParams();
         new OkHttpBuilder.POST(appCompatActivity).urlGetSellerList("getSellerList").entityClass(SellerListBean.class).params(params)
                            .enqueue(networkParams,tOkHttpResponseListener);
    }

    public  void checkPhone(String phoneNum,NetworkParams networkParams)
    {
        clearAllParams();
        params.put("phone",phoneNum);
        new OkHttpBuilder.POST(appCompatActivity).urlCheckPhone("checkPhone").entityClass(BaseBean.class).params(params)
        .enqueue(networkParams,tOkHttpResponseListener);
    }

    public  void  sendSmsCode(String phoneNum,NetworkParams networkParams)
    {
        clearAllParams();
        params.put("phone",phoneNum);
        new OkHttpBuilder.POST(appCompatActivity).urlSendMsg("sendSmsCode").entityClass(BaseBean.class).params(params)
                .enqueue(networkParams,tOkHttpResponseListener);
    }

    public void  register(RegisterModel model)
    {
        clearAllParams();
        params.put("seller_id",model.getSeller_id());
        params.put("nickname",model.getNickname());
        params.put("shop_name",model.getShop_name());
        params.put("address",model.getAddress());
        params.put("phone",model.getPhone());
        params.put("code",model.getCode());
        params.put("password",model.getPassword());
        params.put("repassword",model.getRepassword());

        new OkHttpBuilder.POST(appCompatActivity).urlSendMsg("register").entityClass(BaseBean.class).params(params)
                .enqueue(model.getNetworkParams(),tOkHttpResponseListener);
    }

    public  void  Login(String phone,String password,String push_id,NetworkParams networkParams)
    {
        clearAllParams();
        params.put("phone",phone);
        params.put("password",password);
        params.put("push_id",push_id);
        new OkHttpBuilder.POST(appCompatActivity).urlSendMsg("login").entityClass(AuthBean.class).params(params)
                .enqueue(networkParams,tOkHttpResponseListener);
    }

}
