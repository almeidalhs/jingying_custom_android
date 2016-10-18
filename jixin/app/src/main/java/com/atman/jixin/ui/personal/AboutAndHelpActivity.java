package com.atman.jixin.ui.personal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atman.jixin.R;
import com.atman.jixin.ui.base.MyBaseActivity;
import com.atman.jixin.ui.base.MyBaseApplication;
import com.base.baselibs.widget.ShapeImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;

/**
 * Created by tangbingliang on 16/10/18.
 */

public class AboutAndHelpActivity extends MyBaseActivity {

    @Bind(R.id.personal_head_img_iv)
    ShapeImageView personalHeadImgIv;
    @Bind(R.id.abouthelp_head_version_tx)
    TextView abouthelpHeadVersionTx;
    @Bind(R.id.abouthelp_head_ll)
    LinearLayout abouthelpHeadLl;
    @Bind(R.id.abouthelp_agreement_ll)
    LinearLayout abouthelpAgreementLl;
    @Bind(R.id.abouthelp_feedback_ll)
    LinearLayout abouthelpFeedbackLl;
    @Bind(R.id.abouthelp_service_ll)
    LinearLayout abouthelpServiceLl;

    private Context mContext = AboutAndHelpActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutandhelp);
        ButterKnife.bind(this);
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);

        setBarTitleTx("关于&帮助");
        abouthelpHeadVersionTx.setText("即应 "+ MyBaseApplication.VERSION);
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.abouthelp_agreement_ll, R.id.abouthelp_feedback_ll, R.id.abouthelp_service_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.abouthelp_agreement_ll:
                startActivity(new Intent(mContext, UserAgreementActivity.class));
                break;
            case R.id.abouthelp_feedback_ll:
                startActivity(new Intent(mContext, FeedbackAcivity.class));
                break;
            case R.id.abouthelp_service_ll:
                toPhone(mContext, "4008205310");
                break;
        }
    }
}
