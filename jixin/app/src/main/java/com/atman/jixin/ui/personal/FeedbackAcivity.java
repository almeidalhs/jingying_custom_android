package com.atman.jixin.ui.personal;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.atman.jixin.R;
import com.atman.jixin.ui.base.MyBaseActivity;
import com.atman.jixin.ui.base.MyBaseApplication;
import com.atman.jixin.utils.Common;
import com.base.baselibs.net.BaseNormalModel;
import com.base.baselibs.net.MyStringCallback;
import com.tbl.okhttputils.OkHttpUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Response;

/**
 * Created by tangbingliang on 16/10/18.
 */

public class FeedbackAcivity extends MyBaseActivity {

    @Bind(R.id.feedback_sug_conent_et)
    EditText feedbackSugConentEt;

    private Context mContext = FeedbackAcivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acivity_feedback);
        ButterKnife.bind(this);
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        setBarTitleTx("意见反馈");
        setBarRightIv(R.mipmap.nav_btn_icon_right_ok)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String str = feedbackSugConentEt.getText().toString().trim();
                        if (str.isEmpty()) {
                            showToast("请输入意见内容");
                            return;
                        }
                        Map<String, Object> p = new HashMap<>();
                        p.put("content", str);
                        OkHttpUtils.postString().url(Common.Url_Feedback)
                                .content(mGson.toJson(p)).mediaType(Common.JSON)
                                .headers(MyBaseApplication.getApplication().getHeaderSeting())
                                .addHeader("cookie",MyBaseApplication.getApplication().getCookie())
                                .tag(Common.NET_FEEDBACK_ID).id(Common.NET_FEEDBACK_ID).build()
                                .execute(new MyStringCallback(mContext, "", FeedbackAcivity.this, true));
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_FEEDBACK_ID) {
            BaseNormalModel temp = mGson.fromJson(data, BaseNormalModel.class);
            if (temp.getResult().equals("1")) {
                showToast("你的意见已发送成功，谢谢您的宝贵意见");
            }
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_FEEDBACK_ID);
    }
}
