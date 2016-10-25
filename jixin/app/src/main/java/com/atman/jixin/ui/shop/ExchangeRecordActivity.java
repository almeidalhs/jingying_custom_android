package com.atman.jixin.ui.shop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.atman.jixin.R;
import com.atman.jixin.adapter.ExchangeRecordAdapter;
import com.atman.jixin.adapter.IntegralExchangeAdapter;
import com.atman.jixin.model.response.ExchangeRecordModel;
import com.atman.jixin.ui.base.MyBaseActivity;
import com.atman.jixin.ui.base.MyBaseApplication;
import com.atman.jixin.utils.Common;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.LogUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.tbl.okhttputils.OkHttpUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by tangbingliang on 16/10/24.
 */

public class ExchangeRecordActivity extends MyBaseActivity implements AdapterInterface {

    @Bind(R.id.pullToRefreshListView)
    PullToRefreshListView pullToRefreshListView;

    private Context mContext = ExchangeRecordActivity.this;
    private ExchangeRecordAdapter mAdapter;
    private ExchangeRecordModel mExchangeRecordModel;

    private int mPage = 1;
    private int mSize = 20;
    private long storeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchangerecord);
        ButterKnife.bind(this);
    }

    public static Intent buildIntent(Context context, long storeId) {
        Intent intent = new Intent(context, ExchangeRecordActivity.class);
        intent.putExtra("storeId", storeId);
        return intent;
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        setBarTitleTx("兑换记录");

        storeId = getIntent().getLongExtra("storeId", -1);
        initListView();
    }

    private void initListView() {
        initRefreshView(PullToRefreshBase.Mode.BOTH, pullToRefreshListView);

        mAdapter = new ExchangeRecordAdapter(mContext, this);
        pullToRefreshListView.setAdapter(mAdapter);
        pullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(GoodsDetailActivity.buildIntent(mContext
                        , mAdapter.getItem(position-1).getGoodsId()
                        , mAdapter.getItem(position-1).getGoodsName()));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();
        dohttp(true);
    }

    @Override
    public void onError(Call call, Exception e, int code, int id) {
        super.onError(call, e, code, id);
        mPage = 1;
        onLoad(PullToRefreshBase.Mode.BOTH, pullToRefreshListView);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        super.onPullUpToRefresh(refreshView);
        mPage += 1;
        dohttp(false);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        super.onPullDownToRefresh(refreshView);
        mPage = 1;
        mAdapter.clearData();
        dohttp(false);
    }

    private void dohttp(boolean b) {
        LogUtils.e("url:"+(Common.Url_Get_ExchangeRecord + storeId + "/" + mPage + "/" + mSize));
        OkHttpUtils.get().url(Common.Url_Get_ExchangeRecord + storeId + "/" + mPage + "/" + mSize)
                .headers(MyBaseApplication.getApplication().getHeaderSeting())
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .tag(Common.NET_GET_GOODS_EXCHANGERECORD_ID)
                .id(Common.NET_GET_GOODS_EXCHANGERECORD_ID).build()
                .execute(new MyStringCallback(mContext, "", this, b));
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_GET_GOODS_EXCHANGERECORD_ID) {
            mExchangeRecordModel = mGson.fromJson(data, ExchangeRecordModel.class);
            if (mExchangeRecordModel.getBody() == null
                    || mExchangeRecordModel.getBody().size() == 0) {
                if (mAdapter!=null && mAdapter.getCount()>0) {
                    showToast("没有更多");
                }
                onLoad(PullToRefreshBase.Mode.PULL_FROM_START, pullToRefreshListView);
            } else {
                onLoad(PullToRefreshBase.Mode.BOTH, pullToRefreshListView);
                mAdapter.addBody(mExchangeRecordModel.getBody());
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_GOODS_EXCHANGERECORD_ID);
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}
