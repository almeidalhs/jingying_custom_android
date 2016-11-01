package com.atman.jixin.ui.im.chatui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.atman.jixin.R;
import com.atman.jixin.adapter.GoodsGridViewAdapter;
import com.atman.jixin.model.response.GetGoodsByClassIdModel;
import com.atman.jixin.ui.base.MyBaseApplication;
import com.atman.jixin.ui.base.MyBaseFragment;
import com.atman.jixin.utils.Common;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.LogUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.tbl.okhttputils.OkHttpUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by tangbingliang on 16/10/31.
 */

public class MenuPreviewFragment extends MyBaseFragment implements AdapterInterface {

    @Bind(R.id.pullToRefreshGridView)
    PullToRefreshGridView pullToRefreshGridView;

    private String title;
    private String stc_id;
    private String storeId;
    private int page = 1;
    private int mPageSize = 20;//每页个数
    
    private GetGoodsByClassIdModel mGetGoodsByClassIdModel;
    private List<GetGoodsByClassIdModel.BodyBean> mBodyEntityList;
    private GoodsGridViewAdapter mAdapter;
    private boolean isUserVisibleHint = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menupreview, null);
        ButterKnife.bind(this, view);
        Bundle b = getArguments();
        title = b.getString("TITLES");
        stc_id = b.getString("typeId");
        storeId = b.getString("storeId");
        page = 1;
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);

        initGridView();
    }

    private void initGridView() {
        LogUtils.e("initGridView()");
        initRefreshView(PullToRefreshBase.Mode.BOTH, pullToRefreshGridView);

        mAdapter = new GoodsGridViewAdapter(getActivity(), getmWidth(), this);
        pullToRefreshGridView.setAdapter(mAdapter);
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();
        LogUtils.e("doInitBaseHttp");
        doHttp(false);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Integer.parseInt(stc_id)) {
            mGetGoodsByClassIdModel = mGson.fromJson(data, GetGoodsByClassIdModel.class);
            mBodyEntityList = mGetGoodsByClassIdModel.getBody();
            if (mBodyEntityList == null || mBodyEntityList.size() == 0) {
                if (mAdapter!=null && mAdapter.getCount()>0) {
                    showToast("没有更多");
                }
                onLoad(PullToRefreshBase.Mode.PULL_FROM_START, pullToRefreshGridView);
            } else {
                onLoad(PullToRefreshBase.Mode.BOTH, pullToRefreshGridView);
                mAdapter.setBody(mBodyEntityList);
            }
        }
    }

    @Override
    public void onError(Call call, Exception e, int code, int id) {
        super.onError(call, e, code, id);
        cancelLoading();
        if (page>0) {
            page -= 1;
        }
        onLoad(PullToRefreshBase.Mode.BOTH, pullToRefreshGridView);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isUserVisibleHint = isVisibleToUser;
//        if (isVisibleToUser && storeId!=null) {
//            LogUtils.e("setUserVisibleHint");
//            doHttp(false);
//        }
    }

    private void doHttp(boolean b) {
        LogUtils.e("url:"+(Common.Url_Get_Store_Goods_By_Id + storeId+"/"+stc_id+"/"+page+"/"+mPageSize));
        OkHttpUtils.get().url(Common.Url_Get_Store_Goods_By_Id + storeId+"/"+stc_id+"/"+page+"/"+mPageSize)
                .headers(MyBaseApplication.getApplication().getHeaderSeting())
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .tag(stc_id).id(Integer.parseInt(stc_id)).build()
                .execute(new MyStringCallback(getActivity(), "", this, b));
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        page = 1;
        mAdapter.clearBody();
        doHttp(false);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        page += 1;
        doHttp(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(stc_id);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtils.e("onDestroyView:"+stc_id);
        ButterKnife.unbind(this);
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}
