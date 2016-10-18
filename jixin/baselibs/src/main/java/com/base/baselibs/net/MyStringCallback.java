package com.base.baselibs.net;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import com.base.baselibs.base.BaseAppCompatActivity;
import com.base.baselibs.util.CustomToast;
import com.base.baselibs.util.LogUtils;
import com.base.baselibs.util.PreferenceUtil;
import com.base.baselibs.widget.PromptDialog;
import com.google.gson.Gson;
import com.tbl.okhttputils.callback.StringCallback;
import com.tbl.okhttputils.utils.L;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/6/30 15:42
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class MyStringCallback extends StringCallback {

    private httpCallBack CallBack;
    private Context mContext;
    private boolean isShowLoading;
    private boolean isCancelable = true;
    private String dialogStr;

    public MyStringCallback(){}

    public MyStringCallback(Context mContext, String dialogStr, httpCallBack CallBack, boolean isShowLoading){
        this.CallBack = CallBack;
        this.mContext = mContext;
        this.dialogStr = dialogStr;
        this.isShowLoading = isShowLoading;
    }

    public MyStringCallback(Context mContext, String dialogStr, httpCallBack CallBack
            , boolean isShowLoading, boolean isCancelable){
        this.CallBack = CallBack;
        this.mContext = mContext;
        this.dialogStr = dialogStr;
        this.isCancelable = isCancelable;
        this.isShowLoading = isShowLoading;
    }

    @Override
    public void onBefore(Request request, int id) {
        super.onBefore(request, id);
        if (mContext!=null && isShowLoading) {
            ((BaseAppCompatActivity)mContext).showLoading(dialogStr, isCancelable);
        }
        CallBack.onBefore(request, id);
    }

    @Override
    public void onAfter(int id) {
        super.onAfter(id);
        CallBack.onAfter(id);
    }

    @Override
    public void onError(Call call, Exception e, int code, int id) {
        LogUtils.e("e:"+e.toString()+",id:"+id);
        if (e.toString().contains("Canceled") || e.toString().contains("Socket closed")) {
            return;
        }

        if (e.toString().contains("timeout") || e.toString().contains("java.net.SocketTimeoutException")
                || e.toString().contains("Failed to connect")) {
            CallBack.onError(call, new IOException("访问超时，请稍后再试..."), code, id);
        } else {
            CallBack.onError(call, e, code, id);
        }
    }

    @Override
    public void onResponse(String data, Response response, int id) {
        Headers s = response.headers();
        List<String> list = s.values("Set-Cookie");
        for (int i=0;i<list.size();i++) {
            if (list.get(i).split(";")[0].split(":")[0].split("=")[0].equals(PreferenceUtil.PARM_USER_KEY)) {
                PreferenceUtil.savePreference(mContext, PreferenceUtil.PARM_USER_KEY, list.get(i).split(";")[0].split(":")[0].split("=")[1]);
            } else {
                PreferenceUtil.savePreference(mContext, PreferenceUtil.PARM_USER_TOKEN, list.get(i).split(";")[0].split(":")[0].split("=")[1]);
            }
        }

        String PARM_USER_KEY = PreferenceUtil.getPreferences(mContext, PreferenceUtil.PARM_USER_KEY);
        String PARM_USER_TOKEN = PreferenceUtil.getPreferences(mContext, PreferenceUtil.PARM_USER_TOKEN);
//        Log.e(">>>","PARM_USER_KEY:"+PARM_USER_KEY+",PARM_USER_TOKEN:"+PARM_USER_TOKEN);
        Log.e(">>>","<<"+id+">>data:"+data);
        BaseNormalModel mBaseModel = new Gson().fromJson(data, BaseNormalModel.class);
        if (mBaseModel != null && mBaseModel.getResult() != null && mBaseModel.getResult().equals("1")) {
            CallBack.onStringResponse(data, response, id);
        } else {
            BaseErrorTwoModel mBaseErrorTwoModel = new Gson().fromJson(data, BaseErrorTwoModel.class);
            if (mBaseErrorTwoModel != null && mBaseErrorTwoModel.getBody()!=null) {
                CallBack.onError(null, new IOException(mBaseErrorTwoModel.getBody().getMessage()), 200, id);
            } else {
                if (id == 3) {
                    CallBack.onStringResponse(data, response, id);
                } else {
                    CallBack.onError(null, new IOException("新的错误数据结构"), 200, id);
                }
//                BaseErrorModel mBaseErrorModel = new Gson().fromJson(data, BaseErrorModel.class);
//                if (mBaseErrorModel != null && mBaseErrorModel.getError_code()!=null) {
//                    CallBack.onError(null, new IOException(mBaseErrorModel.getError_description()), 200, id);
//                } else {
//                    CallBack.onStringResponse(data, response, id);
//                }
            }
        }
    }
}
