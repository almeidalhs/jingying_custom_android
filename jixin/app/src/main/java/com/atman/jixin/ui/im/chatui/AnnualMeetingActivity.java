package com.atman.jixin.ui.im.chatui;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.atman.jixin.R;
import com.atman.jixin.model.response.GetSignInforModel;
import com.atman.jixin.ui.base.MyBaseActivity;
import com.atman.jixin.ui.base.MyBaseApplication;
import com.atman.jixin.utils.Common;
import com.base.baselibs.net.MyStringCallback;
import com.tbl.okhttputils.OkHttpUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Response;

/**
 * Created by tangbingliang on 16/12/16.
 */

public class AnnualMeetingActivity extends MyBaseActivity {

    @Bind(R.id.annualmeeting_name_tx)
    TextView annualmeetingNameTx;
    @Bind(R.id.annualmeeting_table_num_tx)
    TextView annualmeetingTableNumTx;
    @Bind(R.id.annualmeeting_seat_num_tx)
    TextView annualmeetingSeatNumTx;

    private Context mContext = AnnualMeetingActivity.this;
    private GetSignInforModel mGetSignInforModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annualmeeting);
        ButterKnife.bind(this);
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);

        setBarTitleTx("邀请函");
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();
        OkHttpUtils.get().url(Common.Url_Get_SignInfor)
                .headers(MyBaseApplication.getApplication().getHeaderSeting())
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .tag(Common.NET_GET_SIGNINFOR_ID).id(Common.NET_GET_SIGNINFOR_ID).build()
                .execute(new MyStringCallback(mContext, "", this, true));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_GET_SIGNINFOR_ID) {
            mGetSignInforModel = mGson.fromJson(data, GetSignInforModel.class);

            if (mGetSignInforModel.getBody() != null &&
                    mGetSignInforModel.getBody().getRespectCall() != null) {
                annualmeetingNameTx.setText(mGetSignInforModel.getBody().getRespectCall());
                annualmeetingTableNumTx.setText("" + mGetSignInforModel.getBody().getTableNum());
                annualmeetingSeatNumTx.setText("" + mGetSignInforModel.getBody().getSeatNum());
            } else {
                showWraning("数据获取失败!");
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_SIGNINFOR_ID);
    }
}
