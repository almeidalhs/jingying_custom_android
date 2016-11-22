package com.atman.jixin.ui.im.chatui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.atman.jixin.R;
import com.atman.jixin.model.response.CouponDetailModel;
import com.atman.jixin.model.response.GetCouponModel;
import com.atman.jixin.ui.base.MyBaseActivity;
import com.atman.jixin.ui.base.MyBaseApplication;
import com.atman.jixin.utils.Common;
import com.atman.jixin.utils.MyTools;
import com.base.baselibs.net.MyStringCallback;
import com.tbl.okhttputils.OkHttpUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;

/**
 * Created by tangbingliang on 16/11/22.
 */

public class GetCouponActivity extends MyBaseActivity {

    @Bind(R.id.coupondetail_price_tx)
    TextView coupondetailPriceTx;
    @Bind(R.id.coupondetail_limit_tx)
    TextView coupondetailLimitTx;
    @Bind(R.id.coupondetail_remaining_tx)
    TextView coupondetailRemainingTx;
    @Bind(R.id.coupondetail_limitnum_tx)
    TextView coupondetailLimitnumTx;
    @Bind(R.id.coupondetail_starttime_tx)
    TextView coupondetailStarttimeTx;
    @Bind(R.id.coupondetail_endtime_tx)
    TextView coupondetailEndtimeTx;
    @Bind(R.id.coupondetail_bt)
    Button coupondetailBt;

    private Context mContext = GetCouponActivity.this;

    private long CouponId;
    private CouponDetailModel mCouponDetailModel;
    private GetCouponModel mGetCouponModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getcoupon);
        ButterKnife.bind(this);
    }

    public static Intent buildIntent(Context context, long CouponId) {
        Intent intent = new Intent(context, GetCouponActivity.class);
        intent.putExtra("CouponId", CouponId);
        return intent;
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);

        setBarTitleTx("优惠券");

        CouponId = getIntent().getLongExtra("CouponId", 0);
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();
        OkHttpUtils.get().url(Common.Url_Coupon_Detail + CouponId)
                .headers(MyBaseApplication.getApplication().getHeaderSeting())
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .tag(Common.NET_COUPON_DETAIL_ID).id(Common.NET_COUPON_DETAIL_ID).build()
                .execute(new MyStringCallback(mContext, "", this, true));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_COUPON_DETAIL_ID) {
            mCouponDetailModel = mGson.fromJson(data, CouponDetailModel.class);

            upDateUi();
        } else if (id == Common.NET_COUPON_DETAIL_GET_ID) {
            mGetCouponModel = mGson.fromJson(data, GetCouponModel.class);
            showToast("领取成功");
        }
    }

    private void upDateUi() {
        coupondetailPriceTx.setText(mCouponDetailModel.getBody().getCouponPrice() + "");
        coupondetailLimitTx.setText("满" + mCouponDetailModel.getBody().getCouponLimit() + "可用");
        coupondetailRemainingTx.setText("剩余" + (mCouponDetailModel.getBody().getCouponStorage()
                - mCouponDetailModel.getBody().getCouponUsage()) +"张");
        coupondetailLimitnumTx.setText("限领" + mCouponDetailModel.getBody().getUserReceiveLimit()+"张");
        coupondetailStarttimeTx.setText("开始:" + MyTools.convertTime(mCouponDetailModel.getBody()
                .getCouponStartDate() * 1000, "yyyy-MM-dd HH:mm"));
        coupondetailEndtimeTx.setText("结束:" + MyTools.convertTime(mCouponDetailModel.getBody()
                .getCouponEndDate() * 1000, "yyyy-MM-dd HH:mm"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_COUPON_DETAIL_ID);
        OkHttpUtils.getInstance().cancelTag(Common.NET_COUPON_DETAIL_GET_ID);
    }

    @OnClick({R.id.coupondetail_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.coupondetail_bt:

                OkHttpUtils.postString().url(Common.Url_Coupon_Detail_Get + CouponId)
                        .content("")
                        .headers(MyBaseApplication.getApplication().getHeaderSeting())
                        .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                        .tag(Common.NET_COUPON_DETAIL_GET_ID).id(Common.NET_COUPON_DETAIL_GET_ID).build()
                        .execute(new MyStringCallback(mContext, "", this, true));
                break;
        }
    }
}
