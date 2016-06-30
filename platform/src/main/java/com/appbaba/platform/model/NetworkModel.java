package com.appbaba.platform.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.appbaba.platform.AppKeyMap;
import com.appbaba.platform.entity.Base.BaseBean;
import com.appbaba.platform.entity.User.FriendsBean;
import com.appbaba.platform.entity.User.MessageDetailBean;
import com.appbaba.platform.entity.User.MessageListBean;
import com.appbaba.platform.entity.comm.BaseHotWordsBean;
import com.appbaba.platform.entity.User.DesignerDetailBean;
import com.appbaba.platform.entity.User.DesignerEMBean;
import com.appbaba.platform.entity.User.MyInspirationBean;
import com.appbaba.platform.entity.User.MyProductBean;
import com.appbaba.platform.entity.User.UserBean;
import com.appbaba.platform.entity.User.UserCollectionListBean;
import com.appbaba.platform.entity.User.UserConcernListBean;
import com.appbaba.platform.entity.User.UserInspirationListBean;
import com.appbaba.platform.entity.comm.InspirationPhotoBean;
import com.appbaba.platform.entity.inspiration.AllInspirationBean;
import com.appbaba.platform.entity.inspiration.InspirationDetailBean;
import com.appbaba.platform.entity.inspiration.InspirationListBean;
import com.appbaba.platform.entity.product.ProductDetailBean;
import com.appbaba.platform.entity.product.ProductListBean;
import com.appbaba.platform.eum.NetworkParams;
import com.appbaba.platform.impl.OkHttpResponseListener;
import com.appbaba.platform.method.MethodConfig;
import com.appbaba.platform.tools.AppTools;
import com.appbaba.platform.tools.LogTools;
import com.appbaba.platform.tools.OkHttpBuilder;
import com.github.pwittchen.prefser.library.Prefser;
import com.github.pwittchen.prefser.library.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;

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

    public void  BaseHotWords(NetworkParams networkParams)
    {
        params.clear();
        new OkHttpBuilder.POST(appCompatActivity).params(params).urlBase("hot_words").
                entityClass(BaseHotWordsBean.class).
                enqueue(networkParams,tOkHttpResponseListener);
    }

    public void FeedBack(String token,String msg,NetworkParams networkParams)
    {
        params.clear();
        params.put("token",token);
        params.put("content",msg);
        new OkHttpBuilder.POST(appCompatActivity).params(params).urlBase("feed_back").
                entityClass(BaseBean.class).
                enqueue(networkParams,tOkHttpResponseListener);
    }

    public void InspirationList(int page,int num ,NetworkParams networkParams)
    {
        params.clear();
        params.put("page",""+page);
        params.put("num",""+num);
        new OkHttpBuilder.POST(appCompatActivity).params(params).urlInspiration("inspirationList").entityClass(InspirationListBean.class).enqueue(networkParams,tOkHttpResponseListener);
    }

    public void  SearchInspiration(String word, String sort, List<String> styleList,List<String> spaceList,int page,int num,NetworkParams networkParams)
    {
        params.clear();
        params.put("page",""+page);
        params.put("num",""+num);
        params.put("word",word);
        params.put("sort",sort);
        List<List<String>> lists = new ArrayList<>();
        lists.add(styleList);
        lists.add(spaceList);
        new OkHttpBuilder.POST(appCompatActivity).params(params,lists,"style[]","space[]").
                urlInspiration("search").
                entityClass(InspirationListBean.class).
                enqueue(networkParams,tOkHttpResponseListener);
    }

    public void InspirationDetail(String inspiration_id, NetworkParams networkParams)
    {
        params.clear();
        params.put("inspiration_id",inspiration_id);
        new OkHttpBuilder.POST(appCompatActivity).params(params).urlInspiration("inspirationDetail").entityClass(InspirationDetailBean.class).enqueue(networkParams,tOkHttpResponseListener);
    }

    public void UploadInspiration(String token, String title, String desc, String label, List<InspirationPhotoBean> list,NetworkParams networkParams)
    {
        params.clear();
        params.put("token",token);
        params.put("title",title);
        params.put("desc",desc);
        params.put("label",label);
        List<List<String>> lists = new ArrayList<>();
        List<String> list1 = new ArrayList<>();
        List<String> names = new ArrayList<>();

        for(int i =0;i<list.size();i++)
        {
           list1.add(list.get(i).getImageUrl());
            names.add(list.get(i).getDetail());
        }
        lists.add(names);
        lists.add(list1);

        new OkHttpBuilder.POST(appCompatActivity).params(params,lists,"design[][desc]","image[]").urlInspiration("publish").
                entityClass(BaseBean.class).enqueue(networkParams,tOkHttpResponseListener);
    }

    public void ProductList(int page,int num,NetworkParams networkParams)
    {
        params.clear();
        params.put("page",""+page);
        params.put("num",""+num);
        new OkHttpBuilder.POST(appCompatActivity).params(params).urlGoods("goodsList").entityClass(ProductListBean.class).enqueue(networkParams,tOkHttpResponseListener);
    }

    public void  SearchProduct(String word, String sort, List<String> styleList,List<String> spaceList,int page,int num,NetworkParams networkParams)
    {
        params.clear();
        params.put("page",""+page);
        params.put("num",""+num);
        params.put("word",word);
        params.put("sort",sort);
        List<List<String>> lists = new ArrayList<>();
        lists.add(styleList);
        lists.add(spaceList);
        new OkHttpBuilder.POST(appCompatActivity).params(params,lists,"style[]","space[]").
                urlGoods("search").
                entityClass(ProductListBean.class).
                enqueue(networkParams,tOkHttpResponseListener);
    }

    public void ProductDetail(String id,NetworkParams networkParams)
    {
        params.clear();
        params.put("goods_id",id);
        new OkHttpBuilder.POST(appCompatActivity).params(params).urlGoods("goodsDetail").entityClass(ProductDetailBean.class).
                enqueue(networkParams,tOkHttpResponseListener);
    }

    public void Collect(String token,String id,NetworkParams networkParams)
    {
        params.clear();
        params.put("token",token);
        params.put("good_id",id);
        new OkHttpBuilder.POST(appCompatActivity).params(params).urlGoods("collect").entityClass(BaseBean.class).
                enqueue(networkParams,tOkHttpResponseListener);
    }

    public void GetCheckCollect(String token,String id,NetworkParams networkParams)
    {
        params.clear();
        params.put("token",token);
        params.put("good_id",id);
        new OkHttpBuilder.POST(appCompatActivity).params(params).urlGoods("getCheckCollect").entityClass(BaseBean.class).
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
        String pushID = MethodConfig.jpush_id;
        params.clear();
        params.put("tell",phoneNum);
        params.put("password",password);
        params.put("token",token);
        params.put("push_id",pushID);
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

    public void GetUserEMID(String token,String design_id,NetworkParams networkParams)
    {
        clearAllParams();
        params.put("token",token);
        params.put("design_id",design_id);
        new OkHttpBuilder.POST(appCompatActivity).urlUser("getEasemobUserName").entityClass(DesignerEMBean.class).params(params)
                .enqueue(networkParams,tOkHttpResponseListener);
    }

    public void GetFriendList(String token,int page,int num,NetworkParams networkParams)
    {
        clearAllParams();
        params.put("token",token);
        params.put("page",""+page);
        params.put("num",""+num);
        new OkHttpBuilder.POST(appCompatActivity).urlUser("get_friends_list").entityClass(FriendsBean.class).params(params)
                .enqueue(networkParams,tOkHttpResponseListener);
    }

    public void UserInspiration(String token,int page,int num,NetworkParams networkParams)
    {
        clearAllParams();
        params.put("token",token);
        params.put("page",""+page);
        params.put("num",""+num);
        new OkHttpBuilder.POST(appCompatActivity).urlUser("userInspiration").entityClass(UserInspirationListBean.class).params(params)
                .enqueue(networkParams,tOkHttpResponseListener);
    }
    public void UserKeep(String token,int page,int num,NetworkParams networkParams)
    {
        clearAllParams();
        params.put("token",token);
        params.put("page",""+page);
        params.put("num",""+num);
        new OkHttpBuilder.POST(appCompatActivity).urlUser("userKeep").entityClass(UserCollectionListBean.class).params(params)
                .enqueue(networkParams,tOkHttpResponseListener);
    }

    public void  AddFriend(String token,String friendID,NetworkParams networkParams)
    {
        clearAllParams();
        params.put("token",token);
        params.put("friend_id",friendID);
        new OkHttpBuilder.POST(appCompatActivity).urlUser("add_friend").entityClass(UserCollectionListBean.class).params(params)
                .enqueue(networkParams,tOkHttpResponseListener);
    }

    public void UserConcern(String token,int page,int num,NetworkParams networkParams)
    {
        clearAllParams();
        params.put("token",token);
        params.put("page",""+page);
        params.put("num",""+num);
        new OkHttpBuilder.POST(appCompatActivity).urlUser("userConcern").entityClass(UserConcernListBean.class).params(params)
                .enqueue(networkParams,tOkHttpResponseListener);
    }

    public void GetMessageRead(String token,NetworkParams networkParams)
    {
        clearAllParams();
        params.put("token",token);
        new OkHttpBuilder.POST(appCompatActivity).urlUser("is_read").entityClass(BaseBean.class).params(params)
                .enqueue(networkParams,tOkHttpResponseListener);

    }

    public void UserMessageList(String token,int page,int num,NetworkParams networkParams)
    {
        clearAllParams();
        params.put("token",token);
        params.put("page",""+page);
        params.put("num",""+num);
        new OkHttpBuilder.POST(appCompatActivity).urlUser("my_push_imformation").entityClass(MessageListBean.class).params(params)
                .enqueue(networkParams,tOkHttpResponseListener);
    }

    public void UserMessageDetail(String token,String id,NetworkParams networkParams)
    {
        clearAllParams();
        params.put("token",token);
        params.put("user_push_id",""+id);
        new OkHttpBuilder.POST(appCompatActivity).urlUser("my_push_imformation_detail").entityClass(MessageDetailBean.class).params(params)
                .enqueue(networkParams,tOkHttpResponseListener);
    }

    public void UserBeDesigner(String token,String name,String email,String wechat,String cardID,String city,String myself,String file1,String file2,NetworkParams networkParams)
    {
        clearAllParams();
        params.put("token",token);
        params.put("real_name",name);
        params.put("email",email);
        params.put("wechat",wechat);
        params.put("city",city);
        params.put("id_card",cardID);
        params.put("introduce",myself);
        params.put("name",name);
        List<List<String>> lists = new ArrayList<>();
        List<String> list = new ArrayList<>();
        list.add(file1);
        lists.add(list);
        if(!TextUtils.isEmpty(file2)) {
            List<String> list2 = new ArrayList<>();
            list.add(file2);
            lists.add(list2);
        }
        if(list.size()==2) {
            new OkHttpBuilder.POST(appCompatActivity).urlDesign("become").entityClass(BaseBean.class).params(params, lists, "correct", "opposite")
                    .enqueue(networkParams, tOkHttpResponseListener);
        }
        else {
            new OkHttpBuilder.POST(appCompatActivity).urlDesign("become").entityClass(BaseBean.class).params(params, lists, "correct")
                    .enqueue(networkParams, tOkHttpResponseListener);
        }
    }


    public void DesignerProfile(String token,String userID,NetworkParams networkParams)
    {
        clearAllParams();
        params.put("token",token);
        params.put("user_id",userID);
        new OkHttpBuilder.POST(appCompatActivity).urlDesign("profile").entityClass(DesignerDetailBean.class).params(params)
                .enqueue(networkParams, tOkHttpResponseListener);
    }

    public void GetCheckSubscribe(String token,String designerID,NetworkParams networkParams)
    {
        clearAllParams();
        params.put("token",token);
        params.put("subscribe_id",designerID);
        new OkHttpBuilder.POST(appCompatActivity).urlDesign("getCheckSubscribe").entityClass(BaseBean.class).params(params)
                .enqueue(networkParams, tOkHttpResponseListener);
    }
   public void Collections(String userID,NetworkParams networkParams)
   {
       clearAllParams();
       params.put("user_id",userID);
       new OkHttpBuilder.POST(appCompatActivity).urlDesign("collections").entityClass(AllInspirationBean.class).params(params)
               .enqueue(networkParams, tOkHttpResponseListener);
   }

   public void Subscribe(String token,String subscribe_id,NetworkParams networkParams)
   {
       clearAllParams();
       params.put("token",token);
       params.put("subscribe_id",subscribe_id);
       new OkHttpBuilder.POST(appCompatActivity).urlDesign("subscribe").entityClass(BaseBean.class).params(params)
               .enqueue(networkParams, tOkHttpResponseListener);
   }

    public void ClickLove(String token,String inspiration_id,NetworkParams networkParams)
    {
        clearAllParams();
        params.put("token",""+token);
        params.put("inspiration_id",""+inspiration_id);
        new OkHttpBuilder.POST(appCompatActivity).urlDesign("clickLove").entityClass(BaseBean.class).params(params)
                .enqueue(networkParams, tOkHttpResponseListener);
    }

    public void GetCheckLove(String token,String inspiration_id,NetworkParams networkParams)
    {
        clearAllParams();
        params.put("token",token);
        params.put("inspiration_id",inspiration_id);
        new OkHttpBuilder.POST(appCompatActivity).urlDesign("getCheckLove").entityClass(BaseBean.class).params(params)
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
