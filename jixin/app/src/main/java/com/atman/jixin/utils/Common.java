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
    public static String hostUrl = "http://192.168.1.141:8089/";
    public static String ImageUrl = "http://192.168.1.141:8000/by/";

//    public static String hostUrl = "http://www.jiplaza.net/";
//    public static String ImageUrl = "http://www.jiplaza.net:8000/by";
    /**************************http基础访问路径*****************************/



    /**************************http访问路径*****************************/
    //登录
    public static String Url_Login = hostUrl + "baiye/login";
    //个人
    public static String Url_Personal = hostUrl + "baiye/rest/custom/index/";
    //退出
    public static String Url_Logout = hostUrl + "baiye/rest/user/logout";
    //个人信息维护
    public static String Url_Manage = hostUrl + "baiye/rest/custom/manage";
    //发送验证码
    public static final String Url_FORGOT = hostUrl + "baiye/rest/checkcode/mobile/";
    public static final String SEED_MEESAGE_AFTER_FORGOT = "?type=";
    //重置密码
    public static String Url_ResetPWD = hostUrl + "baiye/rest/user/forgotpwd/";
    //注册
    public static String Url_Register = hostUrl + "baiye/rest/user";
    //意见反馈
    public static String Url_Feedback = hostUrl + "baiye/rest/ad/feedback";
    //修改密码
    public static String Url_Reset_PassWord = hostUrl + "baiye/rest/user/password/";
    //上传头像
    public static final String Url_HeadImg = hostUrl + "upload?uploadType=avatar";
    //更新头像
    public static final String Url_Modify_HeadImg = hostUrl + "baiye/rest/user/avatar";
    //扫描二维码
    public static final String Url_QRCode = hostUrl + "baiye/rest/custom/qrcode";
    //获取关注列表
    public static final String Url_GetLike_List = hostUrl + "baiye/rest/favor/likelist";
    //获取会员中心
    public static final String Url_MemberCenter = hostUrl + "baiye/rest/custom/memcenter/";
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
    //注册
    public static int NET_REGISTER_ID = 8;
    //意见反馈
    public static int NET_FEEDBACK_ID = 9;
    //修改密码
    public static int NET_RESET_PASSWORD_ID = 10;
    //上传头像
    public static int NET_HEADIMG_ID = 11;
    //更新头像
    public static int NET_MODFY_HEADIMG_ID = 12;
    //扫描二维码
    public static int NET_QR_CODE_ID = 13;
    //获取关注列表
    public static int NET_GETLIKE_LIST_ID = 14;
    //获取会员中心
    public static int NET_MEMBER_CENTRER = 15;
    /**************************http访问回应识别码*****************************/



    /**************************跳转码*****************************/
    public static int TO_LOGIN = 10000;
    public static int TO_REGISTER = 10001;
    public static int TO_RESETPW = 10002;
    public static int TO_PERSONALINFOR = 10004;
    public static int TO_CODESCAN = 10005;
    /**************************跳转码*****************************/
}
