package com.atman.jixin.ui.personal;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atman.jixin.R;
import com.atman.jixin.model.response.GetPersonalInformationModel;
import com.atman.jixin.ui.base.MyBaseActivity;
import com.atman.jixin.ui.base.MyBaseApplication;
import com.atman.jixin.ui.shop.ExchangeRecordActivity;
import com.atman.jixin.utils.Common;
import com.atman.jixin.widget.GroundGlassUtil;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.PreferenceUtil;
import com.base.baselibs.widget.ShapeImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.tbl.okhttputils.OkHttpUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;

/**
 * Created by tangbingliang on 16/10/18.
 */

public class PersonalActivity extends MyBaseActivity {

    @Bind(R.id.personal_head_bg)
    ImageView personalHeadBg;
    @Bind(R.id.personal_head_befor_bg)
    ImageView personalHeadBeforBg;
    @Bind(R.id.personal_head_img_iv)
    ShapeImageView personalHeadImgIv;
    @Bind(R.id.personal_head_name_tx)
    TextView personalHeadNameTx;
    @Bind(R.id.personal_head_ll)
    RelativeLayout personalHeadLl;
    @Bind(R.id.personal_information_ll)
    LinearLayout personalInformationLl;
    @Bind(R.id.personal_attention_num_tx)
    TextView personalAttentionNumTx;
    @Bind(R.id.personal_attention_ll)
    LinearLayout personalAttentionLl;
    @Bind(R.id.personal_exchange_ll)
    LinearLayout personalExchangeLl;
    @Bind(R.id.personal_setting_ll)
    LinearLayout personalSettingLl;
    @Bind(R.id.personal_about_and_help_ll)
    LinearLayout personalAboutAndHelpLl;
    @Bind(R.id.personal_consumption_tx)
    TextView personalConsumptionTx;
    @Bind(R.id.personal_card_tx)
    TextView personalCardTx;

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
        setBarTitleTx("我");

        headImge = PreferenceUtil.getPreferences(mContext, PreferenceUtil.PARM_USER_IMG);
        userId = PreferenceUtil.getPreferences(mContext, PreferenceUtil.PARM_USERID);
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();
    }

    @Override
    protected void onResume() {
        super.onResume();
        headImge = PreferenceUtil.getPreferences(mContext, PreferenceUtil.PARM_USER_IMG);
        userId = PreferenceUtil.getPreferences(mContext, PreferenceUtil.PARM_USERID);
        if (!headImge.isEmpty()) {
            ImageLoader.getInstance().displayImage(Common.ImageUrl + headImge, personalHeadImgIv
                    , MyBaseApplication.getApplication().optionsHead);
            ImageLoader.getInstance().displayImage(Common.ImageUrl + headImge, personalHeadBg
                    , MyBaseApplication.getApplication().optionsNot, mListener);
            personalHeadNameTx.setText(MyBaseApplication.USERINFOR.getBody().getMemberName());
        }

        OkHttpUtils.get().url(Common.Url_Personal + userId)
                .headers(MyBaseApplication.getApplication().getHeaderSeting())
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .tag(Common.NET_PERSONAL_ID).id(Common.NET_PERSONAL_ID).build()
                .execute(new MyStringCallback(mContext, "", this, false));
    }

    private ImageLoadingListener mListener = new ImageLoadingListener() {

        @Override
        public void onLoadingStarted(String s, View view) {

        }

        @Override
        public void onLoadingFailed(String s, View view, FailReason failReason) {

        }

        @Override
        public void onLoadingComplete(String s, View view, Bitmap bitmap) {
//            bitmap = GroundGlassUtil.drawable2Bitmap(getResources().getDrawable(R.mipmap.ic_launcher));
            ImageView im = (ImageView) view;
//            if (Build.VERSION.SDK_INT > 16) {
//                im.setImageBitmap(GroundGlassUtil.groundGlass17(mContext, bitmap, 25));
//            } else {
                im.setImageBitmap(GroundGlassUtil.groundGlass16(bitmap, 25));
//            }
        }

        @Override
        public void onLoadingCancelled(String s, View view) {

        }
    };

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_PERSONAL_ID) {
            mGetPersonalInformationModel = mGson.fromJson(data, GetPersonalInformationModel.class);
            personalAttentionNumTx.setText(mGetPersonalInformationModel.getBody().getLikeNum() + "");
            personalConsumptionTx.setText(mGetPersonalInformationModel.getBody().getConsumeNum() + "");
            personalCardTx.setText(mGetPersonalInformationModel.getBody().getCouponNum() + "");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_PERSONAL_ID);
    }

    @OnClick({R.id.personal_information_ll, R.id.personal_attention_ll, R.id.personal_setting_ll
            , R.id.personal_about_and_help_ll, R.id.personal_exchange_ll, R.id.personal_consumption_ll
            , R.id.personal_card_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.personal_card_ll:
                startActivity(new Intent(mContext, MyCardActivity.class));
                break;
            case R.id.personal_consumption_ll:
                startActivity(new Intent(mContext, MyConsumptionRecordActivity.class));
                break;
            case R.id.personal_information_ll:
                startActivity(new Intent(mContext, PersonalInformationActivity.class));
                break;
            case R.id.personal_attention_ll:
                startActivity(new Intent(mContext, MyAttentionListActivity.class));
                break;
            case R.id.personal_setting_ll:
                startActivity(new Intent(mContext, PersonalSettingActivity.class));
                break;
            case R.id.personal_about_and_help_ll:
                startActivity(new Intent(mContext, AboutAndHelpActivity.class));
                break;
            case R.id.personal_exchange_ll:
                startActivity(ExchangeRecordActivity.buildIntent(mContext, -1, "积分兑换记录"));
                break;
        }
    }
}
