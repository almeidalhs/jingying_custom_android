package com.atman.jixin.utils;

import okhttp3.MediaType;
import okhttp3.internal.framed.PushObserver;

/**
 * Created by tangbingliang on 16/10/14.
 */

public class Common {
    /**************************http通用设置*****************************/
    public static int timeOut = 15000;
    public static int timeOutTwo = 120000;
    public static MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static String ContentType = "application/json; charset=utf-8";
    /**************************http通用设置*****************************/



    /**************************http基础访问路径*****************************/
    public static String hostUrl = "http://192.168.1.141:8089/baiye/";
    public static String ImageUrl = "http://192.168.1.141:8000/by/";

//    public static String hostUrl = "http://www.jiplaza.net/";
//    public static String ImageUrl = "http://www.jiplaza.net:8000/by";
    /**************************http基础访问路径*****************************/



    /**************************http访问路径*****************************/
    //登录
    public static String Url_Login = hostUrl + "login";
    //个人
    public static String Url_Personal = hostUrl + "rest/custom/index/";
    //退出
    public static String Url_Logout = hostUrl + "rest/user/logout";
    //个人信息维护
    public static String Url_Manage = hostUrl + "rest/custom/manage";
    //发送验证码
    public static final String Url_FORGOT = hostUrl + "rest/checkcode/mobile/";
    public static final String SEED_MEESAGE_AFTER_FORGOT = "?type=0";
    //重置密码
    public static String Url_ResetPWD = hostUrl + "rest/user/forgotpwd/";
    /**************************http访问路径*****************************/



    /**************************http访问回应识别码*****************************/
    //登录
    public static int NET_LOGIN_ID = 1;
    //个人
    public static int NET_PERSONAL_ID = 2;
    //退出
    public static int NET_LOGOUT_ID = 3;
    //个人信息维护
    public static int NET_MANAGE_ID = 4;
    //发送验证码
    public static int NET_SMS_ID = 5;
    //验证验证码
    public static int NET_CHECKCODE_ID = 6;
    //重置密码
    public static int NET_RESETPWD_ID = 7;
    /**************************http访问回应识别码*****************************/



    /**************************跳转码*****************************/
    public static int TO_LOGIN = 10000;
    public static int TO_REGISTER = 10001;
    /**************************跳转码*****************************/
}
