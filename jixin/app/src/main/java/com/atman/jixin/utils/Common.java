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
    /**************************http访问路径*****************************/



    /**************************http访问回应识别码*****************************/
    //登录
    public static int NET_LOGIN_ID = 1;
    /**************************http访问回应识别码*****************************/



    /**************************跳转码*****************************/
    public static int TO_LOGIN = 10000;
    public static int TO_REGISTER = 10001;
    /**************************跳转码*****************************/
}
