package com.atman.jixin.ui.personal;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.atman.jixin.R;
import com.atman.jixin.adapter.MyCardAdapter;
import com.atman.jixin.model.response.MyCardModel;
import com.atman.jixin.ui.base.MyBaseActivity;
import com.atman.jixin.ui.base.MyBaseApplication;
import com.atman.jixin.utils.Common;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.net.MyStringCallback;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.tbl.okhttputils.OkHttpUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by tangbingliang on 16/11/21.
 */

public class MyCardActivity extends MyBaseActivity implements AdapterInterface {

    @Bind(R.id.pullToRefreshListView)
    PullToRefreshListView pullToRefreshListView;

    private Context mContext = MyCardActivity.this;
    private View mEmpty;
    private TextView mEmptyTX;
    private MyCardAdapter mAdapter;

    private int mPage = 1;
    private int mSize = 20;
    private MyCardModel mMyCardModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycard);
        ButterKnife.bind(this);
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);

        setBarTitleTx("我的卡券");
        initListView();
    }

    private void initListView() {
        initRefreshView(PullToRefreshBase.Mode.BOTH, pullToRefreshListView);
        mEmpty = LayoutInflater.from(mContext).inflate(R.layout.part_empty_view, null);
        mEmptyTX = (TextView) mEmpty.findViewById(R.id.part_empty_tx);
        mEmptyTX.setText("暂无卡券");

        mAdapter = new MyCardAdapter(mContext, this);
        pullToRefreshListView.setEmptyView(mEmpty);
        pullToRefreshListView.setAdapter(mAdapter);
        pullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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
        OkHttpUtils.get().url(Common.Url_Get_Coupons + "0/0/" + mPage + "/" + mSize)
                .headers(MyBaseApplication.getApplication().getHeaderSeting())
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .tag(Common.NET_GET_COUPONS_ID).id(Common.NET_GET_COUPONS_ID).build()
                .execute(new MyStringCallback(mContext, "", this, b));
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_GET_COUPONS_ID) {
            mMyCardModel = mGson.fromJson(data, MyCardModel.class);
            if (mMyCardModel.getBody() == null
                    || mMyCardModel.getBody().size() == 0) {
                if (mAdapter!=null && mAdapter.getCount()>0) {
                    showToast("没有更多");
                }
                onLoad(PullToRefreshBase.Mode.PULL_FROM_START, pullToRefreshListView);
            } else {
                onLoad(PullToRefreshBase.Mode.BOTH, pullToRefreshListView);
                mAdapter.addBody(mMyCardModel.getBody());
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_COUPONS_ID);
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}
