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
    //获取积分兑换商品
    public static final String Url_Integral_Exchange = hostUrl + "baiye/rest/integral/";
    //积分兑换商品
    public static final String Url_Exchange = hostUrl + "baiye/rest/integral/exchange";
    //获取商品详情
    public static final String Url_Get_GoodsDetail = hostUrl + "baiye/rest/goods/";
    //获取商品兑换记录
    public static final String Url_Get_ExchangeRecord = hostUrl + "baiye/rest/integral/exchangelog/";
    //获取商家聊天服务信息
    public static final String Url_Get_UserChat = hostUrl + "baiye/rest/store/userchat/";
    //发送信息
    public static final String Url_Seed_UserChat = hostUrl + "baiye/rest/chat/send";
    //上传图片
    public static final String Url_Up_File = hostUrl + "upload?uploadType=";
    //上传个推用户id (1是商家, 0是买家)
    public static final String Url_Update_ClienId = hostUrl + "baiye/rest/user/getui/0/";
    //获取商家商品分类
    public static final String Url_Get_Store_GoodsClass = hostUrl + "baiye/rest/store/goodsclass/";
    //获取商家商品分类下的商品
    public static final String Url_Get_Store_Goods_By_Id = hostUrl + "baiye/rest/goods/store/";
    //获取商家介绍
    public static final String Url_Get_Store_Introduction = hostUrl + "baiye/rest/business/detail/";
    //获取评论
    public static final String Url_Get_Comment = hostUrl + "baiye/rest/comment/getcomments/";
    //评论点赞
    public static final String Url_Comment_Like = hostUrl + "baiye/rest/comment/like/";
    //添加评论
    public static final String Url_Add_Comment = hostUrl + "baiye/rest/comment";
    //获取店铺信息
    public static final String Url_Get_StoreDetail = hostUrl + "baiye/rest/store/userchat/";
    //获取所有兑换记录
    public static final String Url_Get_All_ExchangeRecord = hostUrl + "baiye/rest/integral/allexchangelog/";
    //删除兑换记录
    public static final String Url_Delete_ExchangeRecord = hostUrl + "baiye/rest/integral/manage";
    //删除关注
    public static final String Url_Delete_Attention = hostUrl + "baiye/rest/favor/cancel/";
    //检查版本更新
    public static String Url_Get_Version = hostUrl + "baiye/rest/common/androidjyneedUpdate";
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
    public static int NET_MEMBER_CENTRER_ID = 15;
    //获取积分兑换商品
    public static int NET_Integral_EXCHANGE_ID = 16;
    //积分兑换商品
    public static int NET_EXCHANGE_ID = 17;
    //获取商品详情
    public static int NET_GET_GOODSDETAI_ID = 18;
    //获取商品兑换记录
    public static int NET_GET_GOODS_EXCHANGERECORD_ID = 19;
    //获取商家聊天服务信息
    public static int NET_GET_USERCHAT_ID = 20;
    //发送信息
    public static int NET_SEED_USERCHAT_ID = 21;
    //上传图片
    public static int NET_UP_PIC_ID = 22;
    //上传音频
    public static int NET_UP_AUDIO_ID = 23;
    //上传个推用户id (1是商家, 0是买家)
    public static int NET_UP_GETTUI_ID = 24;
    //获取商家商品分类
    public static int NET_GET_STORE_GOODSCLASS_ID = 25;
    //获取商家商品分类下的商品
    public static int NET_GET_STORE_GOODS_BY_ID_ID = 26;
    //获取商家介绍
    public static int NET_GET_STORE_INRRODUCTION_ID = 27;
    //获取评论
    public static int NET_GET_COMMENT_ID = 28;
    //评论点赞
    public static int NET_COMMENT_LIKE_ID = 29;
    //添加评论
    public static int NET_ADD_COMMENT_ID = 30;
    //获取店铺信息
    public static int NET_GET_STOREDETAIL_ID = 31;
    //删除兑换记录
    public static int NET_DELETE_EXHANGE_ID = 32;
    //删除关注
    public static int NET_DELETE_ATTENTION_ID = 33;
    //检查版本更新
    public static int NET_GET_VERSION_ID = 34;
    /**************************http访问回应识别码*****************************/



    /**************************跳转码*****************************/
    public static int TO_LOGIN = 10000;
    public static int TO_REGISTER = 10001;
    public static int TO_RESETPW = 10002;
    public static int TO_PERSONALINFOR = 10004;
    public static int TO_CODESCAN = 10005;
    public static int TO_RECORDE = 10006;
    /**************************跳转码*****************************/
}
