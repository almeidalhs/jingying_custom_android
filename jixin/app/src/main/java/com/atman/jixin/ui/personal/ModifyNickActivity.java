package com.atman.jixin.ui.personal;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.atman.jixin.R;
import com.atman.jixin.model.response.LoginResultModel;
import com.atman.jixin.ui.base.MyBaseActivity;
import com.atman.jixin.ui.base.MyBaseApplication;
import com.atman.jixin.utils.Common;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.LogUtils;
import com.base.baselibs.widget.MyCleanEditText;
import com.tbl.okhttputils.OkHttpUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Response;

/**
 * Created by tangbingliang on 16/10/19.
 */

public class ModifyNickActivity extends MyBaseActivity {

    @Bind(R.id.modifynick_name_et)
    MyCleanEditText modifynickNameEt;

    private Context mContext = ModifyNickActivity.this;
    private  String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifynick);
        ButterKnife.bind(this);
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);

        setBarTitleTx("修改昵称");
        setBarRightTx("完成").setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str = modifynickNameEt.getText().toString().trim();
                if (str.isEmpty()) {
                    showToast("请输入昵称");
                    return;
                }
                Map<String, String> p = new HashMap<>();
                p.put("memberName", str);
                OkHttpUtils.postString().url(Common.Url_Manage).tag(Common.NET_MANAGE_ID)
                        .id(Common.NET_MANAGE_ID).content(mGson.toJson(p))
                        .mediaType(Common.JSON)
                        .headers(MyBaseApplication.getApplication().getHeaderSeting())
                        .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                        .build().execute(new MyStringCallback(mContext, "", ModifyNickActivity.this, true));
            }
        });

        modifynickNameEt.setText(MyBaseApplication.USERINFOR.getBody().getMemberName());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_MANAGE_ID) {
            LoginResultModel mLoginResultModel = mGson.fromJson(data, LoginResultModel.class);
            MyBaseApplication.USERINFOR = mLoginResultModel;
            showToast("昵称修改成功");
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
