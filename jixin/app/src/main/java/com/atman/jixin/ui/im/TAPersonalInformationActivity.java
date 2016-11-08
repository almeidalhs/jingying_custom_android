package com.atman.jixin.ui.im;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atman.jixin.R;
import com.atman.jixin.model.response.GetPersonalInformationModel;
import com.atman.jixin.ui.base.MyBaseActivity;
import com.atman.jixin.ui.base.MyBaseApplication;
import com.atman.jixin.utils.Common;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.widget.ShapeImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tbl.okhttputils.OkHttpUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;

/**
 * Created by tangbingliang on 16/10/25.
 */

public class TAPersonalInformationActivity extends MyBaseActivity {

    @Bind(R.id.tapersional_head_bg)
    ImageView tapersionalHeadBg;
    @Bind(R.id.tapersional_head_befor_bg)
    ImageView tapersionalHeadBeforBg;
    @Bind(R.id.tapersional_head_iv)
    ShapeImageView tapersionalHeadIv;
    @Bind(R.id.tapersional_head_name_tx)
    TextView tapersionalHeadNameTx;
    @Bind(R.id.tapersional_head_gander_tx)
    TextView tapersionalHeadGanderTx;
    @Bind(R.id.tapersional_head_sign_tx)
    TextView tapersionalHeadSignTx;
    @Bind(R.id.tapersional_display_tx)
    TextView tapersionalDisplayTx;
    @Bind(R.id.tapersional_job_tx)
    TextView tapersionalJobTx;
    @Bind(R.id.tapersional_hobby_tx)
    TextView tapersionalHobbyTx;

    private Context mContext = TAPersonalInformationActivity.this;
    private long persionId;
    private boolean isPersonalIM;

    private GetPersonalInformationModel mGetPersonalInformationModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tapersonalinformation);
        ButterKnife.bind(this);
    }

    public static Intent buildIntent(Context context, long persionId) {
        Intent intent = new Intent(context, TAPersonalInformationActivity.class);
        intent.putExtra("persionId", persionId);
        return intent;
    }
    public static Intent buildIntent(Context context, long persionId, boolean isPersonalIM) {
        Intent intent = new Intent(context, TAPersonalInformationActivity.class);
        intent.putExtra("persionId", persionId);
        intent.putExtra("isPersonalIM", isPersonalIM);
        return intent;
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);

        setBarTitleTx("TA的资料");

        persionId = getIntent().getLongExtra("persionId", -1);
        isPersonalIM = getIntent().getBooleanExtra("isPersonalIM", false);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(getmWidth(),
                getmWidth() * 215 / 415);
        tapersionalHeadBg.setLayoutParams(params);
        tapersionalHeadBeforBg.setLayoutParams(params);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();
        OkHttpUtils.get().url(Common.Url_Personal + persionId)
                .headers(MyBaseApplication.getApplication().getHeaderSeting())
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .tag(Common.NET_PERSONAL_ID).id(Common.NET_PERSONAL_ID).build()
                .execute(new MyStringCallback(mContext, "", this, true));
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_PERSONAL_ID) {
            mGetPersonalInformationModel = mGson.fromJson(data, GetPersonalInformationModel.class);

            ImageLoader.getInstance().displayImage(Common.ImageUrl + mGetPersonalInformationModel.getBody().getMemberAvatar()
                    , tapersionalHeadBg, MyBaseApplication.getApplication().optionsHead);
            ImageLoader.getInstance().displayImage(Common.ImageUrl + mGetPersonalInformationModel.getBody().getMemberAvatar()
                    , tapersionalHeadIv, MyBaseApplication.getApplication().optionsHead);
            tapersionalHeadNameTx.setText(mGetPersonalInformationModel.getBody().getMemberName());
            if (mGetPersonalInformationModel.getBody().getAroundSite()!=null
                    && !mGetPersonalInformationModel.getBody().getAroundSite().isEmpty()) {
                tapersionalDisplayTx.setText(mGetPersonalInformationModel.getBody().getAroundSite());
            }
            if (mGetPersonalInformationModel.getBody().getJob()!=null
                    && !mGetPersonalInformationModel.getBody().getJob().isEmpty()) {
                tapersionalJobTx.setText(mGetPersonalInformationModel.getBody().getJob());
            }
            if (mGetPersonalInformationModel.getBody().getInterest()!=null
                    && !mGetPersonalInformationModel.getBody().getInterest().isEmpty()) {
                tapersionalHobbyTx.setText(mGetPersonalInformationModel.getBody().getInterest());
            }
            if (mGetPersonalInformationModel.getBody().getMemberSign()!=null
                    && !mGetPersonalInformationModel.getBody().getMemberSign().isEmpty()) {
                tapersionalHeadSignTx.setText(mGetPersonalInformationModel.getBody().getMemberSign());
            } else {
                tapersionalHeadSignTx.setText("这家伙很忙,还未来得及设置签名!");
            }
            if (mGetPersonalInformationModel.getBody().getMemberSex()==0) {
                tapersionalHeadGanderTx.setText("女");
            } else {
                tapersionalHeadGanderTx.setText("男");
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_PERSONAL_ID);
    }

    @OnClick({R.id.tapersional_tochat_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tapersional_tochat_bt:
                if (isPersonalIM) {
                    finish();
                    return;
                }
                startActivity(PersonalIMActivity.buildIntent(mContext, persionId
                        , mGetPersonalInformationModel.getBody().getMemberName()
                        , mGetPersonalInformationModel.getBody().getMemberAvatar()));
                break;
        }
    }
}
