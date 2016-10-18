package com.atman.jixin.ui.personal;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atman.jixin.R;
import com.atman.jixin.model.response.GetPersonalInformationModel;
import com.atman.jixin.ui.base.MyBaseActivity;
import com.atman.jixin.ui.base.MyBaseApplication;
import com.atman.jixin.utils.Common;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.LogUtils;
import com.base.baselibs.util.PreferenceUtil;
import com.base.baselibs.widget.ShapeImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tbl.okhttputils.OkHttpUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;

/**
 * Created by tangbingliang on 16/10/18.
 */

public class PersonalActivity extends MyBaseActivity {

    @Bind(R.id.personal_head_img_iv)
    ShapeImageView personalHeadImgIv;
    @Bind(R.id.personal_head_name_tx)
    TextView personalHeadNameTx;
    @Bind(R.id.personal_head_ll)
    LinearLayout personalHeadLl;
    @Bind(R.id.personal_information_ll)
    LinearLayout personalInformationLl;
    @Bind(R.id.personal_attention_num_tx)
    TextView personalAttentionNumTx;
    @Bind(R.id.personal_attention_ll)
    LinearLayout personalAttentionLl;
    @Bind(R.id.personal_setting_ll)
    LinearLayout personalSettingLl;
    @Bind(R.id.personal_about_and_help_ll)
    LinearLayout personalAboutAndHelpLl;

    private Context mContext = PersonalActivity.this;
    private String headImge = "";
    private String userId = "";

    private GetPersonalInformationModel mGetPersonalInformationModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        ButterKnife.bind(this);
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        setBarTitleTx("个人");

        headImge = PreferenceUtil.getPreferences(mContext, PreferenceUtil.PARM_USER_IMG);
        userId = PreferenceUtil.getPreferences(mContext, PreferenceUtil.PARM_USERID);
        if (!headImge.isEmpty()) {
            ImageLoader.getInstance().displayImage(Common.ImageUrl+headImge, personalHeadImgIv
                    , MyBaseApplication.getApplication().optionsHead);
            personalHeadNameTx.setText(MyBaseApplication.USERINFOR.getBody().getMemberName());
        }
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();
        LogUtils.e("url:"+(Common.Url_Personal + userId));
        LogUtils.e("MyBaseApplication.getApplication().getCookie():"
                +MyBaseApplication.getApplication().getCookie());
        String key = PreferenceUtil.getPreferences(getApplicationContext(), PreferenceUtil.PARM_USER_KEY);
        String USER_TOKEN = PreferenceUtil.getPreferences(getApplicationContext(), PreferenceUtil.PARM_USER_KEY);
        Map<String, String> m = new HashMap<>();
        m.put("USER_KEY", key);
        m.put("USER_TOKEN", USER_TOKEN);
        OkHttpUtils.get().url(Common.Url_Personal + userId)
                .addHeader("cookie",MyBaseApplication.getApplication().getCookie())
                .tag(Common.NET_PERSONAL_ID).id(Common.NET_PERSONAL_ID).build()
                .execute(new MyStringCallback(mContext, "", this, true));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_PERSONAL_ID) {

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_PERSONAL_ID);
    }

    @OnClick({R.id.personal_information_ll, R.id.personal_attention_ll, R.id.personal_setting_ll
            , R.id.personal_about_and_help_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.personal_information_ll:
                break;
            case R.id.personal_attention_ll:
                break;
            case R.id.personal_setting_ll:
                break;
            case R.id.personal_about_and_help_ll:
                break;
        }
    }
}
