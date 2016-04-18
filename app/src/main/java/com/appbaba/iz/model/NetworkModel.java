package com.appbaba.iz.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.appbaba.iz.AppKeyMap;
import com.appbaba.iz.entity.Base.BaseBean;
import com.appbaba.iz.entity.Favourite.FavouriteBean;
import com.appbaba.iz.entity.Favourite.FavouriteDetailBean;
import com.appbaba.iz.entity.Friends.FriendsArticleBean;
import com.appbaba.iz.entity.Friends.FriendsBean;
import com.appbaba.iz.entity.Friends.FriendsClientBean;
import com.appbaba.iz.entity.Index.HomeBean;
import com.appbaba.iz.entity.Login.AuthBean;
import com.appbaba.iz.entity.SellerListBean;
import com.appbaba.iz.entity.main.CasesAttrEntity;
import com.appbaba.iz.entity.main.album.CaseEntity;
import com.appbaba.iz.entity.main.album.CasesAttrSelection;
import com.appbaba.iz.entity.main.album.ProductEntity;
import com.appbaba.iz.eum.NetworkParams;
import com.appbaba.iz.impl.OkHttpResponseListener;
import com.appbaba.iz.tools.AppTools;
import com.appbaba.iz.tools.LogTools;
import com.appbaba.iz.tools.OkHttpBuilder;
import com.github.pwittchen.prefser.library.Prefser;

