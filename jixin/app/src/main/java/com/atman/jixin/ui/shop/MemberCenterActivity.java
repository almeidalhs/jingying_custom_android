package com.atman.jixin.ui.shop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atman.jixin.R;
import com.atman.jixin.adapter.MemberCenterAdapter;
import com.atman.jixin.model.response.MemberCenterModel;
import com.atman.jixin.ui.base.MyBaseActivity;
import com.atman.jixin.ui.base.MyBaseApplication;
import com.atman.jixin.utils.Common;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.widget.ShapeImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tbl.okhttputils.OkHttpUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Response;

/**
 * Created by tangbingliang on 16/10/24.
 */

public class MemberCenterActivity extends MyBaseActivity implements AdapterInterface, View.OnClickListener {

    @Bind(R.id.membercenter_listview)
    ListView membercenterListview;
    @Bind(R.id.part_empty_tx)
    TextView partEmptyTx;

    private Context mContext = MemberCenterActivity.this;
    private long storeId;
    private MemberCenterModel mMemberCenterModel;
    private View headView;
//    private View emptyView;
    private MemberCenterAdapter mAdapter;

    private ShapeImageView membercenterHeadIv;
    private ImageView membercenterHeadBg;
    private TextView membercenterHeadNameTx;
    private TextView membercenterHeadIntegralTx;
    private TextView membercenterHeadIntegralMarkTx;
//    private TextView partEmptyTx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membercenter);
        ButterKnife.bind(this);
    }

    public static Intent buildIntent(Context context, long storeId) {
        Intent intent = new Intent(context, MemberCenterActivity.class);
        intent.putExtra("storeId", storeId);
        return intent;
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        setBarTitleTx("会员中心");

//        emptyView = LayoutInflater.from(mContext).inflate(R.layout.part_empty_view, null);
//        partEmptyTx = (TextView) emptyView.findViewById(R.id.part_empty_tx);
        headView = LayoutInflater.from(mContext).inflate(R.layout.part_membercenter_head_view, null);
        membercenterHeadIv = (ShapeImageView) headView.findViewById(R.id.membercenter_head_iv);
        membercenterHeadBg = (ImageView) headView.findViewById(R.id.membercenter_head_bg);
        membercenterHeadNameTx = (TextView) headView.findViewById(R.id.membercenter_head_name_tx);
        membercenterHeadIntegralTx = (TextView) headView.findViewById(R.id.membercenter_head_integral_tx);
        membercenterHeadIntegralMarkTx = (TextView) headView.findViewById(R.id.membercenter_head_integralmark_tx);
        membercenterHeadIntegralTx.setOnClickListener(this);
        membercenterHeadIntegralMarkTx.setOnClickListener(this);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(getmWidth(),
                getmWidth() * 215 / 415);
        membercenterHeadBg.setLayoutParams(params);

        mAdapter = new MemberCenterAdapter(mContext, this);
        membercenterListview.addHeaderView(headView);
//        membercenterListview.setEmptyView(emptyView);
        membercenterListview.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        storeId = getIntent().getLongExtra("storeId", -1);
        OkHttpUtils.get().url(Common.Url_MemberCenter + storeId)
                .headers(MyBaseApplication.getApplication().getHeaderSeting())
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .tag(Common.NET_MEMBER_CENTRER).id(Common.NET_MEMBER_CENTRER).build()
                .execute(new MyStringCallback(mContext, "", this, true));
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_MEMBER_CENTRER) {
            mMemberCenterModel = mGson.fromJson(data, MemberCenterModel.class);

            updateUi();
        }
    }

    private void updateUi() {
        if (mMemberCenterModel.getBody().getUserList().size()==0) {
            partEmptyTx.setVisibility(View.VISIBLE);
        } else {
            partEmptyTx.setVisibility(View.GONE);
        }
        ImageLoader.getInstance().displayImage(Common.ImageUrl + mMemberCenterModel.getBody().getStoreAvatar()
                , membercenterHeadIv, MyBaseApplication.getApplication().optionsHead);
        ImageLoader.getInstance().displayImage(Common.ImageUrl + mMemberCenterModel.getBody().getStoreAvatar()
                , membercenterHeadBg, MyBaseApplication.getApplication().optionsHead);
        membercenterHeadNameTx.setText(mMemberCenterModel.getBody().getStoreName());
        membercenterHeadIntegralTx.setText("  我的积分  " + mMemberCenterModel.getBody().getIntegral());
        mAdapter.updateListView(mMemberCenterModel.getBody().getUserList());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_MEMBER_CENTRER);
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (view.getId()) {
            case R.id.item_member_root_ll:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.membercenter_head_integralmark_tx:
            case R.id.membercenter_head_integral_tx:
                startActivity(new Intent(mContext, IntegralexchangeActivity.class));
                break;
        }
    }
}
