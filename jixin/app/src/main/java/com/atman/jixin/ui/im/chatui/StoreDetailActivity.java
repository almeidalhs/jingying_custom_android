package com.atman.jixin.ui.im.chatui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atman.jixin.R;
import com.atman.jixin.model.response.StoreInformationModel;
import com.atman.jixin.ui.base.MyBaseActivity;
import com.atman.jixin.ui.base.MyBaseApplication;
import com.atman.jixin.ui.im.ShopIMActivity;
import com.atman.jixin.ui.shop.MemberCenterActivity;
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
 * Created by tangbingliang on 16/11/1.
 */

public class StoreDetailActivity extends MyBaseActivity {

    @Bind(R.id.storedetail_head_bg)
    ImageView storedetailHeadBg;
    @Bind(R.id.storedetail_head_iv)
    ShapeImageView storedetailHeadIv;
    @Bind(R.id.storedetail_head_name_tx)
    TextView storedetailHeadNameTx;
    @Bind(R.id.storedetail_head_integral_tx)
    TextView storedetailHeadIntegralTx;
    @Bind(R.id.storedetail_head_rl)
    RelativeLayout storedetailHeadRl;
    @Bind(R.id.storedetail_addr_tx)
    TextView storedetailAddrTx;
    @Bind(R.id.storedetail_phone_tx)
    TextView storedetailPhoneTx;
    @Bind(R.id.storedetail_worktime_tx)
    TextView storedetailWorktimeTx;
    @Bind(R.id.storedetail_member_ll)
    LinearLayout storedetailMemberLl;

    private Context mContext = StoreDetailActivity.this;
    private long storeId;
    private int formId;
    private StoreInformationModel mStoreInformationModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storedetail);
        ButterKnife.bind(this);
    }

    public static Intent buildIntent(Context context, long storeId, int formId) {
        Intent intent = new Intent(context, StoreDetailActivity.class);
        intent.putExtra("storeId", storeId);
        intent.putExtra("formId", formId);
        return intent;
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);

        storeId = getIntent().getLongExtra("storeId", -1);
        formId = getIntent().getIntExtra("formId", 1);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(getmWidth(),
                getmWidth() * 215 / 415);
        storedetailHeadRl.setLayoutParams(params);

        setBarTitleTx("商家信息");
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();
        OkHttpUtils.get().url(Common.Url_Get_StoreDetail + storeId)
                .headers(MyBaseApplication.getApplication().getHeaderSeting())
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .tag(Common.NET_GET_STOREDETAIL_ID).id(Common.NET_GET_STOREDETAIL_ID).build()
                .execute(new MyStringCallback(mContext, "", this, true));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_GET_STOREDETAIL_ID) {
            mStoreInformationModel = mGson.fromJson(data, StoreInformationModel.class);

            ImageLoader.getInstance().displayImage(Common.ImageUrl + mStoreInformationModel.getBody().getStoreAvatar()
                    , storedetailHeadIv, MyBaseApplication.getApplication().optionsHead);
            String url = mStoreInformationModel.getBody().getFullStoreBanner();
            if (!url.startsWith("http")) {
                url = Common.ImageUrl + mStoreInformationModel.getBody().getFullStoreBanner();
            }
            ImageLoader.getInstance().displayImage(url, storedetailHeadBg, MyBaseApplication.getApplication().optionsNot);
            storedetailHeadNameTx.setText(mStoreInformationModel.getBody().getStoreName());
            storedetailHeadIntegralTx.setText(mStoreInformationModel.getBody().getDescription());
            storedetailAddrTx.setText(mStoreInformationModel.getBody().getStoreAddress());
            storedetailPhoneTx.setText(mStoreInformationModel.getBody().getStoreTel());
            storedetailWorktimeTx.setText(changeStr(mStoreInformationModel.getBody().getOpenTime()));

        }
    }

    private StringBuilder changeStr(String str) {
        StringBuilder sb = new StringBuilder(str);
        sb.insert(2, ":");
        sb.insert(5, "-");
        sb.insert(8, ":");
        return sb;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_STOREDETAIL_ID);
    }

    @OnClick({R.id.storedetail_member_ll, R.id.storedetail_tochat_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.storedetail_member_ll:
                startActivity(MemberCenterActivity.buildIntent(mContext, storeId));
                break;
            case R.id.storedetail_tochat_bt:
                if (formId == 1) {
                    finish();
                } else {
                    if (mStoreInformationModel!=null) {
                        startActivity(ShopIMActivity.buildIntent(mContext, storeId
                                , mStoreInformationModel.getBody().getStoreName()
                                , mStoreInformationModel.getBody().getStoreAvatar(), true));
                    }
                }
                break;
        }
    }
}
