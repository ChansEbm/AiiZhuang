package com.appbaba.platform;


import com.appbaba.platform.tools.AppTools;

/**
 * Created by ChanZeeBm on 2015/9/7.
 */
public class AppKeyMap {
    //手机正则
    public final static String PHONE_REGEX = "[1][3578]\\d{9}";
    //身份证正则
    public final static String CITIZEN_ID_REGEX = "(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])";
    //中国人名正则
    public final static String CHINESE_PEOPLE_REGEX = "(([\\u4E00-\\u9FA5]|[·]){2,15})";
    //密码正则(只能输入数字字母)
    public final static String PWD_REGEX = "^[A-Za-z0-9]+$";
    //邮编正则
    public final static String ZIP_CODE_REGEX = "^[1-9]\\d{5}$";
    //case action
    public final static String CASE_ACTION = "android.intent.action.CASE";
    //检测无网络action
    public final static String NO_NETWORK_ACTION = "android.intent.action.NO_NETWORK";
    //默认动画执行时间 300ms
    public final static int DEFAULT_DURATION = 300;

    public final static boolean IS_DEBUG = false;

    //be flag
    public final static int CUPCAKE = 0x01f;
    public final static int DONUT = 0x02f;
    public final static int FROYO = 0x03f;
    public final static int GINGERBREAD = 0x04f;
    public final static int HONEYCOMB = 0x05f;
    public final static int ICECREAMSANDWICH = 0x06f;
    public final static int KITKAT = 0x07f;
    public final static int LOLLIPOP = 0x08f;
    public final static int MARSHMALLOW = 0x09f;

    public final static String BASEURL = "http:/192.168.200.251";
    public final static String HEAD = BASEURL + "/Api/User/";
    public final static String HEAD_API_Inspiration = HEAD + "Inspiration/";
    public final static String HEAD_API_GOODS = HEAD + "Goods/";
    public final static String HEAD_API_LOGIN = HEAD + "Login/";
    public final static String HEAD_API_USER = HEAD + "User/";
    public final static String HEAD_API_DESIGNER = HEAD + "Design/";
    public final static String HEAD_API_PAGE_PRODUCT = BASEURL+"/Api/Mer/Page/goods?goods_id=";
    public final static String HEAD_API_PAGE_INSPIRATION = BASEURL+"/Api/Mer/Page/detail?inspiration_id=";




    public final static String CONTENT_JPG = "image/jpg";
    public final static String CONTENT_PNG = "image/png";
    public final static String CONTENT_TXT = "text/plain";
    public final static String CONTENT_MP3 = "audio/mp3";
    public final static String CONTENT_OCT = "application/octet-stream";



    //用户标识
    public final static String AUTH = "auth";

    public static boolean isAuthEmpty() {
        return AppTools.getStringSharedPreferences(AUTH, "").isEmpty();
    }

}
