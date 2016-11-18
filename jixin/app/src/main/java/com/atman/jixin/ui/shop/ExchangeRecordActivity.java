package com.atman.jixin.ui.shop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.atman.jixin.R;
import com.atman.jixin.adapter.ExchangeRecordAllAdapter;
import com.atman.jixin.model.response.ExchangeRecordModel;
import com.atman.jixin.ui.base.MyBaseActivity;
import com.atman.jixin.ui.base.MyBaseApplication;
import com.atman.jixin.ui.im.chatui.StoreDetailActivity;
import com.atman.jixin.utils.Common;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.LogUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.extras.recyclerview.PullToRefreshRecyclerView;
import com.tbl.okhttputils.OkHttpUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by tangbingliang on 16/10/24.
 */

public class ExchangeRecordActivity extends MyBaseActivity implements
        ExchangeRecordAllAdapter.IonSlidingViewClickListener {

    @Bind(R.id.pull_refresh_recycler)
    PullToRefreshRecyclerView pullRefreshRecycler;
    @Bind(R.id.exchange_empty_tx)
    TextView exchangeEmptyTx;

    private Context mContext = ExchangeRecordActivity.this;
    private ExchangeRecordAllAdapter mAdapter;
    private ExchangeRecordModel mExchangeRecordModel;

    private int allPosition;
    private int mPage = 1;
    private int mSize = 20;
    private long storeId;
    private String title;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchangerecord);
        ButterKnife.bind(this);
    }

    public static Intent buildIntent(Context context, long storeId, String title) {
        Intent intent = new Intent(context, ExchangeRecordActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("storeId", storeId);
        return intent;
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        title = getIntent().getStringExtra("title");
        setBarTitleTx(title);

        storeId = getIntent().getLongExtra("storeId", -1);
        initListView();
    }

    private void initListView() {
        initRefreshView(PullToRefreshBase.Mode.BOTH, pullRefreshRecycler);

        mAdapter = new ExchangeRecordAllAdapter(mContext, getmWidth(), this);

        mRecyclerView = pullRefreshRecycler.getRefreshableView();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));//这里用线性显示 类似于listview
        mRecyclerView.setAdapter(mAdapter);

        checkEmpty();
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
        onLoad(PullToRefreshBase.Mode.BOTH, pullRefreshRecycler);
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
        String url = "";
        if (storeId == -1) {
            url = Common.Url_Get_All_ExchangeRecord + mPage + "/" + mSize;
        } else {
            url = Common.Url_Get_ExchangeRecord + storeId + "/" + mPage + "/" + mSize;
        }
        LogUtils.e("url:" + url);
        OkHttpUtils.get().url(url)
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
                if (mAdapter != null && mAdapter.getItemCount() > 0) {
                    showToast("没有更多");
                }
                onLoad(PullToRefreshBase.Mode.PULL_FROM_START, pullRefreshRecycler);
            } else {
                onLoad(PullToRefreshBase.Mode.BOTH, pullRefreshRecycler);
                mAdapter.addData(mExchangeRecordModel.getBody());
            }
        } else if (id == Common.NET_DELETE_EXHANGE_ID) {
            mAdapter.removeData(allPosition);
        }

        checkEmpty();
    }

    private void checkEmpty() {
        if (mAdapter!=null && mAdapter.getItemCount()==0) {
            exchangeEmptyTx.setVisibility(View.VISIBLE);
            pullRefreshRecycler.setVisibility(View.GONE);
        } else {
            exchangeEmptyTx.setVisibility(View.GONE);
            pullRefreshRecycler.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_GOODS_EXCHANGERECORD_ID);
        OkHttpUtils.getInstance().cancelTag(Common.NET_DELETE_EXHANGE_ID);
    }

    @Override
    public void onItemClick(View view, int position) {
        allPosition = position;
        switch (view.getId()) {
            case R.id.item_exchangerecord_shop_tx:
                startActivity(StoreDetailActivity.buildIntent(mContext
                        , mAdapter.getItemById(position).getStoreId(), 2));
                break;
            case R.id.item_fullcut_root_rl:
                if (storeId == -1) {
                    startActivity(StoreDetailActivity.buildIntent(mContext
                            , mAdapter.getItemById(position).getStoreId(), 2));
                } else {
                    startActivity(GoodsDetailActivity.buildIntent(mContext
                            , mAdapter.getItemById(position).getGoodsId()
                            , mAdapter.getItemById(position).getGoodsName(), 0, 0, null));
                }
                break;
        }
    }

    @Override
    public void onDeleteBtnCilck(View view, int position) {
        allPosition = position;
        if (mAdapter.getItemById(position).getState() == 1) {
            showWraning("此订单正在处理中,不能删除");
            return;
        }
        Map<String, String> p = new HashMap<>();
        p.put("id", String.valueOf(mAdapter.getItemById(position).getId()));
        p.put("result", "4"); //B端删除
        OkHttpUtils.postString().url(Common.Url_Delete_ExchangeRecord)
                .tag(Common.NET_DELETE_EXHANGE_ID).id(Common.NET_DELETE_EXHANGE_ID)
                .content(mGson.toJson(p)).mediaType(Common.JSON)
                .headers(MyBaseApplication.getApplication().getHeaderSeting())
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .build().execute(new MyStringCallback(mContext, "删除中..."
                , ExchangeRecordActivity.this, true));
    }
}
