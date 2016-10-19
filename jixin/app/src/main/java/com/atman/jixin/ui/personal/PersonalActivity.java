package com.atman.jixin.ui.personal;

import android.content.Context;
import android.content.Intent;
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
import com.base.baselibs.util.PreferenceUtil;
import com.base.baselibs.widget.ShapeImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tbl.okhttputils.OkHttpUtils;

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
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();
        OkHttpUtils.get().url(Common.Url_Personal + userId)
                .headers(MyBaseApplication.getApplication().getHeaderSeting())
                .addHeader("cookie",MyBaseApplication.getApplication().getCookie())
                .tag(Common.NET_PERSONAL_ID).id(Common.NET_PERSONAL_ID).build()
                .execute(new MyStringCallback(mContext, "", this, true));
    }

    @Override
    protected void onResume() {
        super.onResume();
        headImge = PreferenceUtil.getPreferences(mContext, PreferenceUtil.PARM_USER_IMG);
        userId = PreferenceUtil.getPreferences(mContext, PreferenceUtil.PARM_USERID);
        if (!headImge.isEmpty()) {
            ImageLoader.getInstance().displayImage(Common.ImageUrl+headImge, personalHeadImgIv
                    , MyBaseApplication.getApplication().optionsHead);
            personalHeadNameTx.setText(MyBaseApplication.USERINFOR.getBody().getMemberName());
        }
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_PERSONAL_ID) {
            mGetPersonalInformationModel = mGson.fromJson(data, GetPersonalInformationModel.class);
            personalAttentionNumTx.setText(mGetPersonalInformationModel.getBody().getLikeNum()+"");
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
                startActivity(new Intent(mContext, PersonalInformationActivity.class));
                break;
            case R.id.personal_attention_ll:
                break;
            case R.id.personal_setting_ll:
                startActivity(new Intent(mContext, PersonalSettingActivity.class));
                break;
            case R.id.personal_about_and_help_ll:
                startActivity(new Intent(mContext, AboutAndHelpActivity.class));
                break;
        }
    }
}