import org.antlr.v4.runtime.misc.NotNull;

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

    private void addAuth() {
        if (!TextUtils.isEmpty(getAuth()))
            params.put("auth", getAuth());
    }


    /**
     * new api
     */
    public void getSellerList(NetworkParams networkParams) {
        clearAllParams();

        new OkHttpBuilder.POST(appCompatActivity).urlComm("getSellerList").entityClass
                (SellerListBean.class).params(params)
                .enqueue(networkParams, tOkHttpResponseListener);

    }

    public void checkPhone(String phoneNum, NetworkParams networkParams) {
        clearAllParams();

        params.put("phone",phoneNum);
        new OkHttpBuilder.POST(appCompatActivity).urlLogin("checkPhone").entityClass(BaseBean.class).params(params)
        .enqueue(networkParams,tOkHttpResponseListener);
    }

    public void sendSmsCode(String phoneNum, NetworkParams networkParams) {
        clearAllParams();

        params.put("phone",phoneNum);
        new OkHttpBuilder.POST(appCompatActivity).urlLogin("sendSmsCode").entityClass(BaseBean.class).params(params)
                .enqueue(networkParams,tOkHttpResponseListener);
    }

    public void register(RegisterModel model) {
        clearAllParams();

        params.put("seller_id",model.getSeller_id());
        params.put("nickname",model.getNickname());
        params.put("shop_name",model.getShop_name());
        params.put("address",model.getAddress());
        params.put("phone",model.getPhone());
        params.put("code",model.getCode());
        params.put("password",model.getPassword());
        params.put("repassword",model.getRepassword());

        new OkHttpBuilder.POST(appCompatActivity).urlLogin("register").entityClass(BaseBean.class).params(params)
                .enqueue(model.getNetworkParams(),tOkHttpResponseListener);
    }

    public void Login(String phone, String password, String push_id, NetworkParams networkParams) {
        clearAllParams();
        params.put("phone", phone);
        params.put("password", password);
        params.put("push_id", push_id);
        new OkHttpBuilder.POST(appCompatActivity).urlLogin("login").entityClass(AuthBean.class)
                .params(params)
                .enqueue(networkParams, tOkHttpResponseListener);
    }

    public void casesAttrs(NetworkParams networkParams) {
        clearAllParams().addAuth();
        new OkHttpBuilder.POST(appCompatActivity).urlCases("casesAttrs").params(params)
                .entityClass(CasesAttrEntity.class).enqueue(networkParams, tOkHttpResponseListener);
    }

    public void cases(String productId, String keyword, String page, String pageSize,
                      CasesAttrSelection casesAttrSelection, NetworkParams networkParams) {
        clearAllParams().addAuth();
        String customerId = new Prefser(AppTools.getSharePreferences()).get(AppKeyMap.CUSTOMERID,
                String.class, "");
        params.put("customer_id", customerId);
        params.put("product_id", productId);
        params.put("keyword", keyword);
        params.put("page", page);
        params.put("page_size", pageSize);

        params.put("style_id", casesAttrSelection.getStyleId());
        params.put("space_id", casesAttrSelection.getSpaceId());
        params.put("cate_id", casesAttrSelection.getCateId());
        new OkHttpBuilder.POST(appCompatActivity).urlCases("cases").params(params)
                .entityClass(CaseEntity.class).enqueue(networkParams, tOkHttpResponseListener);
    }

    public void product(String productId, String keyword, String page, String pageSize,
                        CasesAttrSelection casesAttrSelection, NetworkParams networkParams) {
        clearAllParams().addAuth();
        String customerId = new Prefser(AppTools.getSharePreferences()).get(AppKeyMap.CUSTOMERID,
                String.class, "");
        params.put("customer_id", customerId);
        params.put("product_id", productId);
        params.put("keyword", keyword);
        params.put("page", page);
        params.put("page_size", pageSize);

        params.put("style_id", casesAttrSelection.getStyleId());
        params.put("size_id", casesAttrSelection.getSizeId());
        params.put("cate_id", casesAttrSelection.getCateId());
        new OkHttpBuilder.POST(appCompatActivity).urlCases("product").params(params)
                .entityClass(ProductEntity.class).enqueue(networkParams, tOkHttpResponseListener);
//        params.put("phone",phone);
//        params.put("password",password);
//        params.put("push_id",push_id);
//        new OkHttpBuilder.POST(appCompatActivity).urlLogin("login").entityClass(AuthBean.class).params(params)
//                .enqueue(networkParams,tOkHttpResponseListener);
    }

    public void  HomeIndex(String auth,NetworkParams networkParams)
    {
        clearAllParams();
        params.put("auth",auth);
        new OkHttpBuilder.POST(appCompatActivity).urlIndex("index").entityClass(HomeBean.class).params(params)
                .enqueue(networkParams,tOkHttpResponseListener);
    }

    public  void HomeSubject(String auth,int page,int page_size,NetworkParams networkParams)
    {
        clearAllParams();
        params.put("auth",auth);
        params.put("page",""+page);
        params.put("page_size",""+page_size);
        new OkHttpBuilder.POST(appCompatActivity).urlSubject("subject").entityClass(FavouriteBean.class).params(params)
                .enqueue(networkParams,tOkHttpResponseListener);
    }

    public void  HomeSubjectDetail(String auth,String subject_id,NetworkParams networkParams)
    {
        clearAllParams();
        params.put("auth",auth);
        params.put("subject_id",subject_id);
        new OkHttpBuilder.POST(appCompatActivity).urlSubject("subjectDetail").entityClass(FavouriteDetailBean.class).params(params)
                .enqueue(networkParams,tOkHttpResponseListener);
    }

    public  void  HomeMarketingAddCustomer(String auth,AddClientModel model)
    {
        clearAllParams();
        params.put("auth",auth);
        params.put("name",model.getName());
        params.put("phone",model.getPhone());
        params.put("area_ids",model.getArea_ids());
        params.put("address",model.getAddress());
        new OkHttpBuilder.POST(appCompatActivity).urlMarketing("addCustomer").entityClass(BaseBean.class).params(params)
                .enqueue(model.getNetworkParams(),tOkHttpResponseListener);
    }

    public  void  HomeMarketingCustomerList(@NotNull String auth,NetworkParams networkParams)
    {
         clearAllParams();
        params.put("auth",auth);
        new OkHttpBuilder.POST(appCompatActivity).urlMarketing("customerList").entityClass(FriendsClientBean.class).params(params)
                .enqueue(networkParams,tOkHttpResponseListener);
    }

    public  void  HomeMarketingSaveCustomer(String auth,AddClientModel model)
    {
        clearAllParams();
        params.put("auth",auth);
        params.put("customer_id",model.getId());
        params.put("name",model.getName());
        params.put("phone",model.getPhone());
        params.put("area_ids",model.getArea_ids());
        params.put("address",model.getAddress());
        new OkHttpBuilder.POST(appCompatActivity).urlMarketing("saveCustomer").entityClass(BaseBean.class).params(params)
                .enqueue(model.getNetworkParams(),tOkHttpResponseListener);
    }

    public  void  HomeMarketingDelCustomer(String auth,String customer_id,NetworkParams networkParams)
    {
        clearAllParams();
        params.put("auth",auth);
        params.put("customer_id",customer_id);
        new OkHttpBuilder.POST(appCompatActivity).urlMarketing("delCustomer").entityClass(BaseBean.class).params(params)
                .enqueue(networkParams,tOkHttpResponseListener);
    }

    public  void  HomeMarketingArticleCate(String auth,NetworkParams networkParams,boolean isNeedLoading)
    {
        clearAllParams();
        params.put("auth",auth);
        new OkHttpBuilder.POST(appCompatActivity).urlMarketing("articleCate").entityClass(FriendsBean.class).params(params).setIsNeedLoadingDialog(isNeedLoading)
                .enqueue(networkParams,tOkHttpResponseListener);
    }

    public  void  HomeMarketingArticle(String auth,String article_cate_id,int page,int page_size,NetworkParams networkParams)
    {
        clearAllParams();
        params.put("auth",auth);
        params.put("article_cate_id",article_cate_id);
        params.put("page",""+page);
        params.put("page_size",""+page_size);

        new OkHttpBuilder.POST(appCompatActivity).urlMarketing("article").entityClass(FriendsArticleBean.class).params(params)
                .enqueue(networkParams,tOkHttpResponseListener);
    }

    public  void  HomeMoreChangePwd(String auth,PasswordModel model,NetworkParams networkParams)
    {
        clearAllParams();
        params.put("auth",auth);
        params.put("old_password",model.getPwd());
        params.put("new_password",model.getnPwd());
        params.put("re_password",model.getRnPwd());
        new OkHttpBuilder.POST(appCompatActivity).urlMore("editPassword").entityClass(BaseBean.class).params(params)
                .enqueue(networkParams,tOkHttpResponseListener);
    }

}
